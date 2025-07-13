package utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link Sanitize}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #parseStringToIntSucces01}正常系：数字が入力された場合</li>
 * <li>{@link #parseStringToIntSucces02}正常系：nullが入力された場合</li>
 * <li>{@link #parseStringToIntSucces03}正常系：空文字が入力された場合</li>
 * <li>{@link #parseStringToIntError01}異常系：数値以外の文字列が入力された場合</li>
 * </ul>
 */
public class SanitizeTest {

	/**
	 * <p>{@link Sanitize#parseStringToInt}の正常系テスト。</p>
	 * <p>数値が入力された場合</p>
	 */
	@Test
	public void parseStringToIntSucces01() {
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
	public void parseStringToIntSucces02() {
		String input = null;
		// 実行
		int result = Sanitize.parseStringToInt(input);
		
		// 検証
		int expected = 0;
		assertEquals(expected, result);
	}
	
	@Test
	public void parseStringToIntSucces03() {
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

}
