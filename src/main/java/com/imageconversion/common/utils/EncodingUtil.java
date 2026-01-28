package com.imageconversion.common.utils;

import java.nio.charset.StandardCharsets;
/**
 * <p>文字エンコーディングに関するユーティリティクラス。</p>
 */
public class EncodingUtil {
	
	private EncodingUtil() {
		// インスタンス化を防ぐためのコンストラクタ
	}

	/**
	 * <p>Shift_JISからUTF-8に変換する．</p>
	 * 
	 * @param input 変換対象の文字列（Shift_JISエンコード）
	 * @return 変換後の文字列（UTF-8エンコード）
	 */
	public static String convertToUTF8(String input) {
		// 入力がnullの場合は空文字列を返す
		if (input == null) {
			return "";
        }
        
        // Shift_JISからUTF-8に変換
        return new String(input.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
