package com.imageconversion.common.conversion.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 * <p>{@link InputImageController} のテストクラス。</p>
 *
 * <h4>inputImage メソッド</h4>
 * <ul>
 *  <li>{@link #inputImageSuccess01} 正常系：画像をアップロードした場合</li>
 *  <li>{@link #inputImageError01} 異常系：画像ファイルが選択されていない場合</li>
 *  <li>{@link #inputImageError02} 異常系：無効な画像データが送信された場合</li>
 *  <li>{@link #inputImageError03} 異常系：画像ファイルが選択されているが、サイズが0の場合</li>
 * </ul>
 */
public class InputImageControllerTest {

	private InputImageController controller;

	private Part imagePart;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		controller = new InputImageController();
		imagePart = mock(Part.class);
		model = new ExtendedModelMap();
	}

	/**
	 * <p>正常系：画像をアップロードした場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void inputImageSuccess01() throws Exception {

		//
		// 事前準備
		//
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "png", baos);

		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream(baos.toByteArray()));

		//
		// 実行
		//
		String view = controller.inputImage(imagePart, model);

		//
		// 検証
		//
		assertEquals("function/sampleConversion", view);
		assertNotNull(model.getAttribute("base64Image"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void inputImageError01() throws Exception {

		//
		// 実行
		//
		String view = controller.inputImage(null, model);

		//
		// 検証
		//
		assertEquals("redirect:/function/sampleConversion.jsp", view);
	}

	/**
	 * <p>異常系：無効な画像データが送信された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void inputImageError02() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

		//
		// 実行
		//
		String view = controller.inputImage(imagePart, model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な画像ファイルです"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されているが、サイズが0の場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void inputImageError03() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(0L);

		//
		// 実行
		//
		String view = controller.inputImage(imagePart, model);

		//
		// 検証
		//
		assertEquals("redirect:/function/sampleConversion.jsp", view);
	}
}
