package com.imageconversion.common.conversion.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.imageconversion.common.conversion.logic.OpticalCharacterRecognitionImpl;
import com.imageconversion.common.utils.FileToPart;

/**
 * <p>{@link OpticalCharacterRecognitionController} のテストクラス。</p>
 *
 * <h4>opticalCharacterRecognition メソッド</h4>
 * <ul>
 *  <li>{@link #opticalCharacterRecognitionSuccess01} 正常系：画像ファイルから OCR 結果を取得する</li>
 *  <li>{@link #opticalCharacterRecognitionError01} 異常系：画像ファイルが選択されていない場合</li>
 *  <li>{@link #opticalCharacterRecognitionError02} 異常系：無効な画像ファイルが指定された場合</li>
 * </ul>
 */
public class OpticalCharacterRecognitionControllerTest {

	private OpticalCharacterRecognitionController controller;

	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		controller = new OpticalCharacterRecognitionController(new OpticalCharacterRecognitionImpl());
		model = new ExtendedModelMap();
	}

	/**
	 * <p>正常系：画像ファイルから OCR 結果を取得する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void opticalCharacterRecognitionSuccess01() throws Exception {

		//
		// 事前準備
		//
		File inputFile = new File("src/test/resources/OCR_test01.png");
		Part imagePart = FileToPart.fromFile("upload", inputFile);

		//
		// 実行
		//
		String view = controller.opticalCharacterRecognition(imagePart, model);

		//
		// 検証
		//
		String expected = "光学文字認識 (こうがくもじにんしき、英: Opticalcharacter recognition) は、活\n"
				+ "字、手書きテキストの画像を文字コードの列に変換するソフトウェアである。 画像は\n"
				+ "イメージスキャナーや写真で取り込まれた文書、 風景写真(風景内の看板の文字な\n"
				+ "ど) 、 画像内の字幕 (テレビ放送画像内など) が使われる貴。 一般にOCRと中記され\n"
				+ "る。\n";
		assertEquals("function/opticalCharacterRecognition", view);
		assertEquals(expected, model.getAttribute("ocrResult"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void opticalCharacterRecognitionError01() throws Exception {

		//
		// 実行
		//
		String view = controller.opticalCharacterRecognition(null, model);

		//
		// 検証
		//
		assertEquals("redirect:/function/opticalCharacterRecognition.jsp", view);
	}

	/**
	 * <p>異常系：無効な画像ファイルが指定された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void opticalCharacterRecognitionError02() throws Exception {

		//
		// 事前準備
		//
		Part imagePart = mock(Part.class);
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getSubmittedFileName()).thenReturn("dummy.png");
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

		//
		// 実行
		//
		String view = controller.opticalCharacterRecognition(imagePart, model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な画像ファイルです"));
	}
}
