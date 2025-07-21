package conversion.servlet;

import static org.mockito.Mockito.*;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import conversion.logic.GetExtension;
import conversion.logic.GetExtensionImpl;

/**
 * <p>{@link GetExtensionServlet}のテストクラス。</p>
 * 
 * <h4>doGetメソッド</h4>
 * <ul>
 * <li>{@link #doGetSuccess01} 正常系：拡張子一覧を取得してJSPに渡す</li>
 * </ul>
 * 
 * <h4>doPostメソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：拡張子一覧を取得してJSPに渡す</li>
 * </ul>
 */
public class GetExtensionServletTest {

	private GetExtensionServlet servlet;
	private GetExtension logic;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    
    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
    public void setUp() {
    	logic = new GetExtensionImpl();
    	servlet = new GetExtensionServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
    }

	/**
	 * <p>正常系：拡張子一覧を取得してJSPに渡す。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetSuccess01() throws Exception {
		// モックの設定
		when(request.getRequestDispatcher("/function/changeExtension.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		servlet.doGet(request, response);
		
		//
		// 検証
		//
		List<String> extensions = logic.getExtensionList();
		verify(request).setAttribute("extensions", extensions);
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>正常系：拡張子一覧を取得してJSPに渡す。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostSuccess01() throws Exception {
		// モックの設定
		when(request.getRequestDispatcher("/function/changeExtension.jsp")).thenReturn(dispatcher);

		//
		// 実行
		//
		servlet.doPost(request, response);

		//
		// 検証
		//
		List<String> extensions = logic.getExtensionList();
		verify(request).setAttribute("extensions", extensions);
		verify(dispatcher).forward(request, response);
	}

}
