package config;

import lombok.SneakyThrows;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {
    private Properties properties;
    private InputStream inputStream;

    @SneakyThrows
    public PropertyLoader(String filePath) {
        properties = new Properties();
        inputStream = new FileInputStream(filePath);
        properties.load(inputStream);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
