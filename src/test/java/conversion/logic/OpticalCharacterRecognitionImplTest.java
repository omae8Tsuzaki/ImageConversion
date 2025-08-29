package conversion.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.junit.jupiter.api.Test;

import common.exception.LogicException;

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
	private static final int OCR_THRESHOLD = 20;

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
		
		//
		// 検証
		//
		String expected = "本社は東京都新宿区市谷加賀町。\r\n"
				+ "\r\n"
				+ "1876年 (明治9年) の創業以来の印刷技術と情報技術を強みとして、1950年代よ\r\n"
				+ "り他分野進出「拡印刷」事業を展開し[6]、建材分野へ進出したのに始まって、情\r\n"
				+ "報産業や生活産業のほか、ディスプレイや電子デバイスなどのエレクトロニクス\r\n"
				+ "分野にも進出し、世界トップシェアを獲得している製品もある[6]。最近では、環\r\n"
				+ "境、エネルギー、ライフサイエンス分野にも事業を拡大している。\r\n"
				+ "\r\n"
				+ "拠点は日本を中心にアメリカ全土やロンドン、パリ、オランダ、上海、台湾、シ\r\n"
				+ "ンガポール、シドニーなど全世界に広がる。";
		
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
