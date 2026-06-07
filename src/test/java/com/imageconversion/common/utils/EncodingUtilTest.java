package com.imageconversion.common.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link EncodingUtil} のテストクラス。</p>
 * 
 * <h4>convertToUTF8 メソッド</h4>
 * <ul>
 *  <li>{@link #convertToUTF8Success01} 正常系：Shift_JIS から UTF-8 に変換する</li>
 *  <li>{@link #convertToUTF8Success02} 正常系：入力が null の場合、空文字列を返す</li>
 * </ul>
 */
public class EncodingUtilTest {
	
	/**
	 * <p>正常系：Shift_JIS から UTF-8 に変換する。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void convertToUTF8Success01() throws Exception {
		
		//
		// 事前準備
		//
		
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
	 * <p>正常系：入力が null の場合、空文字列を返す。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void convertToUTF8Success02() throws Exception {
		
		//
		// 事前準備
		//
		
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
