package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link Sanitize}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #parseStringToIntSuccess01}正常系：数字が入力された場合</li>
 * <li>{@link #parseStringToIntSuccess02}正常系：nullが入力された場合</li>
 * <li>{@link #parseStringToIntSuccess03}正常系：空文字が入力された場合</li>
 * <li>{@link #parseStringToIntError01}異常系：数値以外の文字列が入力された場合</li>
 * <li>{@link #getFileExtensionSuccess01}正常系：ファイル名が入力された場合</li>
 * <li>{@link #getFileExtensionSuccess02}正常系：ファイル名以外が入力された場合</li>
 * </ul>
 */
public class SanitizeTest {

	/**
	 * <p>{@link Sanitize#parseStringToInt}の正常系テスト。</p>
	 * <p>数値が入力された場合</p>
	 */
	@Test
	public void parseStringToIntSuccess01() {
		String input = "1";
		// 実行
		int result = Sanitize.parseStringToInt(input);
		
		// 検証
		int expected = 1;
		assertEquals(expected, result);
	}
	
	/**
	 * <p>{@link Sanitize#parseStringToInt}の正常系テスト。</p>
	 * <p>nullが入力された場合</p>
	 */
	@Test
	public void parseStringToIntSuccess02() {
		String input = null;
		// 実行
		int result = Sanitize.parseStringToInt(input);
		
		// 検証
		int expected = 0;
		assertEquals(expected, result);
	}
	
	/**
	 * <p>{@link Sanitize#parseStringToInt}の正常系テスト。</p>
	 * <p>空文字が入力された場合</p>
	 */
	@Test
	public void parseStringToIntSuccess03() {
        String input = "";
        // 実行
        int result = Sanitize.parseStringToInt(input);
        
        // 検証
        int expected = 0;
        assertEquals(expected, result);
    }
	
	/**
	 * <p>{@link Sanitize#parseStringToInt}の異常系テスト。</p>
	 * <p>数値以外の文字列が入力された場合</p>
	 */
	@Test
	public void parseStringToIntError01() {
		String input = "abc";
		// 実行
		
		try {
			// 実行
	        Sanitize.parseStringToInt(input);
	        fail();
		} catch (NumberFormatException e) {
			// 検証
			assertEquals("For input string: \"" + input + "\"", e.getMessage());
		}
	}
	
	/**
	 * <p>{@link Sanitize#getFileExtension}の正常系テスト。</p>
	 * <p>ファイル名の場合</p>
	 */
	@Test
	public void getFileExtensionSuccess01() {
		String fileName = "test_image.jpg";
		String expected = "jpg";
		// 実行
		String result = Sanitize.getFileExtension(fileName);
		// 検証
		assertEquals(expected, result);
	}
	
	/**
	 * <p>{@link Sanitize#getFileExtension}の正常系テスト。</p>
	 * <p>ファイル名以外が入力された場合</p>
	 */
	@Test
	public void getFileExtensionSuccess02() {
		String notFileName = "abcdefg";
		String expected = "";
		// 実行
		String result = Sanitize.getFileExtension(notFileName);
		// 検証
		assertEquals(expected, result);
	}

}
