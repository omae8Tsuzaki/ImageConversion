package com.imageconversion.common.conversion.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

import com.imageconversion.common.ApplicationConfig;
import com.imageconversion.common.conversion.logic.ResizeImage;

/**
 * <p>{@link ResizeController} のテストクラス。</p>
 *
 * <h4>resize メソッド</h4>
 * <ul>
 *  <li>{@link #resizeSuccess01}正常系：画像ファイルが選択され、リサイズが成功した場合</li>
 *  <li>{@link #resizeError01}異常系：画像ファイルが選択されていない場合</li>
 *  <li>{@link #resizeError02}異常系：無効な画像データが送信された場合</li>
 *  <li>{@link #resizeError03}異常系：幅と高さが数値以外の入力の場合</li>
 *  <li>{@link #resizeError04}異常系：幅または高さが許容範囲外（過大）の場合</li>
 *  <li>{@link #resizeError05}異常系：幅または高さが1未満の場合</li>
 * </ul>
 */
public class ResizeControllerTest {

	/** リサイズ後に許容する最大の幅・高さ（ピクセル）。 */
	private static final int MAX_DIMENSION = 10000;

	private ResizeController controller;

	private ResizeImage resizeImage;
	private ApplicationConfig applicationConfig;
	private Part imagePart;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		resizeImage = mock(ResizeImage.class);
		applicationConfig = mock(ApplicationConfig.class);
		imagePart = mock(Part.class);
		model = new ExtendedModelMap();

		when(applicationConfig.getMaxResizeDimension()).thenReturn(MAX_DIMENSION);

		controller = new ResizeController(resizeImage, applicationConfig);
	}

	/**
	 * <p>正常系：画像ファイルが選択され、リサイズが成功した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeSuccess01() throws Exception {

		//
		// 事前準備
		//

		// 有効な BufferedImage を動的に生成
		BufferedImage testImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(testImage, "png", baos);

		when(imagePart.getSize()).thenReturn((long) baos.size());
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream(baos.toByteArray()));

		when(resizeImage.resize(any(BufferedImage.class), eq(100), eq(100)))
				.thenReturn(new byte[] { 1, 2, 3 });

		//
		// 実行
		//
		String view = controller.resize(imagePart, "100", "100", model);

		//
		// 検証
		//
		assertEquals("function/resize", view);
		assertNotNull(model.getAttribute("base64Image"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeError01() throws Exception {

		//
		// 実行
		//
		String view = controller.resize(null, "100", "100", model);

		//
		// 検証
		//
		assertEquals("redirect:/function/resize.jsp", view);
		verify(resizeImage, never()).resize(any(), anyInt(), anyInt());
	}

	/**
	 * <p>異常系：無効な画像データが送信された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeError02() throws Exception {

		//
		// 事前準備
		//

		// 無効な画像データ
		when(imagePart.getSize()).thenReturn(10L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

		//
		// 実行
		//
		String view = controller.resize(imagePart, "100", "100", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な画像"));
	}

	/**
	 * <p>異常系：幅と高さが数値以外の入力の場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeError03() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);

		//
		// 実行
		//
		String view = controller.resize(imagePart, "abc", "abc", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertEquals("幅と高さは数値で入力してください", model.getAttribute("exception"));
		// 画像処理に進まないこと
		verify(imagePart, never()).getInputStream();
	}

	/**
	 * <p>異常系：幅または高さが許容範囲を超える（過大な）場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeError04() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);

		//
		// 実行
		//
		String view = controller.resize(imagePart, "100000", "100", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("以下で入力してください"));
		// 画像処理に進まないこと
		verify(imagePart, never()).getInputStream();
		verify(resizeImage, never()).resize(any(), anyInt(), anyInt());
	}

	/**
	 * <p>異常系：幅または高さが1未満の場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void resizeError05() throws Exception {

		//
		// 事前準備
		//
		when(imagePart.getSize()).thenReturn(100L);

		//
		// 実行
		//
		String view = controller.resize(imagePart, "0", "-5", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("1以上"));
		// 画像処理に進まないこと
		verify(imagePart, never()).getInputStream();
		verify(resizeImage, never()).resize(any(), anyInt(), anyInt());
	}
}
