package com.imageconversion.common.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>{@link FileValidator}のテストクラス。</p>
 *
 * <h4>isEmptyFilePart メソッド</h4>
 * <ul>
 * <li>{@link #isEmptyFilePartSuccess01} 正常系：null が渡された場合</li>
 * <li>{@link #isEmptyFilePartSuccess02} 正常系：サイズ0のパートが渡された場合</li>
 * <li>{@link #isEmptyFilePartSuccess03} 正常系：有効なパートが渡された場合</li>
 * </ul>
 *
 * <h4>readImage メソッド</h4>
 * <ul>
 * <li>{@link #readImageSuccess01} 正常系：有効な画像データが渡された場合</li>
 * <li>{@link #readImageError01} 異常系：無効な画像データが渡された場合</li>
 * </ul>
 */
public class FileValidatorTest {

    private Part mockPart;

    /**
     * <p>テストの前処理。</p>
     */
    @BeforeEach
    public void setUp() {
        mockPart = mock(Part.class);
    }

    /**
     * <p>正常系：null が渡された場合。</p>
     */
    @Test
    public void isEmptyFilePartSuccess01() {
        assertTrue(FileValidator.isEmptyFilePart(null));
    }

    /**
     * <p>正常系：サイズ0のパートが渡された場合。</p>
     */
    @Test
    public void isEmptyFilePartSuccess02() {
        when(mockPart.getSize()).thenReturn(0L);
        assertTrue(FileValidator.isEmptyFilePart(mockPart));
    }

    /**
     * <p>正常系：有効なパートが渡された場合。</p>
     */
    @Test
    public void isEmptyFilePartSuccess03() {
        when(mockPart.getSize()).thenReturn(100L);
        assertFalse(FileValidator.isEmptyFilePart(mockPart));
    }

    /**
     * <p>正常系：有効な画像データが渡された場合。</p>
     *
     * @throws Exception 想定外のエラーが発生した場合
     */
    @Test
    public void readImageSuccess01() throws Exception {
        BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(dummyImage, "png", baos);
        when(mockPart.getInputStream()).thenReturn(new ByteArrayInputStream(baos.toByteArray()));

        BufferedImage result = FileValidator.readImage(mockPart);

        assertNotNull(result);
        assertEquals(10, result.getWidth());
        assertEquals(10, result.getHeight());
    }

    /**
     * <p>異常系：無効な画像データが渡された場合。</p>
     *
     * @throws Exception 想定外のエラーが発生した場合
     */
    @Test
    public void readImageError01() throws Exception {
        when(mockPart.getInputStream()).thenReturn(new ByteArrayInputStream("notanimage".getBytes()));

        IOException e = assertThrows(IOException.class, () -> FileValidator.readImage(mockPart));
        assertEquals("無効な画像ファイルです", e.getMessage());
    }

}
