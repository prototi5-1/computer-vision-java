package org.mycompany.lab3.utils;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import org.opencv.imgproc.Imgproc;

/**
 * Вспомогательный класс для работы с изображениями.
 * Включает методы для загрузки, сохранения, клонирования, получения параметров,
 * изменения размеров, объединения и работы с каталогами.
 */
public class ImageUtils {

    private static final String INPUT_DIR = "src/main/resources/images/input";
    private static final String OUTPUT_DIR = "src/main/resources/images/output";

    /**
     * Загружает все изображения из каталога input.
     * @return список Mat изображений
     */
    public static List<Mat> loadAllInputImages() {
        File dir = new File(INPUT_DIR);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("Input directory не существует: " + INPUT_DIR);
        }
        return List.of(dir.listFiles((d, name) -> name.matches(".*\\.(jpg|png|bmp)$")))
                .stream()
                .map(file -> Imgcodecs.imread(file.getAbsolutePath()))
                .collect(Collectors.toList());
    }

    /**
     * Сохраняет изображение в каталог output с заданным именем.
     * @param image изображение
     * @param filename имя файла
     */
    public static void saveImage(Mat image, String filename) {
        File dir = new File(OUTPUT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String path = OUTPUT_DIR + File.separator + filename;
        Imgcodecs.imwrite(path, image);
    }

    /**
     * Создает глубокую копию изображения.
     * @param src исходное изображение
     * @return копия изображения
     */
    public static Mat cloneMat(Mat src) {
        return src.clone();
    }

    /**
     * Получает ширину изображения.
     */
    public static int getWidth(Mat image) {
        return image.cols();
    }

    /**
     * Получает высоту изображения.
     */
    public static int getHeight(Mat image) {
        return image.rows();
    }

    /**
     * Получает размер изображения.
     */
    public static Size getSize(Mat image) {
        return image.size();
    }

    /**
     * Создает пустое изображение заданного размера и типа.
     */
    public static Mat createEmptyMat(Size size, int type) {
        return new Mat(size, type);
    }

    /**
     * Создает изображение с заданным цветом.
     */
    public static Mat createSolidColorImage(int width, int height, Scalar color) {
        return new Mat(height, width, CvType.CV_8UC3, color);
    }

    /**
     * Преобразует список изображений к одному размеру.
     * Использует стримы для ресайза.
     */
    public static List<Mat> resizeToCommonSize(List<Mat> images, Size size) {
        return images.stream()
                .map(mat -> {
                    Mat resized = new Mat();
                    Imgproc.resize(mat, resized, size);
                    return resized;
                })
                .collect(Collectors.toList());
    }

    /**
     * Получает список всех изображений из каталога input.
     */
    public static List<Mat> loadAllImagesFromInput() {
        return loadAllInputImages();
    }
}