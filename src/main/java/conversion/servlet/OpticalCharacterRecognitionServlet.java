package conversion.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * <p>光学文字認識（OCR）を行うサーブレット。</p>
 */
@WebServlet("/function/opticalCharacterRecognition")
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
		if (filePart == null || filePart.getSize() == 0) {
			response.sendRedirect("/function/opticalCharacterRecognition.jsp");
			return;
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/function/opticalCharacterRecognition.jsp");
		dispatcher.forward(request, response);
	}

}
