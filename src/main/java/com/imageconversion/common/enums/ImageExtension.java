package com.imageconversion.common.enums;

/**
 * <p>画像の拡張子を表す列挙型。</p>
 */
public enum ImageExtension {
	JPG("jpg"),
    PNG("png"),
    GIF("gif"),
    BMP("bmp");
	
	private final String extension;
	
	ImageExtension(String extension) {
        this.extension = extension;
    }
	
	public String getExtension() {
        return extension;
    }
	
	/**
	 * <p>指定された拡張子が有効な拡張子か判定するメソッド。</p>
	 * 
	 * @param extension 拡張子文字列
	 * @return 有効な拡張子ならtrue、そうでなければfalse
	 */
	public static boolean isValidExtension(String extension) {
        for (ImageExtension ext : values()) {
            if (ext.getExtension().equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

}
