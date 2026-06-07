package com.imageconversion.common.conversion.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;

import jakarta.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.ApplicationConfig;
import com.imageconversion.common.conversion.logic.ResizeImage;
import com.imageconversion.common.exception.LogicException;
import com.imageconversion.common.utils.FileValidator;

/**
 * <p>画像のリサイズを行うコントローラー。</p>
 */
@Controller
public class ResizeController {

	/** リサイズ処理ロジック。 */
	private final ResizeImage resizeImage;

	/** アプリケーション共通設定。 */
	private final ApplicationConfig applicationConfig;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param resizeImage リサイズ処理ロジック
	 * @param applicationConfig アプリケーション共通設定
	 */
	public ResizeController(ResizeImage resizeImage, ApplicationConfig applicationConfig) {
		this.resizeImage = resizeImage;
		this.applicationConfig = applicationConfig;
	}

	/**
	 * <p>アップロードされた画像を指定された幅・高さにリサイズし、結果を表示する。</p>
	 *
	 * @param imageFile アップロードされた画像ファイル
	 * @param widthParam リサイズ後の幅（文字列）
	 * @param heightParam リサイズ後の高さ（文字列）
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/resize")
	public String resize(
			@RequestParam(value = "imageFile", required = false) Part imageFile,
			@RequestParam("width") String widthParam,
			@RequestParam("height") String heightParam,
			Model model) {

		if (FileValidator.isEmptyFilePart(imageFile)) {
			return "redirect:/function/resize.jsp";
		}

		int newWidth = 0;
		int newHeight = 0;
		try {
			newWidth = Integer.parseInt(widthParam);
			newHeight = Integer.parseInt(heightParam);
		} catch (NumberFormatException e) {
			model.addAttribute("exception", "幅と高さは数値で入力してください");
			return "function/exceptionMessage";
		}

		// 寸法の範囲チェック（過大な値によるメモリ枯渇を防止）
		int maxDimension = applicationConfig.getMaxResizeDimension();
		if (newWidth < 1 || newHeight < 1 || newWidth > maxDimension || newHeight > maxDimension) {
			model.addAttribute("exception", "幅と高さは1以上" + maxDimension + "以下で入力してください");
			return "function/exceptionMessage";
		}

		try {
			BufferedImage originalImage = FileValidator.readImage(imageFile);

			byte[] imageBytes = resizeImage.resize(originalImage, newWidth, newHeight);
			String base64Image = Base64.getEncoder().encodeToString(imageBytes);

			model.addAttribute("base64Image", base64Image);
			return "function/resize";
		} catch (IOException | LogicException e) {
			model.addAttribute("exception", e.getMessage());
			return "function/exceptionMessage";
		}
	}
}
