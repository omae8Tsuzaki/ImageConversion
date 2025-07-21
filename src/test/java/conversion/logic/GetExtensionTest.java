package conversion.logic;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import utils.ImageExtension;

/**
 * <p>{@link GetExtension}のテストクラス。</p>
 * 
 * <h4>{@link GetExtensionImpl#getExtensionList} のテスト</h4>
 * <ul>
 * <li>{@link #getExtensionListSuccess01} 正常系：拡張子の一覧が取得できる</li>
 * </ul>
 * 
 * <h4>{@link GetExtensionImpl#findImageExtension} のテスト</h4>
 * <ul>
 * <li>{@link #findImageExtensionSuccess01} 正常系：指定した拡張子文字列から ImageExtension を特定できる</li>
 * <li>{@link #findImageExtensionError01} 異常系：引数がnullの場合</li>
 * <li>{@link #findImageExtensionError02} 異常系：引数が空文字列の場合</li>
 * </ul>
 */
public class GetExtensionTest {

	/**
	 * <p>正常系：拡張子一覧を取得できること。</p>
	 */
	@Test
	public void getExtensionListSuccess01() {
		GetExtension logic = new GetExtensionImpl();
		
		// 実行
		List<String> result = logic.getExtensionList();
		
		// 検証
		List<String> expected = List.of("jpg", "png", "gif", "bmp");
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：指定した拡張子文字列から ImageExtension を特定できること。</p>
	 */
	@Test
	public void findImageExtensionSuccess01() {
		GetExtension logic = new GetExtensionImpl();

		// 実行
		ImageExtension result = logic.findImageExtension("jpg");

		// 検証
		assertEquals(ImageExtension.JPG, result);
	}
	
	/**
	 * <p>異常系：引数がnullの場合。</p>
	 */
	@Test
	public void findImageExtensionError01() {
		GetExtension logic = new GetExtensionImpl();

		// 実行
		IllegalArgumentException expected = 
				assertThrows(IllegalArgumentException.class, () -> {
					logic.findImageExtension(null);
				});
		
		// 検証
		assertEquals("パラメータは null または空ではいけません。", expected.getMessage());
	}
	
	/**
	 * <p>異常系：引数が空文字列の場合。</p>
	 */
	@Test
	public void findImageExtensionError02() {
		GetExtension logic = new GetExtensionImpl();

		// 実行
		IllegalArgumentException expected = 
				assertThrows(IllegalArgumentException.class, () -> {
					logic.findImageExtension("");
				});
		
		// 検証
		assertEquals("パラメータは null または空ではいけません。", expected.getMessage());
	}

}
