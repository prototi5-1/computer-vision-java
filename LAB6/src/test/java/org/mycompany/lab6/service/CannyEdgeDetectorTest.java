package org.mycompany.lab6.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mycompany.lab6.config.OpenCVLoader;
import org.mycompany.lab6.util.ImageIOUtil;
import org.opencv.core.Mat;

import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CannyEdgeDetectorTest {

    private static CannyEdgeDetector canny;

    @BeforeClass
    public static void setup() {
        OpenCVLoader.load();
        canny = new CannyEdgeDetector();
    }

    @Test
    public void testDetectEdges() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        Mat edges = canny.detectEdges(src, 50, 150);
        assertNotNull(edges);
        assertFalse(edges.empty());
        edges.release();
        src.release();
    }

    @Test
    public void testAnalyzeThresholds() {
        Mat src = ImageIOUtil.loadImage("image1.jpg");
        double[][] thresholdsArr = {{50, 150}, {100, 200}};
        Stream<double[]> stream = Stream.of(thresholdsArr);
        var edgesStream = canny.analyzeThresholds(src, stream);
        edgesStream.forEach(mat -> {
            assertNotNull(mat);
            assertFalse(mat.empty());
            mat.release();
        });
        src.release();
    }
}