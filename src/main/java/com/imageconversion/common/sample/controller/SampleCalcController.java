package com.imageconversion.common.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.sample.logic.CalcImpl;

/**
 * <p>サンプルの足し算処理を行うコントローラー。</p>
 */
@Controller
public class SampleCalcController {

	/** 計算処理ロジック。 */
	private final CalcImpl calc;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param calc 計算処理ロジック
	 */
	public SampleCalcController(CalcImpl calc) {
		this.calc = calc;
	}

	/**
	 * <p>入力された2つの数値を加算し、結果を表示する。</p>
	 *
	 * @param num1Param 数値1（文字列）
	 * @param num2Param 数値2（文字列）
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@GetMapping("/function/sampleCalc")
	public String sampleCalc(
			@RequestParam(value = "num1", required = false) String num1Param,
			@RequestParam(value = "num2", required = false) String num2Param,
			Model model) {

		int num1 = 0;
		int num2 = 0;
		try {
			num1 = Integer.parseInt(num1Param);
			num2 = Integer.parseInt(num2Param);
		} catch (NumberFormatException e) {
			// 数値変換に失敗した場合はエラーメッセージを設定
			model.addAttribute("exception", "数値を入力してください");
			return "function/exceptionMessage";
		}

		int result = calc.add(num1, num2);

		model.addAttribute("result", result);
		return "function/sampleCalc";
	}
}
