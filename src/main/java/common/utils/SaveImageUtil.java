package utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

/**
 * <p>画像を保存するメソッドを用意したユーティリティクラス。</p>
 */
public class SaveImageUtil {
	
	/**
	 * <p>画像をBase64エンコードして保存する。</p>
	 * 
	 * @param imageBytes 画像のバイト配列
	 * @param formatName 画像のフォーマット名（例: "png", "jpg"）
	 * @return Base64エンコードされた画像データ
	 */
	public static String saveImage(byte[] imageBytes, String formatName) {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, formatName, baos);
			return Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * <p>画像を指定されたディレクトリに保存する。</p>
	 * 
	 * @param part アップロードされた画像ファイルのPartオブジェクト
	 * @param uploadDir 保存先のディレクトリパス
	 * @return 保存されたファイルの名前
	 * @throws IOException 画像の保存に失敗した場合
	 */
	public static String saveImage(Part part, String uploadDir) throws IOException {
		if (part == null || part.getSubmittedFileName() == null) {
            throw new IllegalArgumentException("無効なファイルです。");
        }
		
		// 元のファイル名を取得
        String originalFileName = part.getSubmittedFileName();

        // 保存先ディレクトリを作成（存在しない場合）
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        // 保存先ファイルを作成
        File savedFile = new File(uploadDir, originalFileName);
        part.write(savedFile.getAbsolutePath());

        return savedFile.getName();
	}
}
