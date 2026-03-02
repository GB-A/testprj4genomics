package com.genomics.functionalTests.runner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.FEATURES_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
// 1. Tell Cucumber where the .feature files live (relative to src/tests/resources)
@SelectClasspathResource("features")
// 2. Map the "Glue" to the exact package containing your Step Definition classes
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.genomics.functionalTests.steps")
// 3. Generate a pretty HTML report in your target folder
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/report.html")
public class TestRunner {
    /* This class is an empty 'trigger'.
       JUnit 5 uses these annotations to launch the Cucumber Engine.
    */
}