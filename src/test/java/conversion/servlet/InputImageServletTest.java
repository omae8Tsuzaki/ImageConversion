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
 * <p>{@link InputImageServlet}のテストクラス。</p>
 * 
 * <h4>doGet メソッド</h4>
 * <ul>
 * <li>{@link #doGetSuccess01} 正常系：画像をアップロードした場合</li>
 * <li>{@link #doGetError01} 異常系：画像ファイルが選択されていない場合</li>
 * <li>{@link #doGetError02} 異常系：無効な画像データが送信された場合</li>
 * <li>{@link #doGetError03} 異常系：画像ファイルが選択されているが、サイズが0の場合</li>
 * </ul>
 */
public class InputImageServletTest {
	InputImageServlet servlet;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Part imagePart;
    
    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
	public void setUp() {
		servlet = new InputImageServlet();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
		imagePart = mock(Part.class);
	}

    /**
     * <p>正常系：画像をアップロードした場合。</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doGetSuccess01() throws Exception {
		// 準備: ダミー画像を作成
        BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dummyImage, "png", baos);
        InputStream imgStream = new ByteArrayInputStream(baos.toByteArray());
        
        // モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSubmittedFileName()).thenReturn("testImage.png");
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(imgStream);
		when(request.getRequestDispatcher("/function/sampleConversion.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		servlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("base64Image"), any(String.class));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError01() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(null);

		//
		// 実行
		//
		servlet.doPost(request, response);

		//
		// 検証
		//
		verify(response).sendRedirect("/function/sampleConversion.jsp");
	}
	
	/**
	 * <p>異常系：無効な画像データが送信された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError02() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSubmittedFileName()).thenReturn("testImage.png");
		when(imagePart.getSize()).thenReturn(100L);
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
	
	/**
	 * <p>異常系：画像ファイルが選択されているが、サイズが0の場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doGetError03() throws Exception {
		// 準備: ダミー画像を作成
        BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dummyImage, "png", baos);
        InputStream imgStream = new ByteArrayInputStream(baos.toByteArray());
        
        // モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSubmittedFileName()).thenReturn("testImage.png");
		when(imagePart.getSize()).thenReturn(0L);
		when(imagePart.getInputStream()).thenReturn(imgStream);
		when(request.getRequestDispatcher("/function/sampleConversion.jsp")).thenReturn(dispatcher);

		//
		// 実行
		//
		servlet.doPost(request, response);

		//
		// 検証
		//
		verify(response).sendRedirect("/function/sampleConversion.jsp");
	}

}
