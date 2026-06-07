package com.imageconversion.common.conversion.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.imageconversion.common.ApplicationConfig;
import com.imageconversion.common.conversion.logic.SaveImage;
import com.imageconversion.common.exception.LogicException;

/**
 * <p>{@link SaveImageController} のテストクラス。</p>
 *
 * <h4>save メソッド</h4>
 * <ul>
 *  <li>{@link #saveSuccess01} 正常系：画像の保存が成功した場合</li>
 *  <li>{@link #saveSuccess02} 正常系：base64Image の先頭にプレフィックスが付与されている場合</li>
 *  <li>{@link #saveError01} 異常系：base64Image が null の場合、戻り先へリダイレクトする</li>
 *  <li>{@link #saveError02} 異常系：extension が null の場合、戻り先へリダイレクトする</li>
 *  <li>{@link #saveError03} 異常系：backUrl が未指定の場合、デフォルトの戻り先へリダイレクトする</li>
 *  <li>{@link #saveError04} 異常系：保存処理で LogicException が発生した場合</li>
 * </ul>
 */
public class SaveImageControllerTest {

	/** アップロード先ディレクトリ。 */
	private static final String UPLOAD_DIR = "C:/Download/";

	private SaveImageController controller;

	private SaveImage saveImage;
	private ApplicationConfig applicationConfig;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		saveImage = mock(SaveImage.class);
		applicationConfig = mock(ApplicationConfig.class);
		model = new ExtendedModelMap();

		when(applicationConfig.getUploadDir()).thenReturn(UPLOAD_DIR);

		controller = new SaveImageController(saveImage, applicationConfig);
	}

	/**
	 * <p>正常系：画像の保存が成功した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveSuccess01() throws Exception {

		//
		// 事前準備
		//
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

		when(saveImage.saveImage(any(byte[].class), eq(UPLOAD_DIR), eq("testImage.jpg")))
				.thenReturn(UPLOAD_DIR + "testImage.jpg");

		//
		// 実行
		//
		String view = controller.save(base64Image, "jpg", "testImage.jpg", "/home/Menu.html", model);

		//
		// 検証
		//
		assertEquals("function/saveSuccess", view);
		assertEquals(UPLOAD_DIR + "testImage.jpg", model.getAttribute("saveImagePath"));
	}

	/**
	 * <p>正常系：base64Image の先頭に "data:image/png;base64," が付与されている場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveSuccess02() throws Exception {

		//
		// 事前準備
		//
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());

		when(saveImage.saveImage(any(byte[].class), eq(UPLOAD_DIR), eq("testImage.jpg")))
				.thenReturn(UPLOAD_DIR + "testImage.jpg");

		//
		// 実行
		//
		String view = controller.save(base64Image, "jpg", "testImage.jpg", "/home/Menu.html", model);

		//
		// 検証
		//
		assertEquals("function/saveSuccess", view);
		assertEquals(UPLOAD_DIR + "testImage.jpg", model.getAttribute("saveImagePath"));
	}

	/**
	 * <p>異常系：base64Image が null の場合、指定された戻り先へリダイレクトする。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveError01() throws Exception {

		//
		// 実行
		//
		String view = controller.save(null, "jpg", "testImage.jpg", "/home/Menu.html", model);

		//
		// 検証
		//
		assertEquals("redirect:/home/Menu.html", view);
		verify(saveImage, never()).saveImage(any(), anyString(), anyString());
	}

	/**
	 * <p>異常系：extension が null の場合、指定された戻り先へリダイレクトする。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveError02() throws Exception {

		//
		// 実行
		//
		String view = controller.save("data:image/png;base64......", null, "testImage.jpg", "/home/Menu.html", model);

		//
		// 検証
		//
		assertEquals("redirect:/home/Menu.html", view);
		verify(saveImage, never()).saveImage(any(), anyString(), anyString());
	}

	/**
	 * <p>異常系：backUrl が未指定の場合、デフォルトの戻り先へリダイレクトする。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveError03() throws Exception {

		//
		// 実行
		//
		String view = controller.save(null, null, null, null, model);

		//
		// 検証
		//
		assertEquals("redirect:../home/Menu.html", view);
		verify(saveImage, never()).saveImage(any(), anyString(), anyString());
	}

	/**
	 * <p>異常系：保存処理で LogicException が発生した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveError04() throws Exception {

		//
		// 事前準備
		//
		BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(dummyImage, "jpg", baos);
		String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

		when(saveImage.saveImage(any(byte[].class), eq(UPLOAD_DIR), eq("testImage.jpg")))
				.thenThrow(new LogicException("画像の保存に失敗しました。", new RuntimeException()));

		//
		// 実行
		//
		String view = controller.save(base64Image, "jpg", "testImage.jpg", "/home/Menu.html", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertNotNull(model.getAttribute("exception"));
	}
}
