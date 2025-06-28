package conversion.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangeExtensionServlet
 */
@WebServlet("/function/changeExtension")
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
//		// ファイルの取得
//		Part filePart = request.getPart("imageFile");
//		String newExtension = request.getParameter("newExtension");
//		
//		if (filePart != null && newExtension != null) {
//            // 元のファイル名を取得
//            String originalFileName = filePart.getSubmittedFileName();
//            String fileNameWithoutExtension = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
//
//            // 新しいファイル名を作成
//            String newFileName = fileNameWithoutExtension + "." + newExtension;
//
//            // ファイルを保存するディレクトリ
//            String uploadDir = getServletContext().getRealPath("/uploads");
//            File uploadDirFile = new File(uploadDir);
//            if (!uploadDirFile.exists()) {
//                uploadDirFile.mkdirs();
//            }
//
//            // ファイルを保存
//            File newFile = new File(uploadDir, newFileName);
//            filePart.write(newFile.getAbsolutePath());
//
//            // 結果を表示
//            response.setContentType("text/html; charset=UTF-8");
//            response.getWriter().write("<h1>拡張子変更完了</h1>");
//            response.getWriter().write("<p>新しいファイル名: " + newFileName + "</p>");
//        } else {
//            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ファイルまたは拡張子が無効です。");
//        }
	}

}
