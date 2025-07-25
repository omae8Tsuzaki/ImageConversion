package common.utils;

/**
 * <p>サニタイズ処理を行うユーティリティクラス。</p>
 */
public class Sanitize {
	
	private Sanitize() {
		// インスタンス化を防ぐためのコンストラクタ
	}
	
	/**
	 * <p>特殊文字をエスケープする．</p>
	 * 
	 * @param input エスケープ対象の文字列
	 * @return エスケープ後の文字列（nullは空文字として扱う）
	 */
	public static String escapeHtml(String input) {
		if (input == null) {
            return "";
        }
		return input
	            .replace("&", "&amp;")
	            .replace("<", "&lt;")
	            .replace(">", "&gt;")
	            .replace("\"", "&quot;")
	            .replace("'", "&#39;");
	}
	
	/**
	 * <p>文字列を整数に変換する．</p>
	 * 
	 * @param input 入力値（文字列）
	 * @return 変換後の整数値
	 */
	public static int parseStringToInt(String input) {
		if (input == null || input.isEmpty()) {
			return 0;
		}
		return Integer.parseInt(input);
	}
	
	/**
	 * <p>入力したファイル名から拡張子を取得する．</p>
	 * 
	 * @param fileName 入力ファイル名
	 * @return 拡張子（小文字）または空文字列
	 */
	public static String getFileExtension(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("File name cannot be null");
		}
	    int lastIndex = fileName.lastIndexOf('.');
	    if (lastIndex > 0 && lastIndex < fileName.length() - 1) {
	        return fileName.substring(lastIndex + 1).toLowerCase();
	    }
	    return "";
	}
}
