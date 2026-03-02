package com.genomics.functionalTests.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private Log() {} // prevent instantiation

    public static Logger get(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    // convenience methods so you can call Log.info() directly from anywhere
    private static final Logger log = LoggerFactory.getLogger("TestLogger");

    public static void info(String message) {
        log.info(message);
    }

    public static void info(String message, Object... args) {
        log.info(message, args);
    }

    public static void warn(String message) {
        log.warn(message);
    }

    public static void warn(String message, Object... args) {
        log.warn(message, args);
    }

    public static void error(String message) {
        log.error(message);
    }

    public static void error(String message, Throwable t) {
        log.error(message, t);
    }

    public static void debug(String message) {
        log.debug(message);
    }

    public static void debug(String message, Object... args) {
        log.debug(message, args);
    }
}