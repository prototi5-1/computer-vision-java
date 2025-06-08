package org.mycompany.lab6.util;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mycompany.lab6.config.OpenCVLoader;
import org.opencv.core.Mat;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class ImageIOUtilTest {

    private static final String TEST_IMAGE = "image1.jpg";

    @BeforeClass
    public static void setup() {
        OpenCVLoader.load();
    }

    @Test
    public void testLoadImage() {
        Mat img = ImageIOUtil.loadImage(TEST_IMAGE);
        assertNotNull(img);
        assertFalse(img.empty());
        img.release();
    }

    @Test
    public void testSaveImage() throws Exception {
        Mat img = ImageIOUtil.loadImage(TEST_IMAGE);
        String outFilename = "test_output.png";
        boolean saved = ImageIOUtil.saveImage(outFilename, img);
        assertTrue(saved);
        assertTrue(Files.exists(Path.of("src/main/resources/images/output", outFilename)));
        img.release();
    }
}