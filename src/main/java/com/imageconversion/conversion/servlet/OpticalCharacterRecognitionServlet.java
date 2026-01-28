package com.imageconversion.conversion.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.imageconversion.common.exception.LogicException;
import com.imageconversion.conversion.logic.OpticalCharacterRecognition;
import com.imageconversion.conversion.logic.OpticalCharacterRecognitionImpl;

/**
 * <p>光学文字認識（OCR）を行うサーブレット。</p>
 * 
 * <p>jspから遷移して実行しようとするとエラーが発生する。</p>
 * <p>java.lang.ClassNotFoundException: net.sourceforge.tess4j.ITesseract
 */
@MultipartConfig
public class OpticalCharacterRecognitionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OpticalCharacterRecognitionServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("imageFile");
		String language = "jpn";// 日本語固定
		// 入力確認
		if (filePart == null || filePart.getSize() == 0) {
			response.sendRedirect("/function/opticalCharacterRecognition.jsp");
			return;
		}
		
		// 一時ファイルを作成
		String submittedName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	    Path tempFile = Files.createTempFile("uploaded-", "-" + submittedName);
		
		try (InputStream inputStream = filePart.getInputStream()){
			BufferedImage originalImage = ImageIO.read(inputStream);
			if (originalImage == null) {
				RequestDispatcher rd = request.getRequestDispatcher("/function/exceptionMessage.jsp");
				request.setAttribute("exception", "無効な画像ファイルです");
				rd.forward(request, response);
				return;
			}
			
			// ファイルに保存
			filePart.write(tempFile.toString());
			
			// OCR 処理
			OpticalCharacterRecognition logic = new OpticalCharacterRecognitionImpl();
			String result = logic.resultOCR(tempFile.toFile(), language);
			
			request.setAttribute("ocrResult", result);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/function/opticalCharacterRecognition.jsp");
			dispatcher.forward(request, response);
			
		} catch (IOException | LogicException e) {
	        request.setAttribute("exception", "サーブレットでエラーが発生しました。: " + e.getMessage());
	        request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
	    } finally {
	        // 一時ファイルの削除
	        Files.deleteIfExists(tempFile);
	    }
	}

}
