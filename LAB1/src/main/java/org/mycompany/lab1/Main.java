package org.mycompany.lab1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            // Создаем экземпляр вашего API
            ImageAPI api = new ImageAPI();
            log.info("OpenCV successfully initialized!");
        } catch (Exception e) {
            log.error("Error initializing OpenCV: " + e.getMessage(), e);
        }
    }
}
