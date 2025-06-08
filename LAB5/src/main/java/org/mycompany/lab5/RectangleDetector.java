package org.mycompany.lab5;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Класс для идентификации прямоугольных объектов заданного размера на изображении.
 */
public class RectangleDetector {

    static {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    /**
     * Ищет прямоугольники заданного размера на изображении.
     * @param image исходное цветное изображение
     * @param targetWidth целевая ширина прямоугольника
     * @param targetHeight целевая высота прямоугольника
     * @param tolerance допустимое отклонение по ширине и высоте (например 5 пикселей)
     * @return количество найденных прямоугольников заданного размера
     */
    public long countRectangles(Mat image, int targetWidth, int targetHeight, int tolerance) {
    Objects.requireNonNull(image);
    if (targetWidth <= 0 || targetHeight <= 0) {
        throw new IllegalArgumentException("Width and height must be positive");
    }
    if (tolerance < 0) tolerance = 0;

    // Создание final копии, чтобы использовать в лямбдах
    final int targetWidthFinal = targetWidth;
    final int targetHeightFinal = targetHeight;
    final int toleranceFinal = tolerance;

    // Конвертирование в grayscale
    Mat gray = new Mat();
    Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

    // Применение порога для бинаризации
    Mat binary = new Mat();
    Imgproc.threshold(gray, binary, 100, 255, Imgproc.THRESH_BINARY);

    // Поиск контуров
    List<MatOfPoint> contours = new java.util.ArrayList<>();
    Mat hierarchy = new Mat();
    Imgproc.findContours(binary, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

    // Фильтрация контуров по форме прямоугольника и размеру
    return contours.stream()
            .map(contour -> {
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                MatOfPoint2f approxCurve = new MatOfPoint2f();
                Imgproc.approxPolyDP(contour2f, approxCurve, Imgproc.arcLength(contour2f, true) * 0.02, true);
                return approxCurve;
            })
            .filter(approxCurve -> approxCurve.total() == 4) // 4 угла - прямоугольник
            .map(approxCurve -> {
                Rect rect = Imgproc.boundingRect(new MatOfPoint(approxCurve.toArray()));
                return rect;
            })
            .filter(rect -> 
                Math.abs(rect.width - targetWidthFinal) <= toleranceFinal &&
                Math.abs(rect.height - targetHeightFinal) <= toleranceFinal
            )
            .count();
    }

}
