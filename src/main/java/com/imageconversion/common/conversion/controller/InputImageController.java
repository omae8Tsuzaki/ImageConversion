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

/**
 * <p>画像の入力（アップロード表示）を行うコントローラー。</p>
 */
@Controller
public class InputImageController {

	/**
	 * <p>アップロードされた画像を読み込み、Base64 エンコードして表示する。</p>
	 *
	 * @param imageFile アップロードされた画像ファイル
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/inputImage")
	public String inputImage(
			@RequestParam(value = "imageFile", required = false) Part imageFile,
			Model model) {

		if (FileValidator.isEmptyFilePart(imageFile)) {
			return "redirect:/function/sampleConversion.jsp";
		}

		try {
			BufferedImage originalImage = FileValidator.readImage(imageFile);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos);
			String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

			model.addAttribute("base64Image", base64Image);
			return "function/sampleConversion";
		} catch (IOException e) {
			model.addAttribute("exception", e.getMessage());
			return "function/exceptionMessage";
		}
	}
}
