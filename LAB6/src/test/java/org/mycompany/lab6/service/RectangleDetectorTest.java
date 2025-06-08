package org.mycompany.lab6.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mycompany.lab6.config.OpenCVLoader;
import org.mycompany.lab6.model.RectangleDetectionResult;
import org.mycompany.lab6.util.ImageIOUtil;
import org.opencv.core.Mat;

import static org.junit.Assert.*;

public class RectangleDetectorTest {

    private static RectangleDetector rectangleDetector;

    @BeforeClass
    public static void setup() {
        OpenCVLoader.load();
        rectangleDetector = new RectangleDetector();
    }

    @Test
    public void testDetectRectangles() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        // Для теста Canny, чтобы получить контуры
        CannyEdgeDetector canny = new CannyEdgeDetector();
        Mat edges = canny.detectEdges(src, 50, 150);
        RectangleDetectionResult result = rectangleDetector.detectRectangles(edges, 100, 50, 20);
        assertNotNull(result);
        assertTrue(result.getCount() >= 0);
        edges.release();
        src.release();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDetectRectanglesInvalidParams() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        rectangleDetector.detectRectangles(src, -1, 50, 10);
        src.release();
    }
}