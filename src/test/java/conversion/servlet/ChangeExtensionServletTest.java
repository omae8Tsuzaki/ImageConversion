package conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link ChangeExtensionServlet}のテストクラス。</p>
 * 
 * <h4>doPost メソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：画像ファイルが選択され、拡張子変更が成功した場合</li>
 * <li>{@link #doPostError01} 異常系：画像ファイルが選択されていない場合</li>
 * <li>{@link #doPostError02} 異常系：無効な拡張子が指定された場合</li>
 * </ul>
 */
public class ChangeExtensionServletTest {

	private ChangeExtensionServlet servlet;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Part imagePart;
    
    @BeforeEach
    public void setUp() {
		servlet = new ChangeExtensionServlet();
    	request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        imagePart = mock(Part.class);
    }
    
    /**
     * <p>正常系：画像ファイルが選択され、拡張子変更が成功した場合。</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doPostSuccess01() throws Exception {
		// 準備: ダミー画像を作成
        BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dummyImage, "png", baos);
        InputStream imgStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(request.getParameter("extension")).thenReturn("jpg");
		when(request.getRequestDispatcher("/function/changeExtension.jsp")).thenReturn(dispatcher);
		// 画像ファイルのモック設定
		when(imagePart.getSubmittedFileName()).thenReturn("testImage.png");
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(imgStream);
		
		//
		// 実行
		//
		servlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("base64Image"), any(String.class));
		verify(request).setAttribute(eq("oldExtension"), eq("png"));
		verify(request).setAttribute(eq("newExtension"), eq("jpg"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError01() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(null);

		//
		// 実行
		//
		servlet.doPost(request, response);

		//
		// 検証
		//
		verify(response).sendRedirect("/function/changeExtension.jsp");
	}
	
	/**
	 * <p>異常系：無効な拡張子が指定された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError02() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSize()).thenReturn(100L);
        when(imagePart.getSubmittedFileName()).thenReturn("image.txt");
        when(request.getParameter("extension")).thenReturn("png");
        when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		servlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("無効な拡張子"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：無効な画像ファイルが指定された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError03() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSize()).thenReturn(100L);
        when(imagePart.getSubmittedFileName()).thenReturn("image.png");
        when(request.getParameter("extension")).thenReturn("jsp");
        when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));
        when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		servlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("無効な画像ファイルです"));
		verify(dispatcher).forward(request, response);
	}

}
