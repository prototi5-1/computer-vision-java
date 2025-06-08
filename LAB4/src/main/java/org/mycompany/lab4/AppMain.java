package org.mycompany.lab4;

import org.opencv.core.Core;

import java.io.File;
import java.util.List;

public class AppMain {

    static {
        // Загрузка нативной библиотеки OpenCV
        System.load("/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so");
    }

    public static void main(String[] args) {
        ImageProcessor processor = new ImageProcessor();
        List<File> images = processor.loadInputImages();

        int[] kernelSizes = {3, 5, 7};

        images.forEach(imageFile -> processor.applyFiltersForKernelSizes(imageFile, kernelSizes));
    }
}
