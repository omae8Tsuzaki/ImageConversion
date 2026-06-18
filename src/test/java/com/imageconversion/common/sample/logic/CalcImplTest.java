package com.imageconversion.common.sample.logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * <p>{@link CalcImpl} のテストクラス。</p>
 * 
 * <ul>
 *  <li>{@link #addSuccess01}正常系：2つの整数を加算する</li>
 *  <li>{@link #addSuccess02}正常系：2つの小数を加算する</li>
 * </ul>
 */
public class CalcImplTest {

	/**
	 * <p>正常系：２つの整数を加算する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void addSuccess01() throws Exception {

		//
		// 事前準備
		//

		CalcImpl calc = new CalcImpl();
		int expected = 3;

		//
		// 実行
		//
		int result = calc.add(1, 2);

		//
		// 検証
		//
		assertEquals(expected, result);
	}

	/**
	 * <p>正常系：２つの小数を加算する。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void addSuccess02() throws Exception {

		//
		// 事前準備
		//

		CalcImpl calc = new CalcImpl();
		double expected = 3;

		//
		// 実行
		//
		double result = calc.add(1.0, 2.0);

		//
		// 検証
		//
		assertEquals(expected, result);
	}

}
