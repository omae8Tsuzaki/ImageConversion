package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link ImageExtension}のテストクラス。</p>
 * 
 * <h4>{@link ImageExtension#isValidExtension}メソッド</h4>
 * <ul>
 * <li>{@link #isValidExtensionSuccess01}正常系：有効な拡張子が入力された場合</li>
 * <li>{@link #isValidExtensionError01}異常系：無効な拡張子が入力された場合</li>
 * <li>{@link #isValidExtensionError02}異常系：入力がnullの場合</li>
 * </ul>
 * 
 * <h4>{@link ImageExtension#getExtension}メソッド</h4>
 * <ul>
 * <li>{@link #getExtensionSuccess01}正常系：各拡張子が正しく取得できるか検証</li>
 * </ul>
 */
public class ImageExtensionTest {

	/**
	 * <p>{@link ImageExtension#isValidExtension}の正常系テスト。</p>
	 * <p>有効な拡張子が入力された場合</p>
	 */
	@Test
	public void isValidExtensionSuccess01() {
		// 正常系：有効な拡張子
		String validExtension = "jpg";
		// 実行
		boolean result = ImageExtension.isValidExtension(validExtension);
		// 検証
		assertTrue(result);
		assertEquals(validExtension, ImageExtension.JPG.getExtension());
	}
	
	/**
	 * <p>{@link ImageExtension#isValidExtension}の異常系テスト。</p>
	 * <p>無効な拡張子が入力された場合</p>
	 */
	@Test
	public void isValidExtensionError01() {
		// 異常系：無効な拡張子
		String validExtension = "pdf";
		// 実行
		boolean result = ImageExtension.isValidExtension(validExtension);
		// 検証
		assertFalse(result);
	}
	
	/**
	 * <p>{@link ImageExtension#isValidExtension}の異常系テスト。</p>
	 * <p>入力がnullの場合</p>
	 */
	@Test
	public void isValidExtensionError02() {
		// 実行
		boolean result = ImageExtension.isValidExtension(null);
		// 検証
		assertFalse(result);
	}
	
	/**
	 * <p>{@link ImageExtension#getExtension}の正常系テスト。</p>
	 * <p>各拡張子が正しく取得できるか検証</p>
	 */
	@Test
	public void getExtensionSuccess01() {
		assertEquals("jpg", ImageExtension.JPG.getExtension());
		assertEquals("png", ImageExtension.PNG.getExtension());
		assertEquals("gif", ImageExtension.GIF.getExtension());
		assertEquals("bmp", ImageExtension.BMP.getExtension());
	}

}
