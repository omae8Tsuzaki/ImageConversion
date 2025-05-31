package utils;

public class Sanitize {
	
	/**
	 * <p>特殊文字をエスケープする．</p>
	 * @param input 入力値
	 * @return サニタイズした結果
	 */
	public static String sanitizeHtml(String input) {
//		if() {
//			
//		}
		return input.replaceAll("&","&amp;");
	}
}
