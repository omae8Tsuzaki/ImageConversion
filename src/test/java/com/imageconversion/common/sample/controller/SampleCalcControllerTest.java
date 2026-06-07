package com.imageconversion.common.sample.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.imageconversion.common.sample.logic.CalcImpl;

/**
 * <p>{@link SampleCalcController} のテストクラス。</p>
 *
 * <h4>sampleCalc メソッド</h4>
 * <ul>
 *  <li>{@link #sampleCalcSuccess01} 正常系：数値を入力した場合、計算結果を表示する</li>
 *  <li>{@link #sampleCalcError01} 異常系：数値以外を入力した場合、エラーメッセージを表示する</li>
 *  <li>{@link #sampleCalcError02} 異常系：null が入力された場合、エラーメッセージを表示する</li>
 *  <li>{@link #sampleCalcError03} 異常系：空文字が入力された場合、エラーメッセージを表示する</li>
 * </ul>
 */
public class SampleCalcControllerTest {

	private SampleCalcController controller;

	private Model model;

	/**
	 * <p>テストの前処理。</p>
	 */
	@BeforeEach
	public void setUp() {
		controller = new SampleCalcController(new CalcImpl());
		model = new ExtendedModelMap();
	}

	/**
	 * <p>正常系：数値を入力した場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void sampleCalcSuccess01() throws Exception {

		//
		// 実行
		//
		String view = controller.sampleCalc("10", "20", model);

		//
		// 検証
		//
		assertEquals("function/sampleCalc", view);
		assertEquals(30, model.getAttribute("result"));
	}

	/**
	 * <p>異常系：数値以外が入力された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void sampleCalcError01() throws Exception {

		//
		// 実行
		//
		String view = controller.sampleCalc("10", "abc", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertEquals("数値を入力してください", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：null が入力された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void sampleCalcError02() throws Exception {

		//
		// 実行
		//
		String view = controller.sampleCalc(null, null, model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertEquals("数値を入力してください", model.getAttribute("exception"));
	}

	/**
	 * <p>異常系：空文字が入力された場合。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void sampleCalcError03() throws Exception {

		//
		// 実行
		//
		String view = controller.sampleCalc("", "", model);

		//
		// 検証
		//
		assertEquals("function/exceptionMessage", view);
		assertEquals("数値を入力してください", model.getAttribute("exception"));
	}
}
