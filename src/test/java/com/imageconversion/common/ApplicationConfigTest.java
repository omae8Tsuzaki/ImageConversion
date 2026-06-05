package com.imageconversion.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * <p>{@link ApplicationConfig}のテストクラス。</p>
 */
@SpringBootTest
@ContextConfiguration(classes = ApplicationConfig.class)
public class ApplicationConfigTest {

	@Autowired
	private ApplicationConfig config;

	/**
	 * <p>正常系のテスト。</p>
	 */
	@Test
	public void toStringSuccess01() {

		// 実行・検証
		assertEquals(
				ApplicationConfig.class.getName()
				+ "["
					+ "uploadDir=C:/Download/, "
					+ "maxResizeDimension=10000"
				+ "]"
				, config.toString());
	}

}
