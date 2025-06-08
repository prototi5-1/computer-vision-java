package org.mycompany.lab3;

import org.junit.jupiter.api.*;
import org.opencv.core.*;
import org.mycompany.lab3.utils.ImageUtils;
import org.mycompany.lab3.transformations.Transformations;

import java.util.List;
import java.util.stream.IntStream;

public class ImageProcessingTest {

    private static List<Mat> inputImages;

    @BeforeAll
    public static void setup() {
        // Загрузка библиотеки OpenCV
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
        // Загрузка всех входных изображений
        inputImages = ImageUtils.loadAllImagesFromInput();
        Assertions.assertFalse(inputImages.isEmpty(), "Не удалось загрузить входные изображения");
    }

    @Test
    public void testOperators() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Transformations.testSobelAndLaplacian(image);
        });
    }

    @Test
    public void testMirrorAndRepeat() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Transformations.testMirrorAndRepeat(image);
        });
    }

    @Test
    public void testResize() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Transformations.testResize(image, 300, 200);
        });
    }

    @Test
    public void testRotation() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Transformations.testRotation(image, 45, true);
            Transformations.testRotation(image, 90, false);
        });
    }

    @Test
    public void testShift() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Transformations.testShift(image, 50, 30);
            Transformations.testShift(image, -50, -30);
        });
    }

    @Test
    public void testPerspective() {
        IntStream.range(0, inputImages.size()).forEach(i -> {
            Mat image = inputImages.get(i);
            // Логирование обработки изображения
            System.out.println("Обработка изображения " + (i + 1));
            Point[] srcPoints = {
                    new Point(0, 0),
                    new Point(image.cols() - 1, 0),
                    new Point(image.cols() - 1, image.rows() - 1),
                    new Point(0, image.rows() - 1)
            };
            Point[] dstPoints = {
                    new Point(0, 0),
                    new Point(image.cols() - 1, 20),
                    new Point(image.cols() - 20, image.rows() - 1),
                    new Point(20, image.rows() - 20)
            };
            Transformations.testPerspective(image, srcPoints, dstPoints);
        });
    }
}
