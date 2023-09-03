package Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseConfig {
    private static final Properties properties;

    static {

        properties = new Properties();
        try (FileInputStream fileProperties = new FileInputStream("src/main/resources/configResponse.properties")) {
            properties.load(fileProperties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String getUrl() {

        return properties.getProperty("url");
    }
}
