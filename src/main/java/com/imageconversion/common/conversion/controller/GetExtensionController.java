package com.imageconversion.common.conversion.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.imageconversion.common.conversion.logic.GetExtension;

/**
 * <p>画像の拡張子一覧を取得するコントローラー。</p>
 */
@Controller
public class GetExtensionController {

	/** 拡張子取得処理ロジック。 */
	private final GetExtension getExtension;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param getExtension 拡張子取得処理ロジック
	 */
	public GetExtensionController(GetExtension getExtension) {
		this.getExtension = getExtension;
	}

	/**
	 * <p>拡張子一覧を取得して拡張子変換画面に渡す。</p>
	 *
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@GetMapping("/function/getExtension")
	public String getExtensionByGet(Model model) {
		return setExtensions(model);
	}

	/**
	 * <p>拡張子一覧を取得して拡張子変換画面に渡す。</p>
	 *
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/getExtension")
	public String getExtensionByPost(Model model) {
		return setExtensions(model);
	}

	/**
	 * <p>拡張子一覧をモデルに設定する。</p>
	 *
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	private String setExtensions(Model model) {
		List<String> extensions = getExtension.getExtensionList();
		model.addAttribute("extensions", extensions);
		return "function/changeExtension";
	}
}
