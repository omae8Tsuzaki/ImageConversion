package sample.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link SampleCalcServletTest}のテストクラス。</p>
 * 
 * <h4>doGet のテスト</h4>
 * <ul>
 * <li>{@link #doGetSuccess01} 正常系：数値を入力した場合、計算結果を表示する</li>
 * <li>{@link #doGetError01} 異常系：数値以外を入力した場合、エラーメッセージを表示する</li>
 * <li>{@link #doGetError02} 異常系：null が入力された場合、エラーメッセージを表示する</li>
 * <li>{@link #doGetError03} 異常系：空文字が入力された場合、エラーメッセージを表示する</li>
 * </ul>
 * 
 * <h4>doPost のテスト</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：数値を入力した場合、計算結果を表示する</li>
 * </ul>
 * 
 */
public class SampleCalcServletTest {
	
	private SampleCalcServlet sampleCalcServlet;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    
    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
	public void setUp() {
		sampleCalcServlet = new SampleCalcServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
	}

    /**
     * <p>正常系。</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doGetSuccess01() throws Exception {
		// モックの設定
		when(request.getParameter("num1")).thenReturn("10");
		when(request.getParameter("num2")).thenReturn("20");
		
		when(request.getRequestDispatcher("/function/sampleCalc.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		// 
		sampleCalcServlet.doGet(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute("result", 30);
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：数値以外が入力された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError01() throws Exception {
		// モックの設定
		when(request.getParameter("num1")).thenReturn("10");
		when(request.getParameter("num2")).thenReturn("abc");// 数値以外の入力
		
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		// 
		sampleCalcServlet.doGet(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("数値を入力してください"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：null が入力された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError02() throws Exception {
		// モックの設定
		when(request.getParameter("num1")).thenReturn(null);
		when(request.getParameter("num2")).thenReturn(null);

		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);

		//
		// 実行
		// 
		sampleCalcServlet.doGet(request, response);

		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("数値を入力してください"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：空文字が入力された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError03() throws Exception {
		// モックの設定
		when(request.getParameter("num1")).thenReturn("");
		when(request.getParameter("num2")).thenReturn("");

		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);

		//
		// 実行
		// 
		sampleCalcServlet.doGet(request, response);

		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("数値を入力してください"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>正常系：doPost。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostSuccess01() throws Exception {
		// モックの設定
		when(request.getParameter("num1")).thenReturn("10");
		when(request.getParameter("num2")).thenReturn("20");
		
		when(request.getRequestDispatcher("/function/sampleCalc.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		// 
		sampleCalcServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute("result", 30);
		verify(dispatcher).forward(request, response);
	}

}
