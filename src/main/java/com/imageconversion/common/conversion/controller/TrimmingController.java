package com.imageconversion.common.conversion.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.utils.FileValidator;
import com.imageconversion.common.utils.Sanitize;

/**
 * <p>画像のトリミングを行うコントローラー。</p>
 */
@Controller
public class TrimmingController {

	/**
	 * <p>アップロードされた画像を指定された範囲でトリミングし、結果を表示する。</p>
	 *
	 * @param imageFile アップロードされた画像ファイル
	 * @param xParam トリミング開始X座標（文字列）
	 * @param yParam トリミング開始Y座標（文字列）
	 * @param widthParam トリミング幅（文字列）
	 * @param heightParam トリミング高さ（文字列）
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/trimming")
	public String trimming(
			@RequestParam(value = "imageFile", required = false) Part imageFile,
			@RequestParam("x") String xParam,
			@RequestParam("y") String yParam,
			@RequestParam("width") String widthParam,
			@RequestParam("height") String heightParam,
			Model model) {

		// 入力確認
		if (FileValidator.isEmptyFilePart(imageFile)) {
			return "redirect:/function/trimming.jsp";
		}

		int x = Sanitize.parseStringToInt(xParam);
		int y = Sanitize.parseStringToInt(yParam);
		int width = Sanitize.parseStringToInt(widthParam);
		int height = Sanitize.parseStringToInt(heightParam);

		try {
			BufferedImage originalImage = FileValidator.readImage(imageFile);

			// トリミング範囲のチェック
			if (x < 0 || y < 0 ||
				originalImage.getWidth() < x ||
				originalImage.getHeight() < y ||
				width <= 0 || height <= 0 ||
				x + width > originalImage.getWidth() ||
				y + height > originalImage.getHeight()) {

				model.addAttribute("exception", "トリミング範囲が不正です");
				return "function/exceptionMessage";
			}

			// トリミング処理
			BufferedImage trimingImage = originalImage.getSubimage(x, y, width, height);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(trimingImage, "jpg", baos);
			String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

			model.addAttribute("base64Image", base64Image);
			return "function/trimming";
		} catch (IOException e) {
			model.addAttribute("exception", e.getMessage());
			return "function/exceptionMessage";
		}
	}
}
