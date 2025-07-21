package conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link ResizeServlet}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #doPostSuccess01}正常系：画像ファイルが選択され、リサイズが成功した場合</li>
 * <li>{@link #doPostError01}異常系：画像ファイルが選択されていない場合</li>
 * <li>{@link #doPostError02}異常系：無効な画像データが送信された場合</li>
 * <li>{@link #doPostError03}異常系：幅と高さが数値以外の入力の場合</li>
 * </ul>
 */
public class ResizeServletTest {
	private ResizeServlet resizeServlet;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Part imagePart;
    
    String testImagePath = "src/test/resources/TestJpg.jpg";
    
    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
    public void setUp() {
    	resizeServlet = new ResizeServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        imagePart = mock(Part.class);
    }
    
    /**
     * <p>{@link ResizeServlet#doPost(HttpServletRequest, HttpServletResponse)}の正常系テスト。</p>
     * <p>リサイズが成功した場合</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doPostSuccess01() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(request.getParameter("width")).thenReturn("100");
		when(request.getParameter("height")).thenReturn("100");
		
		// 有効な BufferedImage を動的に生成
		BufferedImage testImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(testImage, "png", baos);
		ByteArrayInputStream imageStream = new ByteArrayInputStream(baos.toByteArray());
		
		when(imagePart.getSize()).thenReturn((long) baos.size());
		when(imagePart.getInputStream()).thenReturn(imageStream);
		when(request.getRequestDispatcher("/function/resize.jsp")).thenReturn(dispatcher);
		
		// 実行
		resizeServlet.doPost(request, response);
		
		// 検証

		verify(request).setAttribute(eq("base64Image"), any(String.class));
        verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>{@link ResizeServlet#doPost(HttpServletRequest, HttpServletResponse)}の異常系テスト。</p>
	 * <p>画像ファイルが選択されていない場合</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError01() throws Exception {
		when(request.getPart("imageFile")).thenReturn(null);

		// 実行
		resizeServlet.doPost(request, response);

		// 検証
        verify(response).sendRedirect("/function/resize.jsp");
	}
	
	/**
	 * <p>{@link ResizeServlet#doPost(HttpServletRequest, HttpServletResponse)}の異常系テスト。</p>
	 * <p>無効な画像データが送信された場合</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError02() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
        when(request.getParameter("width")).thenReturn("100");
        when(request.getParameter("height")).thenReturn("100");

        // 無効な画像データ
        ByteArrayInputStream invalidStream = new ByteArrayInputStream("notanimage".getBytes());
        when(imagePart.getSize()).thenReturn(10L);
        when(imagePart.getInputStream()).thenReturn(invalidStream);

        when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);

        // 実行
        resizeServlet.doPost(request, response);

        // 検証
        verify(request).setAttribute(eq("exception"), contains("無効な画像"));
        verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>{@link ResizeServlet#doPost(HttpServletRequest, HttpServletResponse)}の異常系テスト。</p>
	 * <p>幅と高さが数値以外の入力の場合</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError03() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(request.getParameter("width")).thenReturn("abc"); // 数値以外の入力
		when(request.getParameter("height")).thenReturn("abc");

		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenThrow(new IOException("Invalid image data"));

		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);

		// 実行
		resizeServlet.doPost(request, response);

		// 検証
		verify(request).setAttribute(eq("exception"), contains("幅と高さは数値で入力してください"));
		verify(dispatcher).forward(request, response);
	}

}
