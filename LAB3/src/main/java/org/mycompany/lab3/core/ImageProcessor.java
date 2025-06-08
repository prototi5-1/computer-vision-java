package org.mycompany.lab3.core;

import org.opencv.core.*;
import org.opencv.imgproc.*;
import org.mycompany.lab3.utils.ImageUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс содержит методы обработки изображений.
 */
public class ImageProcessor {

    /**
     * Оператор Собеля.
     */
    public static Mat sobelOperator(Mat src, int dx, int dy, int ksize) {
        Mat gray = new Mat();
        if (src.channels() > 1) {
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        } else {
            gray = src;
        }
        Mat grad = new Mat();
        Imgproc.Sobel(gray, grad, CvType.CV_16S, dx, dy, ksize);
        Core.convertScaleAbs(grad, grad);
        return grad;
    }

    /**
     * Оператор Лапласа.
     */
    public static Mat laplacianOperator(Mat src, int ksize) {
        Mat gray = new Mat();
        if (src.channels() > 1) {
            Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        } else {
            gray = src;
        }
        Mat laplacian = new Mat();
        Imgproc.Laplacian(gray, laplacian, CvType.CV_16S, ksize);
        Core.convertScaleAbs(laplacian, laplacian);
        return laplacian;
    }

    /**
     * Зеркальное отображение по вертикали.
     */
    public static Mat mirrorVertical(Mat src) {
        Mat dst = new Mat();
        Core.flip(src, dst, 0);
        return dst;
    }

    /**
     * Зеркальное отображение по горизонтали.
     */
    public static Mat mirrorHorizontal(Mat src) {
        Mat dst = new Mat();
        Core.flip(src, dst, 1);
        return dst;
    }

    /**
     * Объединение изображений по горизонтали.
     */
    public static Mat concatenateHorizontal(Mat... images) {
        return concatenateImages(List.of(images), true);
    }

    /**
     * Объединение изображений по вертикали.
     */
    public static Mat concatenateVertical(Mat... images) {
        return concatenateImages(List.of(images), false);
    }

    private static Mat concatenateImages(List<Mat> images, boolean horizontal) {
        if (images.isEmpty()) return new Mat();

        Size size = getCommonSize(images, horizontal);
        List<Mat> resized = images.stream()
                .map(mat -> resizeTo(mat, size))
                .collect(Collectors.toList());

        Mat result = new Mat();
        if (horizontal) {
            Core.hconcat(resized, result);
        } else {
            Core.vconcat(resized, result);
        }
        return result;
    }

    private static Size getCommonSize(List<Mat> mats, boolean horizontal) {
        if (mats.isEmpty()) return new Size(0, 0);
        if (horizontal) {
            int height = mats.stream().mapToInt(Mat::rows).max().orElse(0);
            int widthSum = mats.stream().mapToInt(Mat::cols).sum();
            return new Size(widthSum, height);
        } else {
            int width = mats.stream().mapToInt(Mat::cols).max().orElse(0);
            int heightSum = mats.stream().mapToInt(Mat::rows).sum();
            return new Size(width, heightSum);
        }
    }

    private static Mat resizeTo(Mat src, Size size) {
        Mat dst = new Mat();
        Imgproc.resize(src, dst, size);
        return dst;
    }

    /**
     * Изменение размера изображения.
     */
    public static Mat resizeImage(Mat src, int newWidth, int newHeight) {
        Mat dst = new Mat();
        Imgproc.resize(src, dst, new Size(newWidth, newHeight));
        return dst;
    }

    /**
     * Вращение изображения.
     */
    public static Mat rotateImage(Mat src, double angle, boolean withCrop) {
        Point center = new Point(src.cols() / 2.0, src.rows() / 2.0);
        Mat rotMat = Imgproc.getRotationMatrix2D(center, angle, 1.0);
        if (withCrop) {
            return rotateWithCrop(src, rotMat);
        } else {
            return rotateWithoutCrop(src, rotMat);
        }
    }

    private static Mat rotateWithCrop(Mat src, Mat rotMat) {
        Size size = new Size(src.cols(), src.rows());
        Mat dst = new Mat();
        Imgproc.warpAffine(src, dst, rotMat, size);
        return dst;
    }

    private static Mat rotateWithoutCrop(Mat src, Mat rotMat) {
        // Расчет новых размеров
        double absCos = Math.abs(rotMat.get(0, 0)[0]);
        double absSin = Math.abs(rotMat.get(0, 1)[0]);
        int boundW = (int) (src.rows() * absSin + src.cols() * absCos);
        int boundH = (int) (src.rows() * absCos + src.cols() * absSin);
        Mat newRotMat = rotMat.clone();
        newRotMat.put(0, 2, newRotMat.get(0, 2)[0] + (boundW - src.cols()) / 2.0);
        newRotMat.put(1, 2, newRotMat.get(1, 2)[0] + (boundH - src.rows()) / 2.0);
        Mat dst = new Mat();
        Imgproc.warpAffine(src, dst, newRotMat, new Size(boundW, boundH));
        return dst;
    }

    /**
     * Сдвиг изображения.
     */
    public static Mat shiftImage(Mat src, int shiftX, int shiftY) {
        Mat transMat = Mat.eye(2, 3, CvType.CV_64F);
        transMat.put(0, 2, shiftX);
        transMat.put(1, 2, shiftY);
        Size size = new Size(src.cols(), src.rows());
        Mat dst = new Mat();
        Imgproc.warpAffine(src, dst, transMat, size);
        return dst;
    }

    /**
     * Трансформация перспективы.
     */
    public static Mat perspectiveTransform(Mat src, Point[] srcPoints, Point[] dstPoints) {
        MatOfPoint2f srcPts = new MatOfPoint2f(srcPoints);
        MatOfPoint2f dstPts = new MatOfPoint2f(dstPoints);
        Mat warpMat = Imgproc.getPerspectiveTransform(srcPts, dstPts);
        Mat dst = new Mat();
        Imgproc.warpPerspective(src, dst, warpMat, src.size());
        return dst;
    }
}