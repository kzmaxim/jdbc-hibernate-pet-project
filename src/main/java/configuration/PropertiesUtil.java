package main.java.configuration;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {
    private PropertiesUtil() {}
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }
    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
    private static void loadProperties() {
        try(var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("File 'database.properties' not found in classpath");
            }
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
