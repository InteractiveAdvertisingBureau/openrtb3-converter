package net.media.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesFileLoader {

  public static Properties templateProperties = new Properties() {{
    putAll(loadProperties("admTemplate.properties"));
  }};

  public static Properties loadProperties(String propertiesFileName) {

    Properties properties = new Properties();
    try (InputStream input = PropertiesFileLoader.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
      if (input != null) {
        properties.load(input);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return properties;
  }

}
