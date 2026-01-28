package com.imageconversion.conversion.servlet;

import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.imageconversion.common.exception.LogicException;
import com.imageconversion.conversion.logic.SaveImage;
import com.imageconversion.conversion.logic.SaveImageImpl;

/**
 * <p>画像を保存するサーブレット。</p>
 */
@MultipartConfig
public class SaveImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 保存先のディレクトリパス
	private String dirPath = "C:/Download/";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveImageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/octet-stream");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 保存先のフォルダ
		String base64Image = request.getParameter("base64Image");
		String extension = request.getParameter("extension");
		String fileName = request.getParameter("fileName");
		String backUrl = request.getParameter("backUrl");// 遷移前のURL
		if (base64Image == null || extension == null) {
			if (backUrl == null || backUrl.isEmpty()) {
			    backUrl = "../home/Menu.html"; // デフォルト戻り先
			}
			// ひとつ前の画面に戻る
			response.sendRedirect(backUrl);
            return;
        }
		
		// "data:image/png;base64," のようなプレフィックスを除去
        if (base64Image.startsWith("data:image")) {
            base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        }
		
        // デーコード
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);
		
		String saveImagePath = "";
		SaveImage saveImageLogic = new SaveImageImpl();
		try {
			// 指定したディレクトリに画像を保存
			saveImagePath = saveImageLogic.saveImage(imageBytes, dirPath, fileName);
		} catch (LogicException e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
			return;
		}
		
		request.setAttribute("saveImagePath", saveImagePath);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/function/saveSuccess.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * <p>保存先のディレクトリパスを設定する（テスト用）。</p>
	 * 
	 * @param dirPath 保存先のディレクトリパス
	 */
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}

}
