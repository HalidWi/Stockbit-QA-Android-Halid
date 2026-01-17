package com.stockbit.automation.context;

import com.stockbit.automation.drivers.DriverManager;
import io.appium.java_client.android.AndroidDriver;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Context class for sharing state between step definitions.
 * Used with PicoContainer for dependency injection in Cucumber.
 */
public class TestContext {

    private final Map<String, Object> scenarioContext;

    public TestContext() {
        this.scenarioContext = new HashMap<>();
    }

    /**
     * Gets the AndroidDriver instance.
     *
     * @return AndroidDriver
     */
    public AndroidDriver getDriver() {
        return DriverManager.getDriver();
    }

    /**
     * Stores a value in the scenario context.
     *
     * @param key   the key to store
     * @param value the value to store
     */
    public void setContext(String key, Object value) {
        scenarioContext.put(key, value);
    }

    /**
     * Retrieves a value from the scenario context.
     *
     * @param key the key to retrieve
     * @return the stored value, or null if not found
     */
    public Object getContext(String key) {
        return scenarioContext.get(key);
    }

    /**
     * Checks if a key exists in the scenario context.
     *
     * @param key the key to check
     * @return true if key exists, false otherwise
     */
    public boolean containsKey(String key) {
        return scenarioContext.containsKey(key);
    }

    /**
     * Clears all stored context data.
     */
    public void clearContext() {
        scenarioContext.clear();
    }
}

