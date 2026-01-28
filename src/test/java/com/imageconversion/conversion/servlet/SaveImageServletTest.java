package com.imageconversion.conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * <p>{@link SaveImageServlet}のテストクラス。</p>
 * 
 * <h4>doPost メソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：画像の保存が成功した場合</li>
 * <li>{@link #doPostError01} 異常系：base64Image パラメータが null の場合</li>
 * <li>{@link #doPostError02} 異常系：extension パラメータが null の場合</li>
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
        
        // テスト用の一時ディレクトリを設定
        saveImageServlet.setDirPath(temporaryFolder.getAbsolutePath());
	}

	/**
	 * <p>doPost メソッドの正常系テスト：画像の保存が成功した場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostSuccess01() throws Exception {
		
		// Base64の文字列を作成
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		byte[] imageBytes = baos.toByteArray();
		String base64Image = Base64.getEncoder().encodeToString(imageBytes);

		// モックの設定
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
	
	/**
	 * <p>doPost メソッドの正常系テスト：画像の保存が成功した場合。（base64Image の先頭に data:image/png;base64, あり）</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostSuccess02() throws Exception {
		// 出力先ディレクトリのパスを取得
		File file = new File("src/test/resources/TestJpg.jpg");
		if (!file.exists()) {
			throw new IllegalArgumentException("テストファイルが存在しません: " + file.getAbsolutePath());
		}
		// ファイル内容をbyte[]に読み込む
		byte[] data = Files.readAllBytes(file.toPath());
		// byte[]をbase64文字列に変換する
		String base64Image = Base64.getEncoder().encodeToString(data);

		// モックの設定
		when(request.getParameter("base64Image")).thenReturn("data:image/png;base64," + base64Image);
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
	
	/**
	 * <p>doPost メソッドの異常系テスト：base64Image パラメータが null の場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError01() throws Exception {
		// モックの設定
		String extension = "jpg";
		when(request.getParameter("base64Image")).thenReturn(null);
		when(request.getParameter("extension")).thenReturn(extension);
		
		//
		// 実行
		//
		saveImageServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(response).sendRedirect("../home/Menu.html");
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：extension パラメータが null の場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError02() throws Exception {
		// モックの設定
		String base64Image = "data:image/png;base64......";
		when(request.getParameter("base64Image")).thenReturn(base64Image);
		when(request.getParameter("extension")).thenReturn(null);
		
		//
		// 実行
		//
		saveImageServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(response).sendRedirect("../home/Menu.html");
	}

}
