package com.genomics.functionaltests.steps;

import com.genomics.functionaltests.base.utils.ConfigReader;
import com.genomics.functionaltests.base.utils.Log;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Cucumber Hooks to manage the Playwright browser lifecycle.
 * Handles browser setup before each scenario and cleanup/screenshots after.
 */
public class Hooks {
  public static Playwright playwright;
  public static Browser browser;
  public static BrowserContext context;
  public static Page page;

  /**
   * Initializes the Playwright browser and navigates to the base URL before each scenario.
   *
   * @param scenario the Cucumber scenario object for metadata and tagging
   */
  @Before
  public void setup(Scenario scenario) {
    playwright = Playwright.create();
    boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));
    browser = playwright.chromium()
        .launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).setSlowMo(1000));
    context = browser.newContext();
    page = context.newPage();
    page.navigate(ConfigReader.get("baseUrl"));
    // flag known bug scenarios
    if (scenario.getSourceTagNames().contains("@known-bug")) {
      Log.warn("========================================");
      Log.warn("KNOWN BUG: Scenario '{}' is expected to fail", scenario.getName());
      Log.warn("Tags: {}", scenario.getSourceTagNames());
      Log.warn("========================================");
    }
  }

  /**
   * Closes the browser and attaches screenshots to the report if a scenario fails.
   *
   * @param scenario the Cucumber scenario object to attach screenshots to
   */
  @After
  public void tearDown(Scenario scenario) {
    if (scenario.isFailed()) {
      byte[] screenshot = page.screenshot();
      scenario.attach(screenshot, "image/png", "failure_screenshot");
    }
    if (browser != null) {
      browser.close();
      playwright.close();
    }
  }
}