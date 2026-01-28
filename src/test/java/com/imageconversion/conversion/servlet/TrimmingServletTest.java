package com.imageconversion.conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * <p>{@link TrimmingServlet}のテストクラス。</p>
 * 
 * <h4>doPost メソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01} 正常系：画像のトリミングが成功した場合</li>
 * <li>{@link #doPostError01} 異常系：画像ファイルが選択されていない場合</li>
 * <li>{@link #doPostError02} 異常系：無効な画像データが送信された場合</li>
 * <li>{@link #doPostError03} 異常系：トリミング範囲が画像外の場合（x 座標が負の値）</li>
 * <li>{@link #doPostError04} 異常系：トリミング範囲が画像外の場合（y 座標が負の値）</li>
 * <li>{@link #doPostError05} 異常系：トリミング範囲が画像外の場合（x 座標 が画像幅を超える）</li>
 * <li>{@link #doPostError06} 異常系：トリミング範囲が画像外の場合（y 座標 が画像高さを超える）</li>
 * <li>{@link #doPostError07} 異常系：トリミング範囲が画像外の場合（幅が0以下）</li>
 * <li>{@link #doPostError08} 異常系：トリミング範囲が画像外の場合（高さが0以下）</li>
 * <li>{@link #doPostError09} 異常系：トリミング範囲が画像外の場合（x 座標 + 幅 が画像幅を超える）</li>
 * <li>{@link #doPostError10} 異常系：トリミング範囲が画像外の場合（y 座標 + 高さ が画像高さを超える）</li>
 * </ul>
 */
public class TrimmingServletTest {
	
	private TrimmingServlet trimmingServlet;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    private Part imagePart;
    
    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
    public void setUp() {
    	trimmingServlet = new TrimmingServlet();
    	request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
        imagePart = mock(Part.class);
    }

    /**
     * <p>doPost メソッドの正常系テスト：画像のトリミングが成功した場合。</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doPostSuccess01() throws Exception {
		
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("100");
		when(request.getParameter("height")).thenReturn("100");
		when(request.getRequestDispatcher("/function/trimming.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		ArgumentCaptor<String> base64Captor = ArgumentCaptor.forClass(String.class);
		verify(request).setAttribute(eq("base64Image"), base64Captor.capture());
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：画像ファイルが選択されていない場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError01() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(null);
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("100");
		when(request.getParameter("height")).thenReturn("100");
		when(request.getRequestDispatcher("/function/trimming.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(response).sendRedirect("/function/trimming.jsp");
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：無効な画像データが送信された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError02() throws Exception {
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("100");
		when(request.getParameter("height")).thenReturn("100");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("無効な画像ファイルです"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（x 座標が負の値）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError03() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("-1");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（y 座標が負の値）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError04() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("-1");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（x 座標 が画像幅を超える）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError05() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("200");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（y 座標 が画像高さを超える）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError06() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("200");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（幅が0以下）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError07() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("0");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（高さが0以下）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError08() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("0");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（x 座標 + 幅 が画像幅を超える）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError09() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("100");
		when(request.getParameter("y")).thenReturn("10");
		when(request.getParameter("width")).thenReturn("100");
		when(request.getParameter("height")).thenReturn("50");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}
	
	/**
	 * <p>doPost メソッドの異常系テスト：トリミング範囲が画像外の場合（y 座標 + 高さ が画像高さを超える）。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError10() throws Exception {
		// 準備：ダミー画像を作成
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		InputStream imageInputStream = new ByteArrayInputStream(baos.toByteArray());
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getInputStream()).thenReturn(imageInputStream);
		when(imagePart.getSize()).thenReturn((long)baos.size());
		when(request.getParameter("x")).thenReturn("10");
		when(request.getParameter("y")).thenReturn("100");
		when(request.getParameter("width")).thenReturn("50");
		when(request.getParameter("height")).thenReturn("100");
		when(request.getRequestDispatcher("/function/exceptionMessage.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		trimmingServlet.doPost(request, response);
		
		//
		// 検証
		//
		verify(request).setAttribute(eq("exception"), contains("トリミング範囲が不正です"));
		verify(dispatcher).forward(request, response);
	}

}
