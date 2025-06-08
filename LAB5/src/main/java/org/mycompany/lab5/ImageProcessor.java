package org.mycompany.lab5;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Класс для работы с изображениями:
 * - заливка по цвету с учетом цветового диапазона,
 * - если цвет и диапазон null, генерируется случайный цвет и диапазон.
 */
public class ImageProcessor {

    static {
        // Загрузка библиотеки OpenCV
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    /**
     * Выполняет заливку изображения начиная с заданной точки.
     * Если fillColor или colorRange == null, генерирует случайный цвет и диапазон.
     *
     * @param image      исходное изображение (Mat)
     * @param startPoint начальная точка заливки
     * @param fillColor  цвет заливки (Scalar BGR) или null
     * @param colorRange диапазон анализа цвета (ColorRange) или null
     * @return новое изображение с заливкой
     */
    public Mat floodFill(Mat image, Point startPoint, Scalar fillColor, ColorRange colorRange) {
        Objects.requireNonNull(image, "Image must not be null");
        Objects.requireNonNull(startPoint, "Start point must not be null");

        Mat result = image.clone();

        // Генерация случайного цвета и диапазона, если null
        if (fillColor == null || colorRange == null) {
            fillColor = randomColor();
            colorRange = randomColorRange(fillColor);
        }

        // Получаем цвет в startPoint
        double[] startPixel = result.get((int) startPoint.y, (int) startPoint.x);
        if (startPixel == null) {
            throw new IllegalArgumentException("Start point outside image bounds");
        }
        Scalar startColor = new Scalar(startPixel);

        // Создаем маску для floodFill (размер на 2 больше)
        Mat mask = Mat.zeros(result.rows() + 2, result.cols() + 2, CvType.CV_8UC1);

        // Определяем нижний и верхний пороги для floodFill
        Scalar loDiff = diffScalar(startColor, colorRange.getLowerBound());
        Scalar upDiff = diffScalar(colorRange.getUpperBound(), startColor);

        // Параметры заливки
        Rect rect = new Rect();
        Imgproc.floodFill(result, mask, startPoint, fillColor, rect, loDiff, upDiff, Imgproc.FLOODFILL_FIXED_RANGE);

        return result;
    }

    /**
     * Выполняет заливку изображения для нескольких стартовых точек.
     *
     * @param image     исходное изображение
     * @param fillColor цвет заливки (может быть null)
     * @param colorRange цветовой диапазон (может быть null)
     * @return список залитых изображений для каждой стартовой точки
     */
    public List<Mat> floodFillMultiplePoints(Mat image, Scalar fillColor, ColorRange colorRange) {
        int rows = image.rows();
        int cols = image.cols();

        // Стартовые точки: верхний ряд (x от 0 до cols-1, y=0)
        return IntStream.range(0, cols)
                .mapToObj(x -> floodFill(image, new Point(x, 0), fillColor, colorRange))
                .toList();
    }

    private Scalar randomColor() {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        return new Scalar(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    private ColorRange randomColorRange(Scalar baseColor) {
        ThreadLocalRandom rnd = ThreadLocalRandom.current();
        int range = rnd.nextInt(10, 50); // Диапазон +-10..50 для каждого канала

        double[] lower = new double[3];
        double[] upper = new double[3];
        for (int i = 0; i < 3; i++) {
            lower[i] = Math.max(0, baseColor.val[i] - range);
            upper[i] = Math.min(255, baseColor.val[i] + range);
        }
        return new ColorRange(new Scalar(lower), new Scalar(upper));
    }

    /**
     * Вычисляет разницу двух Scalar (abs(a - b))
     */
    private Scalar diffScalar(Scalar a, Scalar b) {
        double[] diff = new double[3];
        for (int i = 0; i < 3; i++) {
            diff[i] = Math.abs(a.val[i] - b.val[i]);
        }
        return new Scalar(diff);
    }
}
