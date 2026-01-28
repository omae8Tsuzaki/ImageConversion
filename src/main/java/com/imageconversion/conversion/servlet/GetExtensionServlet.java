package com.imageconversion.conversion.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.imageconversion.conversion.logic.GetExtension;
import com.imageconversion.conversion.logic.GetExtensionImpl;

/**
 * <p>画像の拡張子を取得するサーブレット。</p>
 */
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
