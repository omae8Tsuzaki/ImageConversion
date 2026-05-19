package com.imageconversion.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>共通の設定値を提供するクラス。</p>
 */
@Configuration
public class ApplicationConfig {

	/**
	 * <p>アップロード先ディレクトリ。</p>
	 */
	@Value("${app.upload.dir}")
	private String uploadDir;
	
	/**
	 * <p>アップロード先ディレクトリを取得する。</p>
	 * 
	 * @return アップロード先ディレクトリ
	 */
	public String getUploadDir() {
		return uploadDir;
	}
	
	public String toString() {
		return ApplicationConfig.class.getName() 
				+ "["
					+ "uploadDir=" + uploadDir
				+ "]";
	}
}
