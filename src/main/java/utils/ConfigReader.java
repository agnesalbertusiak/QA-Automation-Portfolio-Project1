package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;

    public static Properties initProperties() {
        if (prop != null) return prop;
        prop = new Properties();
        try (FileInputStream ip = new FileInputStream("config.properties")) {
            prop.load(ip);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
        return prop;
    }

    public static String get(String key) {
        return initProperties().getProperty(key);
    }
}

