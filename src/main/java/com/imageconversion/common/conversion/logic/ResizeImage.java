package com.imageconversion.common.conversion.logic;

import java.awt.image.BufferedImage;

import com.imageconversion.common.exception.LogicException;

/**
 * <p>画像をリサイズするロジックのインターフェース。</p>
 */
public interface ResizeImage {

	/**
	 * <p>画像を指定された幅・高さにリサイズし、JPEG 形式のバイト配列を返す。</p>
	 *
	 * @param originalImage リサイズ対象の元画像
	 * @param width リサイズ後の幅（ピクセル）
	 * @param height リサイズ後の高さ（ピクセル）
	 * @return リサイズ後の JPEG 形式のバイト配列
	 * @throws LogicException リサイズまたは書き込みに失敗した場合
	 */
	public byte[] resize(BufferedImage originalImage, int width, int height) throws LogicException;
}
