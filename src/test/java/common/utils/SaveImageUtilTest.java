package common.utils;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.File;

import javax.servlet.http.Part;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * <p>{@link SaveImageUtil}のテストクラス。</p>
 * 
 * <ul>
 * <li>{@link #saveImageSuccess01}正常系</li>
 * </ul>
 */
public class SaveImageUtilTest {
	// テスト用
	@TempDir
    File temporaryFolder;
	
	/**
	 * <p>{@link SaveImageUtil#saveImage}の正常系テスト。</p>
	 * 
	 * @throws Exception 想定外のエラーが発生した場合
	 */
	@Test
	public void saveImageSuccess01() throws Exception {
		// モックのPartオブジェクトを作成
		Part mockPart = mock(Part.class);
		String fileName = "test_image.jpg";
		when(mockPart.getSubmittedFileName()).thenReturn(fileName);
		// JPEGヘッダー
	    when(mockPart.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}));
		
		// 実行
		String testImage = SaveImageUtil.saveImage(mockPart, temporaryFolder.getAbsolutePath());
		
		// 検証
		assertEquals(fileName, testImage);
	}
	
	@Test
	public void testSaveImage02() {
		// 実行
		//String testImage = SaveImageUtil.saveImage(mockPart, WORK_DIR);
	}

}
