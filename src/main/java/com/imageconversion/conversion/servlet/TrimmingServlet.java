package com.imageconversion.conversion.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

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
		if (filePart == null || filePart.getSize() == 0) {
			response.sendRedirect("/function/trimming.jsp");
			return;
		}
		int x = Sanitize.parseStringToInt(xParam);
		int y = Sanitize.parseStringToInt(yParam);
		int width = Sanitize.parseStringToInt(widthParam);
		int height = Sanitize.parseStringToInt(heightParam);

		try (InputStream inputStream = filePart.getInputStream()) {
			BufferedImage originalImage = ImageIO.read(inputStream);
			if (originalImage == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
				request.setAttribute("exception", "無効な画像ファイルです");
				rd.forward(request, response);
				return;
			}
			// トリミング範囲のチェック
			if ( x < 0 || y < 0 || 
				originalImage.getWidth() < x || 
				originalImage.getHeight() < y ||
				width <= 0 || height <= 0 ||
				x + width > originalImage.getWidth() || 
				y + height > originalImage.getHeight()) {
				
				RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
				request.setAttribute("exception", "トリミング範囲が不正です");
				rd.forward(request, response);
				return;
			}

			// トリミング処理
			BufferedImage trimingImage = originalImage.getSubimage(x, y, width, height);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(trimingImage, "jpg", baos);
			byte[] imageBytes = baos.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
			
			request.setAttribute("base64Image", base64Image);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/function/trimming.jsp");
            dispatcher.forward(request, response);
		}
	}

}
