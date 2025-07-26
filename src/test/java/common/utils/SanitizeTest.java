package common.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link Sanitize}のテストクラス。</p>
 * 
 * <h4>escapeHtml メソッド</h4>
 * <ul>
 * <li>{@link #escapeHtmlSuccess01}正常系：HTMLタグが含まれる文字列が入力された場合</li>
 * <li>{@link #escapeHtmlSuccess02}正常系：& が含まれる文字列が入力された場合</li>
 * <li>{@link #escapeHtmlSuccess03}正常系：null が入力された場合</li>
 * </ul>
 * 
 * <h4>parseStringToInt メソッド</h4>
 * <ul>
 * <li>{@link #parseStringToIntSuccess01}正常系：数字が入力された場合</li>
 * <li>{@link #parseStringToIntSuccess02}正常系：nullが入力された場合</li>
 * <li>{@link #parseStringToIntSuccess03}正常系：空文字が入力された場合</li>
 * <li>{@link #parseStringToIntError01}異常系：数値以外の文字列が入力された場合</li>
 * </ul>
 * 
 * <h4>getFileExtension メソッド</h4>
 * <ul>
 * <li>{@link #getFileExtensionSuccess01}正常系：ファイル名が入力された場合</li>
 * <li>{@link #getFileExtensionSuccess02}正常系：ファイル名以外が入力された場合</li>
 * <li>{@link #getFileExtensionSuccess03}正常系：ファイル名が拡張子なしの場合</li>
 * <li>{@link #getFileExtensionError01}異常系：nullが入力された場合</li>
 * </ul>
 */
public class SanitizeTest {

	/**
	 * <p>正常系：HTMLタグが含まれる文字列が入力された場合。</p>
	 */
	@Test
	public void escapeHtmlSuccess01() {
		String input = "<script>alert('XSS');</script>";
		String expected = "&lt;script&gt;alert(&#39;XSS&#39;);&lt;/script&gt;";

		//
		// 実行
		//
		String result = Sanitize.escapeHtml(input);

		//
		// 検証
		//
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：& が含まれる文字列が入力された場合。</p>
	 */
	@Test
	public void escapeHtmlSuccess02() {
		String input = "Hello & World";
		String expected = "Hello &amp; World";

		//
		// 実行
		//
		String result = Sanitize.escapeHtml(input);

		//
		// 検証
		//
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：null が入力された場合。</p>
	 */
	@Test
	public void escapeHtmlSuccess03() {
		String input = null;
		String expected = "";

		//
		// 実行
		//
		String result = Sanitize.escapeHtml(input);

		//
		// 検証
		//
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：数値が入力された場合。</p>
	 */
	@Test
	public void parseStringToIntSuccess01() {
		String input = "1";
		
		//
		// 実行
		//
		int result = Sanitize.parseStringToInt(input);
		
		//
		// 検証
		//
		int expected = 1;
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：null が入力された場合。</p>
	 */
	@Test
	public void parseStringToIntSuccess02() {
		String input = null;
		
		//
		// 実行
		//
		int result = Sanitize.parseStringToInt(input);
		
		//
		// 検証
		//
		int expected = 0;
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系テスト：空文字が入力された場合。</p>
	 */
	@Test
	public void parseStringToIntSuccess03() {
        String input = "";
        
        //
        // 実行
        //
        int result = Sanitize.parseStringToInt(input);
        
        //
        // 検証
        //
        int expected = 0;
        assertEquals(expected, result);
    }
	
	/**
	 * <p>異常系：数値以外の文字列が入力された場合。</p>
	 */
	@Test
	public void parseStringToIntError01() {
		String input = "abc";
		
		try {
			//
			// 実行
			//
	        Sanitize.parseStringToInt(input);
	        fail();
		} catch (NumberFormatException e) {
			//
			// 検証
			//
			assertEquals("For input string: \"" + input + "\"", e.getMessage());
		}
	}
	
	/**
	 * <p>正常系：ファイル名の場合。</p>
	 */
	@Test
	public void getFileExtensionSuccess01() {
		String fileName = "test_image.jpg";
		String expected = "jpg";
		
		//
		// 実行
		//
		String result = Sanitize.getFileExtension(fileName);
		
		//
		// 検証
		//
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：ファイル名以外が入力された場合。</p>
	 */
	@Test
	public void getFileExtensionSuccess02() {
		String notFileName = "abcdefg";
		String expected = "";
		
		//
		// 実行
		//
		String result = Sanitize.getFileExtension(notFileName);
		
		//
		// 検証
		//
		assertEquals(expected, result);
	}
	
	/**
	 * <p>正常系：ファイル名が拡張子なしの場合。</p>
	 */
	@Test
	public void getFileExtensionSuccess03() {
		String fileName = "no_extension.";
		
		//
		// 実行
		//
		String result = Sanitize.getFileExtension(fileName);
		
		//
		// 検証
		//
		assertEquals("", result);
	}
	
	/**
	 * <p>異常系：nullが入力された場合。</p>
	 */
	@Test
	public void getFileExtensionError01() {
		String fileName = null;

		//
		// 実行
		//
		try {
			Sanitize.getFileExtension(fileName);
			fail();
		} catch (IllegalArgumentException e) {
			//
			// 検証
			//
			assertEquals("File name cannot be null", e.getMessage());
		}
	}

}
