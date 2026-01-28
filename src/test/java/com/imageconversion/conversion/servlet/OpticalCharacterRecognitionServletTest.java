package com.imageconversion.conversion.servlet;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.imageconversion.common.utils.FileToPart;

/**
 * <p>{@link OpticalCharacterRecognitionServlet} のテストクラス。</p>
 * 
 * <h4>doPost メソッド</h4>
 * <ul>
 * <li>{@link #doPostSuccess01}正常系：画像ファイルからOCR結果を取得する</li>
 * <li>{@link #doPostError01}異常系：画像ファイルが選択されていない場合</li>
 * <li>{@link #doPostError02}異常系：無効な画像ファイルが指定された場合</li>
 * </ul>
 */
public class OpticalCharacterRecognitionServletTest {
	
	private OpticalCharacterRecognitionServlet servlet;
	
	private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher dispatcher;
    
    /**
     * <p>テスト前処理。</p>
     */
    @BeforeEach
    public void setUp() {
		servlet = new OpticalCharacterRecognitionServlet();
    	request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        dispatcher = mock(RequestDispatcher.class);
    }

    /**
     * <p>正常系：画像ファイルからOCR結果を取得する。</p>
     * 
     * @throws Exception 想定外のエラーが発生した場合
     */
	@Test
	public void doPostSuccess01() throws Exception {
		// 準備
		String testImagePath = "src/test/resources/OCR_test01.png";
		File inputFile = new File(testImagePath);
		Part imagePart = FileToPart.fromFile("upload", inputFile);
		
		// モックの設定
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(request.getRequestDispatcher("/function/opticalCharacterRecognition.jsp")).thenReturn(dispatcher);
		
		//
		// 実行
		//
		servlet.doPost(request, response);
		
		//
		// 検証
		//
		String expected = "光学文字認識 (こうがくもじにんしき、英: Opticalcharacter recognition) は、活\n"
				+ "字、手書きテキストの画像を文字コードの列に変換するソフトウェアである。 画像は\n"
				+ "イメージスキャナーや写真で取り込まれた文書、 風景写真(風景内の看板の文字な\n"
				+ "ど) 、 画像内の字幕 (テレビ放送画像内など) が使われる貴。 一般にOCRと中記され\n"
				+ "る。\n";
		verify(request).setAttribute(eq("ocrResult"), eq(expected));
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
		verify(response).sendRedirect("/function/opticalCharacterRecognition.jsp");
	}
	
	/**
	 * <p>異常系：無効な画像ファイルが指定された場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void doPostError02() throws Exception {
		// モックの設定
		Part imagePart = mock(Part.class);
		when(request.getPart("imageFile")).thenReturn(imagePart);
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getSubmittedFileName()).thenReturn("dummy.png");
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
