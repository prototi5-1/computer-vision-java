package org.mycompany.lab6.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class OpenCVLoaderTest {

    @Test
    public void testLoad() {
        // Проверяет, что загрузка не бросает исключений
        try {
            OpenCVLoader.load();
        } catch (Throwable t) {
            fail("OpenCV library failed to load: " + t.getMessage());
        }
    }
}