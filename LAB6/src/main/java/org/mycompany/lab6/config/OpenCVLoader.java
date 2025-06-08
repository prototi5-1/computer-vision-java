package org.mycompany.lab6.config;

public final class OpenCVLoader {
    private static final String LIB_PATH = "/home/osboxes/worklib/opencv/build/lib/libopencv_java4110.so";

    private OpenCVLoader() {}

    public static void load() {
        System.load(LIB_PATH);
    }
}
