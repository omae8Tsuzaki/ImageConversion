package conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * <p>{@link SaveImageServlet}のテストクラス。</p>
 * 
 * <h4>doPost メソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：画像の保存が成功した場合</li>
 * </ul>
 */
public class SaveImageServletTest {
	private SaveImageServlet saveImageServlet;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    
    // テスト用
 	@TempDir
 	private File temporaryFolder;
	
    /**
     * <p>テストの前処理。</p>
     */
	@BeforeEach
	public void setUp() {
		saveImageServlet = new SaveImageServlet();
		request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
	}

	/**
	 * <p>doPost メソッドの正常系テスト：画像の保存が成功した場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostSuccess01() throws Exception {
		// 出力先ディレクトリのパスを取得
		String dirPath = temporaryFolder.getAbsolutePath();
		
		// Base64の文字列を作成
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		byte[] imageBytes = baos.toByteArray();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);

		// モックの設定
		when(request.getParameter("dirPath")).thenReturn(dirPath);
		when(request.getParameter("base64Image")).thenReturn(base64Image);
		when(request.getParameter("extension")).thenReturn("jpg");
		when(request.getParameter("fileName")).thenReturn("testImage.jpg");
		when(request.getParameter("backUrl")).thenReturn("/home/Menu.html");
		when(request.getRequestDispatcher("/function/saveSuccess.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		saveImageServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("saveImagePath"), any(String.class));
		verify(dispatcher).forward(request, response);
	}

}
