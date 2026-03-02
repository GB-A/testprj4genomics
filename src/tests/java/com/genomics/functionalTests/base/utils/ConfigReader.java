package com.genomics.functionalTests.base.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

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
                System.out.println("INFO: No config file found for env=" + env + ", relying on system properties");
            }
        } catch (Exception e) {
            System.out.println("WARN: Could not load " + configFile + " - " + e.getMessage());
        }
    }

    public static String get(String key) {
        // system property (-D flags) always wins over properties file
        String value = System.getProperty(key, prop.getProperty(key));
        if (value == null) {
            throw new RuntimeException(" Property '" + key + "' not found in system properties or config file");
        }
        return value;
    }

    public static String get(String key, String defaultValue) {
        return System.getProperty(key, prop.getProperty(key, defaultValue));
    }
}