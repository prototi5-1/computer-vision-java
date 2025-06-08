package org.mycompany.lab6.service;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.Optional;

public class ImagePyramidService {

    /**
     * Уменьшает изображение в размере в factor раз.
     * Если factor <= 1, возвращает копию исходного изображения.
     */
    public Mat downscale(Mat src, int factor) {
        if (factor <= 1) {
            return src.clone();
        }
        int newWidth = src.width() / factor;
        int newHeight = src.height() / factor;
        if (newWidth <= 0 || newHeight <= 0) {
            throw new IllegalArgumentException("Invalid downscale factor: resulting size is zero or negative");
        }
        Mat dst = new Mat();
        Imgproc.resize(src, dst, new Size(newWidth, newHeight), 0, 0, Imgproc.INTER_LINEAR);
        return dst;
    }


    /**
     * Увеличивает изображение в размере в factor раз.
     * Если factor <= 1, возвращает копию исходного изображения.
     */
    public Mat upscale(Mat src, int factor) {
        if (factor <= 1) {
            return src.clone();
        }
        int newWidth = src.width() * factor;
        int newHeight = src.height() * factor;
        Mat dst = new Mat();
        Size newSize = new Size(newWidth, newHeight);
        Imgproc.resize(src, dst, newSize);
        return dst;
    }

    /**
     * Возвращает фрагмент изображения по заданному ROI (прямоугольнику).
     * Если ROI выходит за границы изображения, возвращает Optional.empty().
     */
    public Optional<Mat> getFragment(Mat src, Rect roi) {
        if (roi.x < 0 || roi.y < 0 || roi.x + roi.width > src.width() || roi.y + roi.height > src.height()) {
            return Optional.empty();
        }
        Mat fragment = new Mat(src, roi);
        return Optional.of(fragment.clone()); // клонируем, чтобы избежать зависимости от исходного Mat
    }

    /**
     * Вычитает mat2 из mat1.
     * Проверяет, что размеры и количество каналов совпадают.
     */
    public Mat subtract(Mat mat1, Mat mat2) {
        // Приведение размеров к минимальным
        int minWidth = Math.min(mat1.width(), mat2.width());
        int minHeight = Math.min(mat1.height(), mat2.height());

        // Изменение размера матриц
        Mat mat1Resized = new Mat();
        Mat mat2Resized = new Mat();
        Imgproc.resize(mat1, mat1Resized, new Size(minWidth, minHeight));
        Imgproc.resize(mat2, mat2Resized, new Size(minWidth, minHeight));

        // Проверка совпадения каналов
        if (mat1Resized.channels() != mat2Resized.channels()) {
            throw new IllegalArgumentException("Number of channels do not match: " + mat1Resized.channels() + " vs " + mat2Resized.channels());
        }

        Mat result = new Mat();
        Core.subtract(mat1Resized, mat2Resized, result);

        // Освобождение ресурсов
        mat1Resized.release();
        mat2Resized.release();

        return result;
    }
}
