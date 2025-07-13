package conversion.servlet;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.ImageExtension;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ImageExtensionの値を取得してリストに変換
	    List<String> extensions = List.of(ImageExtension.values())
	                                  .stream()
	                                  .map(ImageExtension::getExtension)
	                                  .collect(Collectors.toList());
	    // リクエスト属性に設定
	    request.setAttribute("extensions", extensions);
	    // JSPにフォワード
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/function/changeExtension.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ImageExtensionの値を取得してリストに変換
        List<String> extensions = List.of(ImageExtension.values())
                                      .stream()
                                      .map(ImageExtension::getExtension)
                                      .collect(Collectors.toList());
        // リクエスト属性に設定
        request.setAttribute("extensions", extensions);
        // JSPにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/function/changeExtension.jsp");
        dispatcher.forward(request, response);
	}

}
