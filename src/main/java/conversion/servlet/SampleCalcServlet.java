package conversion.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.CalcImpl;

/**
 * Servlet implementation class SampleCalcServlet
 */
@WebServlet("/function/sampleCalc")
public class SampleCalcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SampleCalcServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String num1Str = request.getParameter("num1");
		String num2Str = request.getParameter("num2");
		int num1 = 0;
		int num2 = 0;
		try {
			num1 = Integer.parseInt(num1Str);
			num2 = Integer.parseInt(num2Str);
		} catch (NumberFormatException e) {
			// 数値変換に失敗した場合はエラーメッセージを設定
			request.setAttribute("error", "数値を入力してください");
			request.getRequestDispatcher("/exceptionMessage.jsp").forward(request, response);
			return;
		}
		
		// 計算を実行
		CalcImpl calc = new CalcImpl();
		int result = calc.add(num1, num2);
		
        request.setAttribute("result", result);
        // リダイレクトで結果ページへ
        request.getRequestDispatcher("/function/sampleCalc.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
