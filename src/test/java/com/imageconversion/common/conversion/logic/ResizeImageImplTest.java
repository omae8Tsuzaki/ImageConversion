package com.imageconversion.common.conversion.logic;


import static org.junit.jupiter.api.Assertions.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link ResizeImageImpl} のテストクラス。</p>
 *
 * <h4>resize メソッド</h4>
 * <ul>
 *  <li>{@link #resizeSuccess01}正常系：画像を縮小し、指定した寸法の JPEG が返る</li>
 *  <li>{@link #resizeSuccess02}正常系：画像を拡大し、指定した寸法の JPEG が返る</li>
 * </ul>
 */
public class ResizeImageImplTest {

	private ResizeImage logic = new ResizeImageImpl();

	private BufferedImage originalImage;

	/**
	 * <p>テストの前処理。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@BeforeEach
	public void setUp() throws Exception {
		// サンプル画像を作成
		originalImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * <p>正常系：画像を縮小し、指定した寸法の JPEG が返る。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeSuccess01() throws Exception {

		//
		// 実行
		//
		byte[] result = logic.resize(originalImage, 100, 50);

		//
		// 検証
		//
		assertNotNull(result);
		assertTrue(result.length > 0);

		BufferedImage resized = ImageIO.read(new ByteArrayInputStream(result));
		assertNotNull(resized);
		assertEquals(100, resized.getWidth());
		assertEquals(50, resized.getHeight());
	}

	/**
	 * <p>正常系：画像を拡大し、指定した寸法の JPEG が返る。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeSuccess02() throws Exception {

		//
		// 実行
		//
		byte[] result = logic.resize(originalImage, 400, 300);

		//
		// 検証
		//
		assertNotNull(result);
		assertTrue(result.length > 0);

		BufferedImage resized = ImageIO.read(new ByteArrayInputStream(result));
		assertNotNull(resized);
		assertEquals(400, resized.getWidth());
		assertEquals(300, resized.getHeight());
	}
}
