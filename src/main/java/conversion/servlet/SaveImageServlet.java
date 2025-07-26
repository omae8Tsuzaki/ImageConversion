package conversion.servlet;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conversion.logic.SaveImage;
import conversion.logic.SaveImageImpl;

/**
 * <p>画像を保存するサーブレット。</p>
 */
@WebServlet("/function/saveImage")
public class SaveImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 保存先のフォルダ
		String dirPath = request.getParameter("dirPath");
		String base64Image = request.getParameter("base64Image");
		String extension = request.getParameter("extension");
		String fileName = request.getParameter("fileName");
		if (base64Image == null || extension == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image data");
            return;
        }
		
		
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);
		
		String saveImagePath = "";
		SaveImage saveImageLogic = new SaveImageImpl();
		try {
			// 指定したディレクトリに画像を保存
			saveImagePath = saveImageLogic.saveImage(imageBytes, dirPath, fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("saveImagePath", saveImagePath);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/function/saveImageSuccess.jsp");
		dispatcher.forward(request, response);
	}

}
