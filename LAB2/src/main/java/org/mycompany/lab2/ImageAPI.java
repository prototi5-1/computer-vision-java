package org.mycompany.lab2;

import java.nio.file.Paths;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageAPI {

    private static final Logger log = LoggerFactory.getLogger(ImageAPI.class);

    public enum OSType {
        WINDOWS, MACOS, LINUX, OTHER
    }

    public OSType getOperatingSystemType() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return OSType.MACOS;
        } else if (os.contains("win")) {
            return OSType.WINDOWS;
        } else if (os.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    public ImageAPI() throws Exception {
        OSType osType = getOperatingSystemType();
        String relativeLibraryPath;
        switch (osType) {
            case WINDOWS -> relativeLibraryPath = Config.getProp("pathToNativeLibWin");
            case MACOS -> relativeLibraryPath = Config.getProp("pathToNativeLibMac");
            case LINUX -> relativeLibraryPath = Config.getProp("pathToNativeLibLinux");
            default -> throw new Exception("Unsupported operating system");
        }

        // Преобразование относительного пути в абсолютный, основываясь на расположении папки проекта
        String absoluteLibraryPath = Paths.get(System.getProperty("user.dir"), relativeLibraryPath).toAbsolutePath().toString();
        System.load(absoluteLibraryPath);
        log.info("OpenCV library loaded successfully");
    }
}
