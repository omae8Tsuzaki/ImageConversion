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
 * <p>{@link TrimmingController} のテストクラス。</p>
 *
 * <h4>trimming メソッド</h4>
 * <ul>
 *  <li>{@link #trimmingSuccess01} 正常系：画像のトリミングが成功した場合</li>
 *  <li>{@link #trimmingError01} 異常系：画像ファイルが選択されていない場合</li>
 *  <li>{@link #trimmingError02} 異常系：無効な画像データが送信された場合</li>
 *  <li>{@link #trimmingError03} 異常系：トリミング範囲が画像外の場合（x 座標が負の値）</li>
 *  <li>{@link #trimmingError04} 異常系：トリミング範囲が画像外の場合（y 座標が負の値）</li>
 *  <li>{@link #trimmingError05} 異常系：トリミング範囲が画像外の場合（x 座標 が画像幅を超える）</li>
 *  <li>{@link #trimmingError06} 異常系：トリミング範囲が画像外の場合（y 座標 が画像高さを超える）</li>
 *  <li>{@link #trimmingError07} 異常系：トリミング範囲が画像外の場合（幅が0以下）</li>
 *  <li>{@link #trimmingError08} 異常系：トリミング範囲が画像外の場合（高さが0以下）</li>
 *  <li>{@link #trimmingError09} 異常系：トリミング範囲が画像外の場合（x 座標 + 幅 が画像幅を超える）</li>
 *  <li>{@link #trimmingError10} 異常系：トリミング範囲が画像外の場合（y 座標 + 高さ が画像高さを超える）</li>
 * </ul>
 */
public class TrimmingControllerTest {

	private TrimmingController controller;

	private Part imagePart;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		controller = new TrimmingController();
		imagePart = mock(Part.class);
		model = new ExtendedModelMap();
	}

	/**
	 * <p>150x150 の有効なダミー画像を imagePart に設定する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	private void setUpValidImage() throws Exception {
		BufferedImage dummyImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);

		when(imagePart.getSize()).thenReturn((long) baos.size());
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream(baos.toByteArray()));
	}

	/**
	 * <p>正常系：画像のトリミングが成功した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingSuccess01() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "10", "100", "100", model);

		assertEquals("function/trimming", view);
		assertNotNull(model.getAttribute("base64Image"));
	}

	/**
	 * <p>異常系：画像ファイルが選択されていない場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError01() throws Exception {
		String view = controller.trimming(null, "10", "10", "100", "100", model);

		assertEquals("redirect:/function/trimming.jsp", view);
	}

	/**
	 * <p>異常系：無効な画像データが送信された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError02() throws Exception {
		when(imagePart.getSize()).thenReturn(100L);
		when(imagePart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

		String view = controller.trimming(imagePart, "10", "10", "100", "100", model);

		assertEquals("function/exceptionMessage", view);
		assertTrue(((String) model.getAttribute("exception")).contains("無効な画像ファイルです"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（x 座標が負の値）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError03() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "-1", "10", "50", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（y 座標が負の値）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError04() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "-1", "50", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（x 座標 が画像幅を超える）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError05() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "200", "10", "50", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（y 座標 が画像高さを超える）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError06() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "200", "50", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（幅が0以下）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError07() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "10", "0", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系:トリミング範囲が画像外の場合（高さが0以下）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError08() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "10", "50", "0", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（x 座標 + 幅 が画像幅を超える）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError09() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "100", "10", "100", "50", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：トリミング範囲が画像外の場合（y 座標 + 高さ が画像高さを超える）。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void trimmingError10() throws Exception {
		setUpValidImage();

		String view = controller.trimming(imagePart, "10", "100", "50", "100", model);

		assertEquals("function/exceptionMessage", view);
		assertEquals("トリミング範囲が不正です", model.getAttribute("exception"));
	}
}
