package com.imageconversion.conversion.logic;

import java.io.File;

import com.imageconversion.common.exception.LogicException;

/**
 * <p>光学文字認識（OCR）のロジックを提供するインターフェース。</p>
 */
public interface OpticalCharacterRecognition {
	
	public String resultOCR(File input, String language) throws LogicException;

}
