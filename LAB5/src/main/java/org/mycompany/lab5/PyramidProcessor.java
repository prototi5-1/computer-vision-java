package org.mycompany.lab5;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.core.Core;
import org.opencv.imgproc.Imgproc;

import java.util.Objects;

/**
 * Класс для построения понижающей и повышающей пирамид изображения,
 * а также получения фрагмента после последовательных операций.
 */
public class PyramidProcessor {

    static {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    /**
     * Создает понижающую пирамиду изображения.
     * @param image исходное изображение
     * @param levels количество уровней понижения
     * @return изображение после понижения
     */
    public Mat downscale(Mat image, int levels) {
        Objects.requireNonNull(image);
        if (levels < 0) {
            throw new IllegalArgumentException("Levels must be >= 0");
        }
        Mat result = image.clone();
        for (int i = 0; i < levels; i++) {
            Mat tmp = new Mat();
            Imgproc.pyrDown(result, tmp);
            result = tmp;
        }
        return result;
    }

    /**
     * Создает повышающую пирамиду изображения.
     * @param image исходное изображение
     * @param levels количество уровней повышения
     * @return изображение после повышения
     */
    public Mat upscale(Mat image, int levels) {
        Objects.requireNonNull(image);
        if (levels < 0) {
            throw new IllegalArgumentException("Levels must be >= 0");
        }
        Mat result = image.clone();
        for (int i = 0; i < levels; i++) {
            Mat tmp = new Mat();
            Imgproc.pyrUp(result, tmp);
            result = tmp;
        }
        return result;
    }

    /**
     * Получает фрагмент изображения после операций понижения и повышения.
     * @param image исходное изображение
     * @param downLevels количество понижений
     * @param upLevels количество повышений
     * @param fragmentRect прямоугольник фрагмента (в координатах исходного изображения)
     * @return фрагмент изображения после операций
     */
    public Mat getFragmentAfterPyramidOps(Mat image, int downLevels, int upLevels, Rect fragmentRect) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(fragmentRect);

        Mat downscaled = downscale(image, downLevels);
        Mat upscaled = upscale(downscaled, upLevels);

        // Коррекция размера фрагмента, если выходит за пределы изображения
        Rect correctedRect = new Rect(
                Math.min(fragmentRect.x, upscaled.cols() - 1),
                Math.min(fragmentRect.y, upscaled.rows() - 1),
                Math.min(fragmentRect.width, upscaled.cols() - fragmentRect.x),
                Math.min(fragmentRect.height, upscaled.rows() - fragmentRect.y)
        );

        return new Mat(upscaled, correctedRect);
    }

    /**
     * Вычитание изображений с помощью Core.subtract
     * @param img1 первое изображение
     * @param img2 второе изображение
     * @return результат вычитания
     */
    public Mat subtract(Mat img1, Mat img2) {
        Objects.requireNonNull(img1);
        Objects.requireNonNull(img2);
        Mat result = new Mat();
        Core.subtract(img1, img2, result);
        return result;
    }
}
