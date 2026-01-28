package com.imageconversion.conversion.logic;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.imageconversion.common.exception.LogicException;

import net.sourceforge.tess4j.ITessAPI;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

/**
 * <p>光学文字認識（OCR）のロジックの実装クラス。</p>
 */
public class OpticalCharacterRecognitionImpl implements OpticalCharacterRecognition{

	@Override
	public String resultOCR(File input, String language) throws LogicException{
		
		ITesseract tesseract = new Tesseract();
		// Tesseractの設定
		// データセット↓
		// https://github.com/tesseract-ocr/tessdata/tree/main
		
		tesseract.setDatapath("src/main/resources/ocrDataset");
		tesseract.setLanguage(language);
		tesseract.setOcrEngineMode(ITessAPI.TessOcrEngineMode.OEM_LSTM_ONLY);
		
		try {
			BufferedImage img = ImageIO.read(input);
			// OCR処理を実行
			String result = tesseract.doOCR(img);
			return result;
		} catch (Exception e) {
			throw new LogicException("OCR処理中にエラーが発生しました。", e);
		}
	}

}
