package org.mycompany.lab4;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Filters {

    /**
     * Применяет 4 фильтра к изображению:
     * 1) Гауссов фильтр
     * 2) Медианный фильтр
     * 3) Собель (градиент по X)
     * 4) Лапласиан
     * @param src изображение (Mat)
     * @param kernelSize размер ядра (3,5,7 и т.п.)
     * @return массив Mat с результатами
     */
    public static Mat[] applyAll(Mat src, int kernelSize) {
        Mat gaussian = new Mat();
        Imgproc.GaussianBlur(src, gaussian, new Size(kernelSize, kernelSize), 0);

        Mat median = new Mat();
        Imgproc.medianBlur(src, median, kernelSize);

        Mat sobel = new Mat();
        Imgproc.Sobel(src, sobel, CvType.CV_16S, 1, 0, kernelSize);
        Core.convertScaleAbs(sobel, sobel);

        Mat laplacian = new Mat();
        Imgproc.Laplacian(src, laplacian, CvType.CV_16S, kernelSize);
        Core.convertScaleAbs(laplacian, laplacian);

        return new Mat[]{gaussian, median, sobel, laplacian};
    }
}
