package org.mycompany.lab6.service;

import org.mycompany.lab6.model.RectangleDetectionResult;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class RectangleDetector {

    public RectangleDetector() {}

    /**
     * Идентифицирует прямоугольные объекты на изображении заданного размера (с допуском).
     * @param src изображение (предварительно обработанное, Canny)
     * @param targetWidth ширина искомого прямоугольника
     * @param targetHeight высота искомого прямоугольника
     * @param tolerance допуск по размеру (в пикселях)
     * @return результат с количеством найденных
     */
    public RectangleDetectionResult detectRectangles(Mat src, int targetWidth, int targetHeight, int tolerance) {
        Objects.requireNonNull(src, "Source image must not be null");
        if (targetWidth <= 0 || targetHeight <= 0 || tolerance < 0) {
            throw new IllegalArgumentException("Invalid dimension or tolerance");
        }

        List<MatOfPoint> contours = new java.util.ArrayList<>();
        Mat hierarchy = new Mat();

        Imgproc.findContours(src, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        hierarchy.release();

        List<Rect> rects = contours.stream()
                .map(contour -> {
                    MatOfPoint2f approxCurve = new MatOfPoint2f();
                    MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                    double peri = Imgproc.arcLength(contour2f, true);
                    Imgproc.approxPolyDP(contour2f, approxCurve, 0.02 * peri, true);
                    contour2f.release();

                    if (approxCurve.total() == 4 && Imgproc.isContourConvex(new MatOfPoint(approxCurve.toArray()))) {
                        Rect r = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
                        approxCurve.release();
                        return r;
                    }
                    approxCurve.release();
                    return null;
                })
                .filter(Objects::nonNull)
                .filter(r -> Math.abs(r.width - targetWidth) <= tolerance && Math.abs(r.height - targetHeight) <= tolerance)
                .collect(Collectors.toList());

        return new RectangleDetectionResult(targetWidth, targetHeight, rects.size());
    }
}
