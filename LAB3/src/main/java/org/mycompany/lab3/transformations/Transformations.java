package org.mycompany.lab3.transformations;

import org.opencv.core.*;
import org.mycompany.lab3.core.ImageProcessor;
import org.mycompany.lab3.utils.ImageUtils;

import java.util.List;

/**
 * Класс содержит тестовые сценарии для различных трансформаций.
 */
public class Transformations {

    public static void testSobelAndLaplacian(Mat image) {
        Mat sobelX = ImageProcessor.sobelOperator(image, 1, 0, 3);
        Mat sobelY = ImageProcessor.sobelOperator(image, 0, 1, 3);
        Mat laplacian = ImageProcessor.laplacianOperator(image, 3);

        ImageUtils.saveImage(sobelX, "sobelX.jpg");
        ImageUtils.saveImage(sobelY, "sobelY.jpg");
        ImageUtils.saveImage(laplacian, "laplacian.jpg");
    }

    public static void testMirrorAndRepeat(Mat image) {
        Mat mirrorV = ImageProcessor.mirrorVertical(image);
        Mat mirrorH = ImageProcessor.mirrorHorizontal(image);
        Mat repeatV = ImageProcessor.concatenateVertical(image, mirrorV);
        Mat repeatH = ImageProcessor.concatenateHorizontal(image, mirrorH);

        ImageUtils.saveImage(mirrorV, "mirrorVertical.jpg");
        ImageUtils.saveImage(mirrorH, "mirrorHorizontal.jpg");
        ImageUtils.saveImage(repeatV, "repeatVertical.jpg");
        ImageUtils.saveImage(repeatH, "repeatHorizontal.jpg");
    }

    public static void testResize(Mat image, int newW, int newH) {
        Mat resized = ImageProcessor.resizeImage(image, newW, newH);
        ImageUtils.saveImage(resized, "resized.jpg");
    }

    public static void testRotation(Mat image, double angle, boolean withCrop) {
        Mat rotated = ImageProcessor.rotateImage(image, angle, withCrop);
        String filename = "rotated_" + (int)angle + (withCrop ? "_crop" : "_noCrop") + ".jpg";
        ImageUtils.saveImage(rotated, filename);
    }

    public static void testShift(Mat image, int shiftX, int shiftY) {
        Mat shifted = ImageProcessor.shiftImage(image, shiftX, shiftY);
        String filename = "shifted_" + shiftX + "_" + shiftY + ".jpg";
        ImageUtils.saveImage(shifted, filename);
    }

    public static void testPerspective(Mat image, Point[] srcPoints, Point[] dstPoints) {
        Mat transformed = ImageProcessor.perspectiveTransform(image, srcPoints, dstPoints);
        ImageUtils.saveImage(transformed, "perspective.jpg");
    }
}