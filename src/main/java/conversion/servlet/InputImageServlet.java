package conversion.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.opencv.core.Mat;


/**
 * Servlet implementation class InputImageServlet
 */
@WebServlet(name = "inputImage", urlPatterns = { "/inputImage" })
public class InputImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIR = "uploads";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InputImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// アップロードディレクトリのパスを取得
        String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        
        // アップロードディレクトリが存在しない場合は作成
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        
		try {
			// ファイルを保存
            String fileName = Paths.get(request.getPart("file").getSubmittedFileName()).getFileName().toString();
            File file = new File(uploadPath, fileName);
            request.getPart("file").write(file.getAbsolutePath());
            
            // 画像のURLを作成
            String fileUrl = request.getContextPath() + "/" + UPLOAD_DIR + "/" + fileName;
            
            RequestDispatcher rd = request.getRequestDispatcher("/conversion.jsp");
    		request.setAttribute("", fileUrl);
    		rd.forward(request, response);
		} catch (Exception e) {
			RequestDispatcher rd = request.getRequestDispatcher("/exceptionMessage.jsp");
			request.setAttribute("exception", e);
			rd.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
