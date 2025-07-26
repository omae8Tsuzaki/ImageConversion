package conversion.servlet;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exception.LogicException;
import conversion.logic.SaveImage;
import conversion.logic.SaveImageImpl;

/**
 * <p>画像を保存するサーブレット。</p>
 */
@WebServlet("/function/saveImage")
@MultipartConfig
public class SaveImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 保存先のディレクトリパス
	private static final String DIR_PATH = "C:/Download/";
       
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
			saveImagePath = saveImageLogic.saveImage(imageBytes, DIR_PATH, fileName);
		} catch (LogicException e) {
			request.setAttribute("exception", e);
			request.getRequestDispatcher("/function/exceptionMessage.jsp").forward(request, response);
			return;
		}
		
		System.out.println("保存先パス: " + saveImagePath);
		request.setAttribute("saveImagePath", saveImagePath);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/function/saveSuccess.jsp");
		dispatcher.forward(request, response);
	}

}
