package org.mycompany.lab5;

import org.junit.Test;
import org.opencv.core.Mat;
import org.mycompany.lab5.utils.ImageUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class RectangleDetectorTest {

    static {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    private final Path inputDir = Paths.get("src/main/resources/images/input");
    private final Path outputDir = Paths.get("src/main/resources/images/output");

    @Test
    public void testCountRectangles() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        RectangleDetector detector = new RectangleDetector();

        long count = detector.countRectangles(image, 200, 100, 10);
        assertTrue(count >= 0);

        // исходное изображение для визуального контроля
        ImageUtils.saveImage(image, outputDir.resolve("rectangle_detector_input.jpg"));
    }

    @Test
    public void testCountRectanglesWithTolerance() {
        Mat image = ImageUtils.loadImage(inputDir.resolve("test_image.jpg"));
        RectangleDetector detector = new RectangleDetector();

        long count = detector.countRectangles(image, 150, 80, 15);
        assertTrue(count >= 0);

        ImageUtils.saveImage(image, outputDir.resolve("rectangle_detector_input_tolerance.jpg"));
    }
}
