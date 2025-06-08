package org.mycompany.lab4;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

public class ImageProcessor {

    private final File inputDir = new File(Config.getAbsoluteInputDir());
    private final File outputDir = new File(Config.getAbsoluteOutputDir());

    public ImageProcessor() {
        if (!outputDir.exists() && !outputDir.mkdirs()) {
            throw new RuntimeException("Не удалось создать выходную директорию: " + outputDir);
        }
    }

    public List<File> loadInputImages() {
        return Utils.listImageFiles(inputDir);
    }

    public void saveImage(Mat image, String baseName, String suffix) {
        String filename = baseName + suffix + ".png";
        File outFile = new File(outputDir, filename);
        Imgcodecs.imwrite(outFile.getAbsolutePath(), image);
    }

    /**
     * Применяет 4 фильтра для каждого размера ядра из массива, сохраняет результаты
     * @param imageFile файл изображения
     * @param kernelSizes массив размеров ядер
     */
    public void applyFiltersForKernelSizes(File imageFile, int[] kernelSizes) {
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath());
        if (src.empty()) {
            throw new RuntimeException("Не удалось загрузить изображение: " + imageFile.getName());
        }
        String baseName = Utils.getBaseName(imageFile.getName());

        IntStream.of(kernelSizes).forEach(kernelSize -> {
            Mat[] filtered = Filters.applyAll(src, kernelSize);
            String[] suffixes = {"_gaussian_" + kernelSize, "_median_" + kernelSize, "_sobel_" + kernelSize, "_laplacian_" + kernelSize};
            IntStream.range(0, filtered.length).forEach(i -> saveImage(filtered[i], baseName, suffixes[i]));
        });
    }
}
