package com.imageconversion.common.conversion.logic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.imageconversion.common.enums.ImageExtension;

/**
 * <p>画像の拡張子を取得するロジックの実装クラス。</p>
 */
@Service
public class GetExtensionImpl implements GetExtension{

	@Override
	public List<String> getExtensionList() {
		List<String> extensions = Arrays.stream(ImageExtension.values())
                .map(ImageExtension::getExtension)
                .collect(Collectors.toList());
		return extensions;
	}

	@Override
	public ImageExtension findImageExtension(String extParam) {
		ImageExtension result = null;
		
		if (extParam == null || extParam.isEmpty()) {
            throw new IllegalArgumentException("パラメータは null または空ではいけません。");
        }

        for (ImageExtension ext : ImageExtension.values()) {
            if (ext.getExtension().equalsIgnoreCase(extParam)
                || ext.toString().equalsIgnoreCase(extParam)) {
                result = ext;
            }
        }
        
        return result;
	}

}
