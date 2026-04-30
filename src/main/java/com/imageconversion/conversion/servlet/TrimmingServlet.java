package com.imageconversion.conversion.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.imageconversion.common.utils.FileValidator;
import com.imageconversion.common.utils.Sanitize;

/**
 * <p>画像のトリミングを行うサーブレット。</p>
 */
@MultipartConfig
public class TrimmingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public TrimmingServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/octet-stream");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// パラメータを取得
		Part filePart = request.getPart("imageFile");
		String xParam = request.getParameter("x");
		String yParam = request.getParameter("y");
		String widthParam = request.getParameter("width");
		String heightParam = request.getParameter("height");
		// 入力確認
		if (FileValidator.isEmptyFilePart(filePart)) {
			response.sendRedirect("/function/trimming.jsp");
			return;
		}
		int x = Sanitize.parseStringToInt(xParam);
		int y = Sanitize.parseStringToInt(yParam);
		int width = Sanitize.parseStringToInt(widthParam);
		int height = Sanitize.parseStringToInt(heightParam);

		try {
			BufferedImage originalImage = FileValidator.readImage(filePart);
			// トリミング範囲のチェック
			if (x < 0 || y < 0 ||
				originalImage.getWidth() < x ||
				originalImage.getHeight() < y ||
				width <= 0 || height <= 0 ||
				x + width > originalImage.getWidth() ||
				y + height > originalImage.getHeight()) {

				request.setAttribute("exception", "トリミング範囲が不正です");
				request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
				return;
			}

			// トリミング処理
			BufferedImage trimingImage = originalImage.getSubimage(x, y, width, height);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(trimingImage, "jpg", baos);
			byte[] imageBytes = baos.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);

			request.setAttribute("base64Image", base64Image);
			request.getRequestDispatcher("/function/trimming.jsp").forward(request, response);
		} catch (IOException e) {
			request.setAttribute("exception", e.getMessage());
			request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
		}
	}

}
