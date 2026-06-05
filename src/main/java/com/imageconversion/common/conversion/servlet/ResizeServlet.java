package com.imageconversion.common.conversion.servlet;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

/**
 * <p>画像のリサイズを行うサーブレット。</p>
 */
@MultipartConfig
public class ResizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** リサイズ後に許容する最大の幅・高さ（ピクセル）のデフォルト値。 */
	private static final int DEFAULT_MAX_DIMENSION = 10000;

	/** リサイズ後に許容する最大の幅・高さ（ピクセル）。 */
	private final int maxDimension;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResizeServlet() {
        this(DEFAULT_MAX_DIMENSION);
    }

    /**
     * <p>許容する最大の幅・高さを指定してインスタンスを生成する。</p>
     *
     * @param maxDimension リサイズ後に許容する最大の幅・高さ（ピクセル）
     */
    public ResizeServlet(int maxDimension) {
        super();
        this.maxDimension = maxDimension;
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part filePart = request.getPart("imageFile");
        String widthParam = request.getParameter("width");
        String heightParam = request.getParameter("height");

        if (FileValidator.isEmptyFilePart(filePart)) {
        	 response.sendRedirect("/function/resize.jsp");
             return;
        }

        int newWidth = 0;
        int newHeight = 0;
        try {
        	newWidth = Integer.parseInt(widthParam);
            newHeight = Integer.parseInt(heightParam);
		} catch (NumberFormatException e) {
			request.setAttribute("exception", "幅と高さは数値で入力してください");
			request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
			return;
		}

        // 寸法の範囲チェック（過大な値によるメモリ枯渇を防止）
        if (newWidth < 1 || newHeight < 1 ||
        		newWidth > maxDimension || newHeight > maxDimension) {
        	request.setAttribute("exception",
        			"幅と高さは1以上" + maxDimension + "以下で入力してください");
        	request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
        	return;
        }

        try {
        	BufferedImage originalImage = FileValidator.readImage(filePart);

        	// リサイズ処理
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            request.setAttribute("base64Image", base64Image);
            request.getRequestDispatcher("/function/resize.jsp").forward(request, response);
        } catch (IOException e) {
        	request.setAttribute("exception", e.getMessage());
        	request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
        }
	}

}
