package conversion.logic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import common.exception.LogicException;
import common.utils.EncodingUtil;
import common.utils.Sanitize;

/**
 * <p>画像を保存するロジックの実装クラス。</p>
 */
public class SaveImageImpl implements SaveImage {
	

	/**
	 * <p>バイト配列から画像を指定されたディレクトリに保存する。</p>
	 * 
	 * @param imageBytes 画像のバイト配列
	 * @param outputDir 保存先のディレクトリパス
	 * @param outputFileName 保存するファイル名
	 * @return 保存されたファイルの絶対パス
	 * @throws LogicException 画像の保存に失敗した場合
	 */
	@Override
	public String saveImage(byte[] imageBytes, String outputDir, String outputFileName) throws LogicException {
		try {
			// 出力ファイル名のエンコードをUTF-8に変換
			outputFileName = EncodingUtil.convertToUTF8(outputFileName);
			// バイト配列から画像を復元
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			if (image == null) {
	            throw new IOException();
	        }
			
			// 保存先ディレクトリを確認・作成
	        File dir = new File(outputDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }
	        
	        // 拡張子取り出し
	        String extension = Sanitize.getFileExtension(outputFileName);
	        
	        File outputFile = new File(dir, outputFileName);

			ImageIO.write(image, extension, outputFile);
			return outputFile.getAbsolutePath();
		} catch (IOException e) {
			throw new LogicException("画像の保存に失敗しました。", e);
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
	@Override
	public String savePartImage(Part part, String uploadDir) throws LogicException {
		
		try {
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

	        return savedFile.getAbsolutePath();
		} catch (IllegalArgumentException | IOException e) {
			throw new LogicException("画像の保存に失敗しました。", e);
		} 
		
	}
}
