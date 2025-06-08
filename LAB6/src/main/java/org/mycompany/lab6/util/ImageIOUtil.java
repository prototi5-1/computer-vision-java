package org.mycompany.lab6.util;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class ImageIOUtil {

    private static final String INPUT_DIR = "src/main/resources/images/input/";
    private static final String OUTPUT_DIR = "src/main/resources/images/output/";

    private ImageIOUtil() {}

    public static Mat loadImage(String filename) {
        Objects.requireNonNull(filename, "Filename must not be null");
        Path path = Path.of(INPUT_DIR, filename);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Input image file does not exist: " + path);
        }
        Mat image = Imgcodecs.imread(path.toString());
        if (image.empty()) {
            throw new IllegalArgumentException("Failed to load image: " + path);
        }
        return image;
    }

    public static boolean saveImage(String filename, Mat image) {
        Objects.requireNonNull(filename, "Filename must not be null");
        Objects.requireNonNull(image, "Image must not be null");
        File outDir = new File(OUTPUT_DIR);
        if (!outDir.exists()) {
            if (!outDir.mkdirs()) {
                throw new IllegalStateException("Failed to create output directory: " + OUTPUT_DIR);
            }
        }
        return Imgcodecs.imwrite(Path.of(OUTPUT_DIR, filename).toString(), image);
    }
}
