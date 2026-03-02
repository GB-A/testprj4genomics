package com.genomics.functionaltests.base.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Utility class for reading configuration properties from environment-specific
 * properties files or system properties.
 */

public class ConfigReader {
  /**
   * Internal properties storage loaded from the configuration file.
   */

  private static final Properties prop = new Properties();

  static {
    // try to load a properties file but don't fail if it doesn't exist
    // user or jenkins might  supply everything via -D system properties
    String env = System.getProperty("env", "dev");
    String configFile = "config-" + env + ".properties";

    try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(configFile)) {
      if (is != null) {
        prop.load(is);
      } else {
        System.out.println(
            "INFO: No config file found for env=" + env + ", relying on system properties");
      }
    } catch (Exception e) {
      System.out.println("WARN: Could not load " + configFile + " - " + e.getMessage());
    }
  }

  /**
   * Retrieves the value of a property. System properties take precedence
   * over the configuration file.
   *
   * @param key the name of the property to retrieve
   * @return the value of the property
   * @throws RuntimeException if the property is not found in either source
   */
  public static String get(String key) {
    // system property (-D flags) always wins over properties file
    String value = System.getProperty(key, prop.getProperty(key));
    if (value == null) {
      throw new RuntimeException(
          " Property '" + key + "' not found in system properties or config file");
    }
    return value;
  }

  /**
   * Retrieves the value of a property, returning a default value if not found.
   *
   * @param key          the name of the property to retrieve
   * @param defaultValue the value to return if the property is missing
   * @return the value of the property or the default value
   */

  public static String get(String key, String defaultValue) {
    return System.getProperty(key, prop.getProperty(key, defaultValue));
  }
}