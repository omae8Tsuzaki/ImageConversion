package logic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
/**
 * <p>{@link CalcImpl}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #addSucces01}正常系：2つの整数を加算する</li>
 * </ul>
 */
public class CalcImplTest {

	@Test
	public void addSucces01() {
		CalcImpl calc = new CalcImpl();
		// 実行
		int result = calc.add(1, 2);
		// 検証
		int expected = 3;
		assertEquals(expected, result);
	}

}
