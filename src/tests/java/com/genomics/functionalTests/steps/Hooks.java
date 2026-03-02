package com.genomics.functionalTests.steps;
import com.genomics.functionalTests.base.utils.ConfigReader;
import com.genomics.functionalTests.base.utils.Log;
import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
    public static Playwright playwright;
    public static Browser browser;
    public static BrowserContext context;
    public static Page page;

    @Before
    public void setup(Scenario scenario) {
        playwright = Playwright.create();
        boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("headless", "false"));
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless).setSlowMo(1000));
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