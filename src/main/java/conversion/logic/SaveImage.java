package conversion.logic;

import java.io.IOException;

import javax.servlet.http.Part;

import common.exception.LogicException;

/**
 * <p>画像を保存するロジックのインターフェース。</p>
 */
public interface SaveImage {
	
	/**
	 * <p>画像を指定されたディレクトリに保存する。</p>
	 * 
	 * @param imageBytes 画像データのバイト配列
	 * @param outputDir 出力先ディレクトリのパス
	 * @param outputFileName 出力ファイル名（拡張子を含む）
	 * @return 保存された画像のパス
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	public String saveImage(byte[] imageBytes, String outputDir, String outputFileName) throws LogicException;
	

	/**
	 * <p>Partオブジェクトから画像を指定されたディレクトリに保存する。</p>
	 * 
	 * @param part 画像のPartオブジェクト
	 * @param uploadDir アップロード先ディレクトリのパス
	 * @return 保存された画像のパス
	 * @throws IOException 入出力エラーが発生した場合
	 */
	public String savePartImage(Part part, String uploadDir) throws LogicException;
}
