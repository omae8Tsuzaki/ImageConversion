package com.imageconversion.common.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

/**
 * <p>ファイルアップロードに関するバリデーションユーティリティクラス。</p>
 */
public class FileValidator {

    private FileValidator() {
    }

    /**
     * <p>ファイルパートが空（未選択またはサイズ0）かどうかを判定する。</p>
     *
     * @param filePart 検査対象のパート
     * @return 空の場合 true
     */
    public static boolean isEmptyFilePart(Part filePart) {
        return filePart == null || filePart.getSize() == 0;
    }

    /**
     * <p>ファイルパートから BufferedImage を読み込む。</p>
     * <p>無効な画像データの場合は IOException をスローする。</p>
     *
     * @param filePart 読み込み対象のパート
     * @return 読み込んだ BufferedImage
     * @throws IOException 画像の読み込みに失敗した場合
     */
    public static BufferedImage readImage(Part filePart) throws IOException {
        try (InputStream inputStream = filePart.getInputStream()) {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                throw new IOException("無効な画像ファイルです");
            }
            return image;
        }
    }
}
