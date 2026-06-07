package com.imageconversion.common.conversion.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imageconversion.common.conversion.logic.OpticalCharacterRecognition;
import com.imageconversion.common.exception.LogicException;
import com.imageconversion.common.utils.FileValidator;

/**
 * <p>光学文字認識（OCR）を行うコントローラー。</p>
 */
@Controller
public class OpticalCharacterRecognitionController {

	/** 日本語固定の言語設定。 */
	private static final String LANGUAGE = "jpn";

	/** OCR処理ロジック。 */
	private final OpticalCharacterRecognition opticalCharacterRecognition;

	/**
	 * <p>依存オブジェクトを注入してインスタンスを生成する。</p>
	 *
	 * @param opticalCharacterRecognition OCR処理ロジック
	 */
	public OpticalCharacterRecognitionController(OpticalCharacterRecognition opticalCharacterRecognition) {
		this.opticalCharacterRecognition = opticalCharacterRecognition;
	}

	/**
	 * <p>アップロードされた画像からテキストを抽出し、結果を表示する。</p>
	 *
	 * @param imageFile アップロードされた画像ファイル
	 * @param model ビューへ渡す属性を格納するモデル
	 * @return 遷移先のビュー名
	 * @throws IOException 一時ファイルの操作に失敗した場合
	 */
	@PostMapping("/function/opticalCharacterRecognition")
	public String opticalCharacterRecognition(
			@RequestParam(value = "imageFile", required = false) Part imageFile,
			Model model) throws IOException {

		// 入力確認
		if (FileValidator.isEmptyFilePart(imageFile)) {
			return "redirect:/function/opticalCharacterRecognition.jsp";
		}

		// 一時ファイルを作成
		String submittedName = Paths.get(imageFile.getSubmittedFileName()).getFileName().toString();
		Path tempFile = Files.createTempFile("uploaded-", "-" + submittedName);

		try (InputStream inputStream = imageFile.getInputStream()) {
			BufferedImage originalImage = ImageIO.read(inputStream);
			if (originalImage == null) {
				model.addAttribute("exception", "無効な画像ファイルです");
				return "function/exceptionMessage";
			}

			// ファイルに保存
			imageFile.write(tempFile.toString());

			// OCR 処理
			String result = opticalCharacterRecognition.resultOCR(tempFile.toFile(), LANGUAGE);

			model.addAttribute("ocrResult", result);
			return "function/opticalCharacterRecognition";
		} catch (IOException | LogicException e) {
			model.addAttribute("exception", "コントローラーでエラーが発生しました。: " + e.getMessage());
			return "function/exceptionMessage";
		} finally {
			// 一時ファイルの削除
			Files.deleteIfExists(tempFile);
		}
	}
}
