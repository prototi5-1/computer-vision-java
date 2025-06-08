package org.mycompany.lab4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new RuntimeException("config.properties not found");
            }
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getInputDir() {
        return props.getProperty("input.directory");
    }

    public static String getOutputDir() {
        return props.getProperty("output.directory");
    }

    public static String getAbsoluteInputDir() {
        return Paths.get(getInputDir()).toAbsolutePath().toString();
    }

    public static String getAbsoluteOutputDir() {
        return Paths.get(getOutputDir()).toAbsolutePath().toString();
    }
}
