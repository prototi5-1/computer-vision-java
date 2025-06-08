package org.mycompany.lab5.utils;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Утилитный класс для работы с изображениями.
 */
public final class ImageUtils {

    private ImageUtils() {
        // static only
    }

    /**
     * Загружает изображение из файла.
     * @param path путь к файлу изображения
     * @return Mat с изображением
     */
    public static Mat loadImage(Path path) {
        Objects.requireNonNull(path);
        Mat image = Imgcodecs.imread(path.toString(), Imgcodecs.IMREAD_COLOR);
        if (image.empty()) {
            throw new IllegalArgumentException("Cannot load image from " + path);
        }
        return image;
    }

    /**
     * Сохраняет изображение в файл.
     * @param image изображение Mat
     * @param path путь к файлу
     * @return true, если успешно
     */
    public static boolean saveImage(Mat image, Path path) {
        Objects.requireNonNull(image);
        Objects.requireNonNull(path);
        return Imgcodecs.imwrite(path.toString(), image);
    }

    /**
     * Сохраняет список изображений с последовательной нумерацией в имени файла.
     * @param images список изображений
     * @param basePath базовый путь (каталог + префикс имени файла)
     * @param extension расширение файла (например "jpg")
     */
    public static void saveImages(List<Mat> images, Path basePath, String extension) {
        Objects.requireNonNull(images);
        Objects.requireNonNull(basePath);
        Objects.requireNonNull(extension);

        IntStream.range(0, images.size())
                .forEach(i -> {
                    Path path = basePath.resolveSibling(basePath.getFileName() + "_" + i + "." + extension);
                    saveImage(images.get(i), path);
                });
    }
}
