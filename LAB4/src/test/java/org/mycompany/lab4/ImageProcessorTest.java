package org.mycompany.lab4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ImageProcessorTest {

    private static ImageProcessor processor;

    @BeforeAll
    public static void setup() {
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
        processor = new ImageProcessor();
    }

    @Test
    public void testLoadInputImages() {
        List<File> images = processor.loadInputImages();
        assertNotNull(images);
        assertFalse(images.isEmpty(), "Входные изображения не найдены");
        assertTrue(images.stream().allMatch(Utils::isImageFile));
    }

    @Test
    public void testApplyFiltersForKernelSizes() {
        List<File> images = processor.loadInputImages();
        assertFalse(images.isEmpty());

        int[] kernelSizes = {3, 5, 7};
        images.forEach(imageFile -> {
            processor.applyFiltersForKernelSizes(imageFile, kernelSizes);
        });

        File outputDir = new File(Config.getAbsoluteOutputDir());
        assertTrue(outputDir.exists() && outputDir.isDirectory());

        boolean anyResultFile = List.of(outputDir.listFiles())
                .stream()
                .anyMatch(f -> f.getName().matches(".*_gaussian_3\\.png"));
        assertTrue(anyResultFile);
    }

    @Test
    public void morphologyTest() {
        List<File> images = processor.loadInputImages();
        assertFalse(images.isEmpty());

        File imageFile = images.get(0);
        Mat src = Imgcodecs.imread(imageFile.getAbsolutePath());
        assertFalse(src.empty());

        int[] kernelSizes = {3, 5, 7, 9, 13, 15};
        int[] morphTypes = {Imgproc.MORPH_GRADIENT, Imgproc.MORPH_BLACKHAT};
        int[] shapes = {Imgproc.MORPH_RECT, Imgproc.MORPH_ELLIPSE};

        String[] morphPrefixes = {"gr_", "bl_"};
        String[] shapePrefixes = {"rec_", "ell_"};

        IntStream.of(kernelSizes).forEach(ks -> {
            IntStream.range(0, morphTypes.length).forEach(i -> {
                IntStream.range(0, shapes.length).forEach(j -> {
                    Mat dst = Morphology.morphOperation(src, ks, morphTypes[i], shapes[j]);
                    String suffix = morphPrefixes[i] + shapePrefixes[j] + ks;
                    processor.saveImage(dst, Utils.getBaseName(imageFile.getName()), suffix);
                });
            });
        });
    }
}
