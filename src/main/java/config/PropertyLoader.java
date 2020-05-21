package config;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyLoader {
    private final String BASE_PATH = "src/main/resources/";
    private Properties properties;
    private InputStream inputStream;

    @SneakyThrows
    public PropertyLoader(String path) {
        properties = new Properties();
        inputStream = new FileInputStream(this.BASE_PATH + path);
        properties.load(inputStream);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
