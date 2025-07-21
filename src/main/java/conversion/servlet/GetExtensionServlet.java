package conversion.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conversion.logic.GetExtension;
import conversion.logic.GetExtensionImpl;

/**
 * <p>画像の拡張子を取得するサーブレット。</p>
 */
@WebServlet("/function/getExtension")
@MultipartConfig
public class GetExtensionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetExtensionServlet() {
        super();
    }

	/**
	 * <p>拡張子一覧を取得してJSPに渡す（画面初期表示など）。</p>
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetExtension logic = new GetExtensionImpl();
		List<String> extensions = logic.getExtensionList();
		
		request.setAttribute("extensions", extensions);
	    
		// JSPにフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/function/changeExtension.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * <p>ユーザーが送信した拡張子文字列から ImageExtension を特定し、結果をJSPに返す。</p>
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GetExtension logic = new GetExtensionImpl();
		List<String> extensions = logic.getExtensionList();
		
		request.setAttribute("extensions", extensions);
	    
		// JSPにフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/function/changeExtension.jsp");
	    dispatcher.forward(request, response);
	}

}
