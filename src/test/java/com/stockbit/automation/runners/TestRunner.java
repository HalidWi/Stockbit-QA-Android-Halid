package com.stockbit.automation.runners;

import org.junit.platform.suite.api.*;

import static io.cucumber.junit.platform.engine.Constants.*;

/**
 * JUnit 5 Test Runner for Cucumber tests.
 * Configures Cucumber options and Allure reporting.
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty," +
        "html:target/cucumber-reports/cucumber.html," +
        "json:target/cucumber-reports/cucumber.json," +
        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.stockbit.automation.steps,com.stockbit.automation.hooks")
@ConfigurationParameter(key = FEATURES_PROPERTY_NAME, value = "src/test/resources/features")
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "@drawing or (@e2e and @checkout)")
@ConfigurationParameter(key = SNIPPET_TYPE_PROPERTY_NAME, value = "camelcase")
public class TestRunner {
    // This class serves as an entry point for running Cucumber tests with JUnit 5
    // Configuration is done via annotations
}