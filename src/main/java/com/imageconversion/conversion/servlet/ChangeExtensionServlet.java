package com.imageconversion.conversion.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.imageconversion.common.enums.ImageExtension;
import com.imageconversion.common.utils.Sanitize;
import com.imageconversion.conversion.logic.GetExtension;
import com.imageconversion.conversion.logic.GetExtensionImpl;

/**
 * <p>画像の拡張子を変更するサーブレット。</p>
 */
@MultipartConfig
public class ChangeExtensionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeExtensionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// パラメータを取得
		Part filePart = request.getPart("imageFile");
		String newExtension = request.getParameter("extension");
		// 入力確認
		if (filePart == null || filePart.getSize() == 0) {
			response.sendRedirect("/function/changeExtension.jsp");
			return;
		}
		
		// 拡張子確認
		String fileExtension = Sanitize.getFileExtension(filePart.getSubmittedFileName());
		if(!ImageExtension.isValidExtension(fileExtension)) {
			RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
		    request.setAttribute("exception", "無効な拡張子です: " + fileExtension);
		    rd.forward(request, response);
		    return;
		}
		
		// 拡張子一覧を取得
		GetExtension logic = new GetExtensionImpl();
		List<String> extensions = logic.getExtensionList();
		
		try(InputStream inputStream = filePart.getInputStream()) {
			BufferedImage originalImage = ImageIO.read(inputStream);
			if (originalImage == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
				request.setAttribute("exception", "無効な画像ファイルです");
				rd.forward(request, response);
				return;
			}
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, newExtension, baos);
			byte[] imageBytes = baos.toByteArray();
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);
			
			// 新しいファイルの名前
			String fileName = filePart.getSubmittedFileName();
			if (fileName.lastIndexOf('.') != -1) {
				fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "." + newExtension;
			} else {
				fileName += "." + newExtension;
			}
			
			request.setAttribute("base64Image", base64Image);
			request.setAttribute("oldExtension", fileExtension);
			request.setAttribute("newExtension", newExtension);
			request.setAttribute("extensions", extensions);
			request.setAttribute("fileName", fileName);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/function/changeExtension.jsp");
            dispatcher.forward(request, response);
		}
	}

}
