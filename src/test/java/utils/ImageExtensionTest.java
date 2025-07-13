package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link ImageExtension}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #isValidExtensionSucces01}正常系：有効な拡張子が入力された場合</li>
 * <li>{@link #isValidExtensionError01}異常系：無効な拡張子が入力された場合</li>
 * </ul>
 */
public class ImageExtensionTest {

	/**
	 * <p>{@link ImageExtension#isValidExtension}の正常系テスト。</p>
	 * <p>有効な拡張子が入力された場合</p>
	 */
	@Test
	public void isValidExtensionSucces01() {
		// 正常系：有効な拡張子
		String validExtension = "jpg";
		// 実行
		boolean result = ImageExtension.isValidExtension(validExtension);
		// 検証
		assertTrue(result);
	}
	
	/**
	 * <p>{@link ImageExtension#isValidExtension}の異常系テスト。</p>
	 * <p>無効な拡張子が入力された場合</p>
	 */
	@Test
	public void isValidExtensionError01() {
		// 正常系：有効な拡張子
		String validExtension = "pdf";
		// 実行
		boolean result = ImageExtension.isValidExtension(validExtension);
		// 検証
		assertFalse(result);
	}

}
