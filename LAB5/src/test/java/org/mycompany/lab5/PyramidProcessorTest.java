package org.mycompany.lab5;

import org.junit.Test;
import org.opencv.core.*;
import org.mycompany.lab5.utils.ImageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class PyramidProcessorTest {

    static {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    private final Path inputDir = Paths.get("src/main/resources/images/input");
    private final Path outputDir = Paths.get("src/main/resources/images/output");

    @Test
    public void testDownscale() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        PyramidProcessor processor = new PyramidProcessor();

        Mat downscaled = processor.downscale(image, 2);

        assertNotNull(downscaled);
        assertTrue(downscaled.rows() < image.rows());
        assertTrue(downscaled.cols() < image.cols());

        ImageUtils.saveImage(downscaled, outputDir.resolve("downscaled_image.jpg"));
    }

    @Test
    public void testUpscale() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        PyramidProcessor processor = new PyramidProcessor();

        Mat upscaled = processor.upscale(image, 2);

        assertNotNull(upscaled);
        assertTrue(upscaled.rows() > image.rows());
        assertTrue(upscaled.cols() > image.cols());

        ImageUtils.saveImage(upscaled, outputDir.resolve("upscaled_image.jpg"));
    }

    @Test
    public void testGetFragmentAfterPyramidOps() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        PyramidProcessor processor = new PyramidProcessor();

        Rect fragmentRect = new Rect(50, 50, 100, 100);
        Mat fragment = processor.getFragmentAfterPyramidOps(image, 1, 1, fragmentRect);

        assertNotNull(fragment);
        assertEquals(fragmentRect.width, fragment.cols());
        assertEquals(fragmentRect.height, fragment.rows());

        ImageUtils.saveImage(fragment, outputDir.resolve("fragment_image.jpg"));
    }
}
