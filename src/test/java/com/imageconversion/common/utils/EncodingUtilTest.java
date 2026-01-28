package com.imageconversion.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link EncodingUtil}のテストクラス。</p>
 * 
 * <h4>convertToUTF8 メソッド</h4>
 * <ul>
 * <li>{@link #convertToUTF8Success01} 正常系：Shift_JISからUTF-8に変換する</li>
 * <li>{@link #convertToUTF8Success02} 正常系：入力がnullの場合、空文字列を返す</li>
 * </ul>
 */
public class EncodingUtilTest {
	
	/**
	 * <p>正常系：Shift_JISからUTF-8に変換する。</p>
	 */
	@Test
	public void convertToUTF8Success01() {
		String original = "テスト文字列";
		// Shift_JIS バイト列を ISO-8859-1 として読み込む
        byte[] sjisBytes = original.getBytes();
        String sjisAsIsoString = new String(sjisBytes, StandardCharsets.ISO_8859_1);
		
		//
		// 実行
		//
		String result = EncodingUtil.convertToUTF8(sjisAsIsoString);
		
		//
		// 検証
		//
		assertEquals(original, result);
	}
	
	/**
	 * <p>正常系：入力がnullの場合、空文字列を返す。</p>
	 */
	@Test
	public void convertToUTF8Success02() {
		String input = null;
		
		//
		// 実行
		//
		String result = EncodingUtil.convertToUTF8(input);
		
		//
		// 検証
		//
		assertEquals("", result);
	}

}
