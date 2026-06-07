package com.imageconversion.common.conversion.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.conversion.logic.GetExtension;
import com.imageconversion.common.enums.ImageExtension;
import com.imageconversion.common.utils.FileValidator;
import com.imageconversion.common.utils.Sanitize;

/**
 * <p>画像の拡張子を変更するコントローラー。</p>
 */
@Controller
public class ChangeExtensionController {

	/** 拡張子取得処理ロジック。 */
	private final GetExtension getExtension;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param getExtension 拡張子取得処理ロジック
	 */
	public ChangeExtensionController(GetExtension getExtension) {
		this.getExtension = getExtension;
	}

	/**
	 * <p>アップロードされた画像を指定された拡張子へ変換し、結果を表示する。</p>
	 *
	 * @param imageFile アップロードされた画像ファイル
	 * @param newExtension 変換後の拡張子
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 */
	@PostMapping("/function/changeExtension")
	public String changeExtension(
			@RequestParam(value = "imageFile", required = false) Part imageFile,
			@RequestParam("extension") String newExtension,
			Model model) {

		// 入力確認
		if (FileValidator.isEmptyFilePart(imageFile)) {
			return "redirect:/function/changeExtension.jsp";
		}

		// 拡張子確認
		String fileExtension = Sanitize.getFileExtension(imageFile.getSubmittedFileName());
		if (!ImageExtension.isValidExtension(fileExtension)) {
			model.addAttribute("exception", "無効な拡張子です: " + fileExtension);
			return "function/exceptionMessage";
		}

		// 拡張子一覧を取得
		List<String> extensions = getExtension.getExtensionList();

		try {
			BufferedImage originalImage = FileValidator.readImage(imageFile);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, newExtension, baos);
			String base64Image = Base64.getEncoder().encodeToString(baos.toByteArray());

			// 新しいファイルの名前
			String fileName = imageFile.getSubmittedFileName();
			if (fileName.lastIndexOf('.') != -1) {
				fileName = fileName.substring(0, fileName.lastIndexOf('.')) + "." + newExtension;
			} else {
				fileName += "." + newExtension;
			}

			model.addAttribute("base64Image", base64Image);
			model.addAttribute("oldExtension", fileExtension);
			model.addAttribute("newExtension", newExtension);
			model.addAttribute("extensions", extensions);
			model.addAttribute("fileName", fileName);
			return "function/changeExtension";
		} catch (IOException e) {
			model.addAttribute("exception", e.getMessage());
			return "function/exceptionMessage";
		}
	}
}
