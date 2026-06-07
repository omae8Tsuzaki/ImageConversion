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

import com.imageconversion.common.conversion.logic.GetExtensionImpl;

/**
 * <p>{@link ChangeExtensionController} のテストクラス。</p>
 *
 * <h4>changeExtension メソッド</h4>
 * <ul>
 *  <li>{@link #changeExtensionSuccess01} 正常系：画像ファイルが選択され、拡張子変更が成功した場合</li>
 *  <li>{@link #changeExtensionError01} 異常系：画像ファイルが選択されていない場合</li>
 *  <li>{@link #changeExtensionError02} 異常系：無効な拡張子が指定された場合</li>
 *  <li>{@link #changeExtensionError03} 異常系：無効な画像ファイルが指定された場合</li>
 * </ul>
 */
public class ChangeExtensionControllerTest {

	private ChangeExtensionController controller;

	private Part imagePart;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		controller = new ChangeExtensionController(new GetExtensionImpl());
		imagePart = mock(Part.class);
		model = new ExtendedModelMap();
	}

	/**
	 * <p>正常系：画像ファイルが選択され、拡張子変更が成功した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void changeExtensionSuccess01() throws Exception {

		//
		// 事前準備
		//
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "png", baos);

		when(imagePart.getSubmittedFileName()).thenReturn("testImage.png");
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream(baos.toByteArray()));

		//
		// 実行
		//
		String view = controller.changeExtension(imagePart, "jpg", model);

		//
		// 検証
		//
		assertEquals("function/changeExtension", view);
		assertNotNull(model.getAttribute("base64Image"));
		assertEquals("png", model.getAttribute("oldExtension"));
		assertEquals("jpg", model.getAttribute("newExtension"));
		assertEquals("testImage.jpg", model.getAttribute("fileName"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void changeExtensionError01() throws Exception {

		//
		// 実行
		//
		String view = controller.changeExtension(null, "jpg", model);

		//
		// 検証
		//
		assertEquals("redirect:/function/changeExtension.jsp", view);
	}

	/**
	 * <p>異常系：無効な拡張子が指定された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void changeExtensionError02() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getSubmittedFileName()).thenReturn("image.txt");

		//
		// 実行
		//
		String view = controller.changeExtension(imagePart, "png", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な拡張子"));
	}

	/**
	 * <p>異常系：無効な画像ファイルが指定された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void changeExtensionError03() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getSubmittedFileName()).thenReturn("image.png");
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

		//
		// 実行
		//
		String view = controller.changeExtension(imagePart, "jpg", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な画像ファイルです"));
	}
}
