package conversion.logic;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.servlet.http.Part;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;


/**
 * <p>{@link SaveImageImpl}のテストクラス。</p>
 * 
 * <h4>saveImageメソッド</h4>
 * <ul>
 * </ul>
 * 
 * <h4>savePartImage メソッド</p>
 * <ul>
 * <li>{@link #savePartImageSuccess01}正常系</li>
 * </ul>
 */
public class SaveImageImplTest {
	// テスト用
	@TempDir
    private File temporaryFolder;
	
	private SaveImage logic = new SaveImageImpl();
	
	@Test
	public void saveImageSuccess01() throws Exception {

		String outputFileName = "test.jpg";
		byte[] imageBytes = null;

		//
		// 実行
		//
		String result = logic.saveImage(imageBytes, temporaryFolder.getAbsolutePath(), outputFileName);
		
		//
		// 検証
		//
	}
	
	/**
	 * <p>正常系テスト。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void savePartImageSuccess01() throws Exception {
		// モックのPartオブジェクトを作成
		Part mockPart = mock(Part.class);
		String fileName = "test_image.jpg";
		when(mockPart.getSubmittedFileName()).thenReturn(fileName);
		// JPEGヘッダー
	    when(mockPart.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}));
		
	    //
		// 実行
	    //
		String testImage = logic.savePartImage(mockPart, temporaryFolder.getAbsolutePath());
		
		//
		// 検証
		//
		assertEquals(fileName, testImage);
	}
	
	@Test
	public void testSaveImage02() {
		// 実行
		//String testImage = SaveImageUtil.saveImage(mockPart, WORK_DIR);
	}

}
