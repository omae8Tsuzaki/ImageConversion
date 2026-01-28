package com.imageconversion.conversion.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;

import com.imageconversion.common.exception.LogicException;

/**
 * <p>{@link OpticalCharacterRecognitionImpl} のテストクラス。</p>
 * 
 * <h4>resultOCR メソッド</h4>
 * <ul>
 * <li>{@link #resultOCRSuccess01}正常系：画像ファイルからOCR結果を取得する</li>
 * <li>{@link #resultOCRError01}異常系：存在しない言語コードを指定した場合</li>
 * </ul>
 */
public class OpticalCharacterRecognitionImplTest {
	
	// OCR結果の許容範囲（Levenshtein 距離）
	private static final int OCR_THRESHOLD = 10;

	/**
	 * <p>正常系：画像ファイルからOCR結果を取得する。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resultOCRSuccess01() throws Exception {
		File input = new File("src/test/resources/OCR_test01.png");
		if (!input.exists()) {
			throw new IllegalArgumentException("テストファイルが存在しません: " + input.getAbsolutePath());
		}
		
		//
		// 実行
		//
		OpticalCharacterRecognition ocrLogic = new OpticalCharacterRecognitionImpl();
		String result = ocrLogic.resultOCR(input, "jpn");
		System.out.println(result);
		
		//
		// 検証
		//
		String expected = "光学文字認識 (こうがくもじにんしき、英: Opticalcharacter recognition) は、活\r\n"
				+ "字、手書きテキストの画像を文字コードの列に変換するソフトウェアである。 画像は\r\n"
				+ "イメージスキャナーや写真で取り込まれた文書、 風景写真(風景内の看板の文字な\r\n"
				+ "ど) 、 画像内の字幕 (テレビ放送画像内など) が使われる。 一般にOCRと中記され\r\n"
				+ "る。\r\n";
		
		// Levenshtein 距離を計算して、OCR結果が許容範囲内かどうかを確認
		LevenshteinDistance distance = new LevenshteinDistance(OCR_THRESHOLD);
		int score = distance.apply(expected, result);
		
		assertTrue(score <= OCR_THRESHOLD, "OCR結果は許容範囲内です。Levenshtein 距離： " + score);
	}
	
	/**
	 * <p>異常系：存在しない言語コードを指定した場合。</p>
	 */
	@Test
	public void resultOCRError01() {
		File input = new File("src/test/resources/OCR_test01.png");
		if (!input.exists()) {
			throw new IllegalArgumentException("テストファイルが存在しません: " + input.getAbsolutePath());
		}
		
		//
		// 実行
		//
		OpticalCharacterRecognition ocrLogic = new OpticalCharacterRecognitionImpl();
		LogicException exception = 
				assertThrows(LogicException.class, () -> {
					ocrLogic.resultOCR(input, "XXX"); // 存在しない言語コードを指定
				});

		//
		// 検証
		//
		assertEquals("OCR処理中にエラーが発生しました。", exception.getMessage());
	}

}
