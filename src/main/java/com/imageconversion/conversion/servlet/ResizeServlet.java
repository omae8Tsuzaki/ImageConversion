package com.imageconversion.conversion.servlet;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
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

/**
 * <p>画像のリサイズを行うサーブレット。</p>
 */
@MultipartConfig
public class ResizeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResizeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Part filePart = request.getPart("imageFile");
        String widthParam = request.getParameter("width");
        String heightParam = request.getParameter("height");
        
        if (filePart == null || filePart.getSize() == 0 ) {
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
        
        // 画像を一時ファイルに保存
        try (InputStream inputStream = filePart.getInputStream()) {
        	BufferedImage originalImage = ImageIO.read(inputStream);
        	if(originalImage == null) {
            	RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
    			request.setAttribute("exception", "無効な画像ファイルです");
    			rd.forward(request, response);
    			return;
            }
        	
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/function/resize.jsp");
            dispatcher.forward(request, response);
        }
	}

}
