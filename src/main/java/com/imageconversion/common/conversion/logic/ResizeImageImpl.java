package com.imageconversion.common.conversion.logic;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.imageconversion.common.exception.LogicException;

/**
 * <p>画像をリサイズするロジックの実装クラス。</p>
 */
@Service
public class ResizeImageImpl implements ResizeImage {

	// ログ出力の設定
	private static final Logger LOGGER = LoggerFactory.getLogger(ResizeImageImpl.class);

	/**
	 * <p>画像を指定された幅・高さにリサイズし、JPEG 形式のバイト配列を返す。</p>
	 *
	 * @param originalImage リサイズ対象の元画像
	 * @param width リサイズ後の幅（ピクセル）
	 * @param height リサイズ後の高さ（ピクセル）
	 * @return リサイズ後の JPEG 形式のバイト配列
	 * @throws LogicException リサイズまたは書き込みに失敗した場合
	 */
	@Override
	public byte[] resize(BufferedImage originalImage, int width, int height) throws LogicException {
		try {
			// リサイズ処理
			BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(originalImage, 0, 0, width, height, null);
			g2d.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "jpg", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			LOGGER.error("画像のリサイズに失敗しました。");
			throw new LogicException("画像のリサイズに失敗しました。", e);
		}
	}
}
