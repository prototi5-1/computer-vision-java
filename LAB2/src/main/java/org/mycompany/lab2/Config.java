package org.mycompany.lab2;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try {
            // Загрузка ресурса из classpath
            try (var input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("config.properties not found in resources!");
                }
                properties.load(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения значения по ключу
    public static String getProp(String key) {
        return properties.getProperty(key);
    }

    // Метод для получения абсолютного пути к файлу
    public static String getAbsolutePath(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }
}