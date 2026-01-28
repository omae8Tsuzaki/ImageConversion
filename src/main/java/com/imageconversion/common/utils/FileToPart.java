package com.imageconversion.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Part;

/**
 * <p>ファイルからPartオブジェクトを作成するユーティリティクラス。</p>
 */
public final class FileToPart implements Part {
	
	private final String name;              // フォームのフィールド名
    private final File file;                // 元となるファイル
    private final String contentType;       // 推定コンテンツタイプ
    private final Map<String, List<String>> headers;
    
    /**
     * <p>ファイルからPartオブジェクトを作成するコンストラクタ。</p>
     * 
     * @param fieldName フィールド名
     * @param file ファイル
     * @throws IOException 想定外のエラーが発生した場合
     */
    public FileToPart(String fieldName, File file) throws IOException {
    	if (fieldName == null || fieldName.isEmpty()) {
            throw new IllegalArgumentException("fieldName must not be null or empty.");
        }
        if (file == null || !file.isFile()) {
            throw new IllegalArgumentException("file must be an existing regular file.");
        }
		this.name = fieldName;
		this.file = file;
		this.contentType = Files.probeContentType(file.toPath());
		this.headers = new HashMap<>();
		// 必要ならヘッダーを追加（例: Content-Type, Content-Disposition）
        addHeader("Content-Type", this.contentType != null ? this.contentType : "application/octet-stream");
        addHeader("Content-Disposition",
                String.format("form-data; name=\"%s\"; filename=\"%s\"", fieldName, file.getName()));
    }
    
    private void addHeader(String key, String value) {
        headers.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

	@Override
	public InputStream getInputStream() throws IOException {
		return new BufferedInputStream(new FileInputStream(file));
	}

	@Override
	public String getContentType() {
		return contentType != null ? contentType : "application/octet-stream";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getSubmittedFileName() {
		return file.getName();
	}

	@Override
	public long getSize() {
		return file.length();
	}

	/**
	 * <p>ファイルの内容を指定されたファイルに書き込む。</p>
	 */
	@Override
	public void write(String fileName) throws IOException {
		Path target = Paths.get(fileName);
        Files.createDirectories(target.getParent() != null ? target.getParent() : target.toAbsolutePath().getParent());
        try (InputStream in = getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
	}

	@Override
	public void delete() throws IOException {
		// no-op（必要なら file.delete() などに変更）
	}

	@Override
	public String getHeader(String name) {
		List<String> values = headers.get(name);
        return (values == null || values.isEmpty()) ? null : values.get(0);
	}

	@Override
	public Collection<String> getHeaders(String name) {
		List<String> values = headers.get(name);
        return values != null ? Collections.unmodifiableList(values) : Collections.emptyList();
	}

	@Override
	public Collection<String> getHeaderNames() {
		return Collections.unmodifiableSet(headers.keySet());
	}
	
	public File getFile() {
        return file;
    }
	
	// ファクトリ
    public static Part fromFile(String fieldName, File file) throws IOException {
        return new FileToPart(fieldName, file);
    }

}
