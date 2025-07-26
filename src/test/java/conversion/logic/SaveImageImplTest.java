package conversion.logic;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import common.exception.LogicException;


/**
 * <p>{@link SaveImageImpl}のテストクラス。</p>
 * 
 * <h4>saveImageメソッド</h4>
 * <ul>
 * <li>{@link #saveImageSuccess01}正常系：画像を指定されたディレクトリに保存する</li>
 * <li>{@link #saveImageSuccess02}正常系：出力先ディレクトリが存在しない場合、ディレクトリを作成する</li>
 * <li>{@link #saveImageError01}異常系：無効な画像データを保存しようとした場合</li>
 * </ul>
 * 
 * <h4>savePartImage メソッド</p>
 * <ul>
 * <li>{@link #savePartImageSuccess01}正常系</li>
 * <li>{@link #savePartImageSuccess02}正常系：Partオブジェクトのファイル名が指定されている場合</li>
 * <li>{@link #savePartImageError01}異常系：Partオブジェクトがnullの場合</li>
 * <li>{@link #savePartImageError02}異常系：Partオブジェクトのファイル名がnullの場合</li>
 * </ul>
 */
public class SaveImageImplTest {
	// テスト用
	@TempDir
    private File temporaryFolder;
	
	private byte[] imageBytes;
	
	// 出力ファイル名
	private static String OUTPUT_FILENAME = "test.jpg";
	
	private SaveImage logic = new SaveImageImpl();
	
	/**
	 * <p>テストの前処理。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@BeforeEach
	public void setUp() throws Exception {
		// サンプル画像を作成
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        imageBytes = baos.toByteArray();
	}
	
	/**
	 * <p>正常系：画像を指定されたディレクトリに保存する。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveImageSuccess01() throws Exception {
		// 出力先ディレクトリのパスを取得
		String dirPath = temporaryFolder.getAbsolutePath();

		//
		// 実行
		//
		String result = logic.saveImage(imageBytes, dirPath, OUTPUT_FILENAME);
		
		//
		// 検証
		//
		assertEquals(dirPath + File.separator + OUTPUT_FILENAME, result);
	}
	
	/**
	 * <p>正常系：出力先ディレクトリが存在しない場合、ディレクトリを作成する。</p>
	 *
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveImageSuccess02() throws Exception {
		
		// 出力先ディレクトリのパスを取得
		File outputFile = new File(temporaryFolder, "test");
		String dirPath = outputFile.getAbsolutePath();
		
		//
		// 実行
		//
		String result = logic.saveImage(imageBytes, dirPath, OUTPUT_FILENAME);
		
		//
		// 検証
		//
		assertEquals(dirPath + File.separator + OUTPUT_FILENAME, result);
	}
	
	/**
	 * <p>異常系：無効な画像データを保存しようとした場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveImageError01() throws Exception {
		
		byte[] invalidBytes = "not-an-image".getBytes();
		
		//
		// 実行
		//
		LogicException expected = 
				assertThrows(LogicException.class, () -> {
					logic.saveImage(invalidBytes, temporaryFolder.getAbsolutePath(), OUTPUT_FILENAME);
				});
		
		//
		// 検証
		//
		assertEquals("画像の保存に失敗しました。", expected.getMessage());
	}
	
	/**
	 * <p>正常系テスト。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void savePartImageSuccess01() throws Exception {
		// 出力先ディレクトリのパスを取得
		String dirPath = temporaryFolder.getAbsolutePath();
		
		// モックのPartオブジェクトを作成
		Part mockPart = mock(Part.class);
		when(mockPart.getSubmittedFileName()).thenReturn(OUTPUT_FILENAME);
		
	    //
		// 実行
	    //
		String testImage = logic.savePartImage(mockPart, dirPath);
		
		//
		// 検証
		//
		String expected = dirPath + File.separator + OUTPUT_FILENAME;
		assertEquals(expected, testImage);
	}
	
	/**
	 * <p>正常系：Partオブジェクトのファイル名が指定されている場合。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void savePartImageSuccess02 () throws Exception {
		// 出力先ディレクトリのパスを取得
		File outputFile = new File(temporaryFolder, "test");
		String dirPath = outputFile.getAbsolutePath();
		
		// モックのPartオブジェクトを作成
		Part mockPart = mock(Part.class);
		when(mockPart.getSubmittedFileName()).thenReturn(OUTPUT_FILENAME);
		
	    //
		// 実行
	    //
		String testImage = logic.savePartImage(mockPart, dirPath);
		
		//
		// 検証
		//
		String expected = dirPath + File.separator + OUTPUT_FILENAME;
		assertEquals(expected, testImage);
	}
	
	/**
	 * <p>異常系：Partオブジェクトがnullの場合。</p>
	 */
	@Test
	public void savePartImageError01() {
		// モックのPartオブジェクトを作成
		Part mockPart = null;
		
		//
		// 実行
	    //
		LogicException expected = 
				assertThrows(LogicException.class, () -> {
					logic.savePartImage(mockPart, temporaryFolder.getAbsolutePath());
				});

		//
		// 検証
		//
		assertEquals("画像の保存に失敗しました。", expected.getMessage());
	}
	
	/**
	 * <p>異常系：Partオブジェクトのファイル名がnullの場合。</p>
	 */
	@Test
	public void savePartImageError02() {
		// モックのPartオブジェクトを作成
		Part mockPart = mock(Part.class);
		when(mockPart.getSubmittedFileName()).thenReturn(null);

		//
		// 実行
		//
		LogicException expected = 
				assertThrows(LogicException.class, () -> {
					logic.savePartImage(mockPart, temporaryFolder.getAbsolutePath());
				});

		//
		// 検証
		//
		assertEquals("画像の保存に失敗しました。", expected.getMessage());
	}

}
