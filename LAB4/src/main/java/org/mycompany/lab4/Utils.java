package org.mycompany.lab4;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final List<String> IMAGE_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg", ".bmp", ".tiff");

    public static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return IMAGE_EXTENSIONS.stream().anyMatch(name::endsWith);
    }

    public static List<File> listImageFiles(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Invalid directory: " + dir);
        }
        return Arrays.stream(dir.listFiles())
                .filter(File::isFile)
                .filter(Utils::isImageFile)
                .collect(Collectors.toList());
    }

    public static String getBaseName(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex == -1 ? filename : filename.substring(0, dotIndex);
    }
}
