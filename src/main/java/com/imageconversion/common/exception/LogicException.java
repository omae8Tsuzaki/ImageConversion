package com.imageconversion.common.exception;

/**
 * <p>ロジックで送出する例外クラス</p>
 */
public class LogicException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * <p>指定された詳細メッセージおよび原因を使用して新規例外を生成します。</p>
	 * 
	 * @param message 例外の詳細メッセージ
	 * @param cause 例外インスタンスに付加する原因
	 */
	public LogicException(String message, Throwable cause) {
		super(message, cause);
	}

}
