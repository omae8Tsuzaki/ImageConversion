package com.imageconversion.common.conversion.controller;

import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.ApplicationConfig;
import com.imageconversion.common.conversion.logic.SaveImage;
import com.imageconversion.common.exception.LogicException;

/**
 * <p>画像の保存を行うコントローラー。</p>
 */
@Controller
public class SaveImageController {

	/** デフォルトの戻り先 URL。 */
	private static final String DEFAULT_BACK_URL = "../home/Menu.html";

	/** 画像保存処理ロジック。 */
	private final SaveImage saveImage;

	/** アプリケーション共通設定。 */
	private final ApplicationConfig applicationConfig;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param saveImage 画像保存処理ロジック
	 * @param applicationConfig アプリケーション共通設定
	 */
	public SaveImageController(SaveImage saveImage, ApplicationConfig applicationConfig) {
		this.saveImage = saveImage;
		this.applicationConfig = applicationConfig;
	}

	/**
	 * <p>Base64 エンコードされた画像をデコードし、指定ディレクトリへ保存する。</p>
	 *
	 * @param base64Image Base64 エンコードされた画像データ
	 * @param extension 画像の拡張子
	 * @param fileName 保存するファイル名
	 * @param backUrl 遷移前の URL（保存対象が無い場合の戻り先）
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/saveImage")
	public String save(
			@RequestParam(value = "base64Image", required = false) String base64Image,
			@RequestParam(value = "extension", required = false) String extension,
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "backUrl", required = false) String backUrl,
			Model model) {

		// 保存対象が無い場合は元の画面に戻る
		if (base64Image == null || extension == null) {
			if (backUrl == null || backUrl.isEmpty()) {
				backUrl = DEFAULT_BACK_URL;
			}
			return "redirect:" + backUrl;
		}

		// "data:image/png;base64," のようなプレフィックスを除去
		if (base64Image.startsWith("data:image")) {
			base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
		}

		// デコード
		byte[] imageBytes = Base64.getDecoder().decode(base64Image);

		try {
			// 指定したディレクトリに画像を保存
			String saveImagePath = saveImage.saveImage(imageBytes, applicationConfig.getUploadDir(), fileName);
			model.addAttribute("saveImagePath", saveImagePath);
			return "function/saveSuccess";
		} catch (LogicException e) {
			model.addAttribute("exception", e);
			return "function/exceptionMessage";
		}
	}
}
