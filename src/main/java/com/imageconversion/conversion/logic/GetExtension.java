package com.imageconversion.conversion.logic;

import java.util.List;

import com.imageconversion.common.enums.ImageExtension;

/**
 * <p>画像の拡張子を取得するロジックのインターフェース。</p>
 */
public interface GetExtension {
	
	/**
	 * <p>enumで定義した拡張子の一覧を取得する。</p>
	 * 
	 * @return 拡張子の文字列リスト
	 */
	public List<String> getExtensionList();
	
	/**
	 * <p>指定した拡張子文字列から ImageExtension を特定する。</p>
	 * 
	 * @param extParam 拡張子文字列（例："jpg"、"png" など）
	 * @return 対応する ImageExtension オブジェクト、存在しない場合は null
	 */
	public ImageExtension findImageExtension(String extParam);
}
