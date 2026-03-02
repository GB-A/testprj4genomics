package com.genomics.functionaltests.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class providing static methods for centralized logging across the test suite.
 * This class wraps SLF4J to provide a consistent "TestLogger" context.
 */
public class Log {

  // convenience methods so you can call Log.info() directly from anywhere
  private static final Logger log = LoggerFactory.getLogger("TestLogger");

  private Log() {
  } // prevent instantiation

  /**
   * Retrieves a logger instance for a specific class.
   *
   * @param clazz the class for which to retrieve the logger
   * @return an SLF4J Logger instance
   */
  public static Logger get(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }

  /**
   * Logs an info-level message.
   *
   * @param message the message to log
   */
  public static void info(String message) {
    log.info(message);
  }

  /**
   * Logs a parameterized info-level message.
   *
   * @param message the message template containing {} placeholders
   * @param args    the arguments to be substituted into the placeholders
   */
  public static void info(String message, Object... args) {
    log.info(message, args);
  }

  /**
   * Logs a warning-level message.
   *
   * @param message the warning message to log
   */
  public static void warn(String message) {
    log.warn(message);
  }

  /**
   * Logs a parameterized warning-level message.
   *
   * @param message the message template
   * @param args    the arguments for substitution
   */
  public static void warn(String message, Object... args) {
    log.warn(message, args);
  }

  /**
   * Logs an error-level message.
   *
   * @param message the error message to log
   */
  public static void error(String message) {
    log.error(message);
  }

  /**
   * Logs an error-level message along with a stack trace.
   *
   * @param message the error message
   * @param t       the throwable or exception to log
   */
  public static void error(String message, Throwable t) {
    log.error(message, t);
  }

  /**
   * Logs a debug-level message.
   *
   * @param message the debug message to log
   */
  public static void debug(String message) {
    log.debug(message);
  }

  /**
   * Logs a parameterized debug-level message.
   *
   * @param message the message template
   * @param args    the arguments for substitution
   */
  public static void debug(String message, Object... args) {
    log.debug(message, args);
  }
}