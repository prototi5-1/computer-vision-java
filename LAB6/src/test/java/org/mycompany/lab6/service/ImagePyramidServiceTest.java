package org.mycompany.lab6.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mycompany.lab6.config.OpenCVLoader;
import org.mycompany.lab6.util.ImageIOUtil;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;

import java.util.Optional;

import static org.junit.Assert.*;

public class ImagePyramidServiceTest {

    private static ImagePyramidService pyramidService;

    @BeforeClass
    public static void setup() {
        OpenCVLoader.load();
        pyramidService = new ImagePyramidService();
    }

    @Test
    public void testDownscaleAndUpscale() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        Mat down = pyramidService.downscale(src, 2);
        assertNotNull(down);
        assertFalse(down.empty());
        Mat up = pyramidService.upscale(down, 2);
        assertNotNull(up);
        assertFalse(up.empty());
        down.release();
        up.release();
        src.release();
    }

    @Test
    public void testGetFragmentValid() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        Rect roi = new Rect(0, 0, 10, 10);
        Optional<Mat> fragment = pyramidService.getFragment(src, roi);
        assertTrue(fragment.isPresent());
        fragment.get().release();
        src.release();
    }

    @Test
    public void testGetFragmentInvalid() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        Rect roi = new Rect(-1, -1, 10, 10);
        Optional<Mat> fragment = pyramidService.getFragment(src, roi);
        assertFalse(fragment.isPresent());
        src.release();
    }

    @Test
    public void testSubtract() {
        Mat src1 = ImageIOUtil.loadImage("image1.jpg");
        Mat src2 = pyramidService.downscale(src1, 2); // downscale на 2

        // Приведение src1 к размеру src2, чтобы размеры совпали
        Mat src1Resized = new Mat();
        Imgproc.resize(src1, src1Resized, src2.size());

        Mat result = pyramidService.subtract(src1Resized, src2);

        assertNotNull(result);
        assertFalse(result.empty());

        src1.release();
        src1Resized.release();
        src2.release();
        result.release();
    }
}
