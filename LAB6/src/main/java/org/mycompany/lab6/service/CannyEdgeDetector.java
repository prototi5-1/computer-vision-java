package org.mycompany.lab6.service;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.Objects;
import java.util.stream.Stream;

public final class CannyEdgeDetector {

    public CannyEdgeDetector() {}

    /**
     * Выполняет детектирование границ методом Canny.
     * @param src исходное цветное изображение
     * @param threshold1 нижний порог
     * @param threshold2 верхний порог
     * @return изображение с выделенными границами
     */
    public Mat detectEdges(Mat src, double threshold1, double threshold2) {
        Objects.requireNonNull(src, "Source image must not be null");
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(gray, gray, new org.opencv.core.Size(3, 3), 0);
        Mat edges = new Mat();
        Imgproc.Canny(gray, edges, threshold1, threshold2);
        gray.release();
        return edges;
    }

    /**
     * Анализирует поток пар порогов, возвращает поток изображений с границами.
     * @param src исходное изображение
     * @param thresholdsStream поток double[] с двумя элементами: threshold1, threshold2
     * @return поток изображений с границами
     */
    public Stream<Mat> analyzeThresholds(Mat src, Stream<double[]> thresholdsStream) {
        Objects.requireNonNull(src, "Source image must not be null");
        Objects.requireNonNull(thresholdsStream, "Thresholds stream must not be null");
        return thresholdsStream.map(thresholds -> detectEdges(src, thresholds[0], thresholds[1]));
    }
}