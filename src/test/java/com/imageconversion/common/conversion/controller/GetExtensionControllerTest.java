package com.imageconversion.common.conversion.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.imageconversion.common.conversion.logic.GetExtension;
import com.imageconversion.common.conversion.logic.GetExtensionImpl;

/**
 * <p>{@link GetExtensionController} のテストクラス。</p>
 *
 * <h4>getExtensionByGet メソッド</h4>
 * <ul>
 *  <li>{@link #getExtensionByGetSuccess01} 正常系：拡張子一覧を取得してモデルに設定する</li>
 * </ul>
 *
 * <h4>getExtensionByPost メソッド</h4>
 * <ul>
 *  <li>{@link #getExtensionByPostSuccess01} 正常系：拡張子一覧を取得してモデルに設定する</li>
 * </ul>
 */
public class GetExtensionControllerTest {

	private GetExtensionController controller;

	private GetExtension logic;
	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		logic = new GetExtensionImpl();
		controller = new GetExtensionController(logic);
		model = new ExtendedModelMap();
	}

	/**
	 * <p>正常系：GET で拡張子一覧を取得してモデルに設定する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void getExtensionByGetSuccess01() throws Exception {

		//
		// 実行
		//
		String view = controller.getExtensionByGet(model);

		//
		// 検証
		//
		assertEquals("function/changeExtension", view);
		assertEquals(logic.getExtensionList(), model.getAttribute("extensions"));
	}

	/**
	 * <p>正常系：POST で拡張子一覧を取得してモデルに設定する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void getExtensionByPostSuccess01() throws Exception {

		//
		// 実行
		//
		String view = controller.getExtensionByPost(model);

		//
		// 検証
		//
		assertEquals("function/changeExtension", view);
		assertEquals(logic.getExtensionList(), model.getAttribute("extensions"));
	}
}
