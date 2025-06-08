package org.mycompany.lab4;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class Morphology {

    /**
     * Выполняет морфологическую операцию с заданным ядром и формой
     * @param src исходное изображение
     * @param kernelSize размер ядра (3,5,7,9,13,15)
     * @param morphType тип морфологической операции (Imgproc.MORPH_GRADIENT, MORPH_BLACKHAT и т.п.)
     * @param shape форма ядра (Imgproc.MORPH_RECT, MORPH_ELLIPSE и т.п.)
     * @return результат
     */
    public static Mat morphOperation(Mat src, int kernelSize, int morphType, int shape) {
        Mat dst = new Mat();
        Mat kernel = Imgproc.getStructuringElement(shape, new Size(kernelSize, kernelSize));
        Imgproc.morphologyEx(src, dst, morphType, kernel);
        return dst;
    }
}
