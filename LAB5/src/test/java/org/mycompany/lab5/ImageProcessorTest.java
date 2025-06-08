package org.mycompany.lab5;

import org.junit.Test;
import org.opencv.core.*;
import org.mycompany.lab5.utils.ImageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class ImageProcessorTest {

    static {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    private final Path inputDir = Paths.get("src/main/resources/images/input");
    private final Path outputDir = Paths.get("src/main/resources/images/output");

    @Test
    public void testFloodFillWithNullParams() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        ImageProcessor processor = new ImageProcessor();

        Point start = new Point(0, 0);
        Mat result = processor.floodFill(image, start, null, null);

        assertNotNull(result);
        assertEquals(image.rows(), result.rows());
        assertEquals(image.cols(), result.cols());

        ImageUtils.saveImage(result, outputDir.resolve("flood_fill_null_params.jpg"));
    }

    @Test
    public void testFloodFillMultiplePoints() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        ImageProcessor processor = new ImageProcessor();

        List<Mat> results = processor.floodFillMultiplePoints(image, null, null);

        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertEquals(image.cols(), results.size());

        ImageUtils.saveImages(results, outputDir.resolve("flood_fill_multiple"), "jpg");
    }
}
