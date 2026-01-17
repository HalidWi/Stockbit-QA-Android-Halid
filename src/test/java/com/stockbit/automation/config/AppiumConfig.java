package com.stockbit.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Configuration class for Appium settings.
 * Loads configuration from properties file or uses defaults.
 */
public class AppiumConfig {

    private static final Properties properties = new Properties();
    private static final String CONFIG_FILE = "config.properties";
    private static final String APK_RESOURCE = "mda-1.0.13-15.apk";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream is = AppiumConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is != null) {
                properties.load(is);
            } else {
                setDefaults();
            }
        } catch (IOException e) {
            // Use default values if config file not found
            setDefaults();
        }
    }

    private static void setDefaults() {
        properties.setProperty("appium.server.url", "http://127.0.0.1:4723");
        properties.setProperty("platform.name", "Android");
        properties.setProperty("automation.name", "UiAutomator2");
        properties.setProperty("device.name", "emulator-5554");
        properties.setProperty("app.path", APK_RESOURCE);
        properties.setProperty("implicit.wait", "15");
        properties.setProperty("explicit.wait", "20");
        properties.setProperty("no.reset", "false");
        properties.setProperty("full.reset", "false");
    }

    public static String getAppiumServerUrl() {
        return properties.getProperty("appium.server.url", "http://127.0.0.1:4723");
    }

    public static String getPlatformName() {
        return properties.getProperty("platform.name", "Android");
    }

    public static String getAutomationName() {
        return properties.getProperty("automation.name", "UiAutomator2");
    }

    public static String getDeviceName() {
        return properties.getProperty("device.name", "emulator-5554");
    }

    /**
     * Gets the absolute path to the APK file from classpath resources.
     * @return Absolute path to the APK file
     */
    public static String getAppPath() {
        String appPath = properties.getProperty("app.path", APK_RESOURCE);
        
        // Try to load from classpath resources first
        URL resourceUrl = AppiumConfig.class.getClassLoader().getResource(appPath);
        if (resourceUrl != null) {
            try {
                Path path = Paths.get(resourceUrl.toURI());
                return path.toAbsolutePath().toString();
            } catch (URISyntaxException e) {
                // Fall back to file path
            }
        }
        
        // Fallback: try as direct file path
        return Paths.get(appPath).toAbsolutePath().toString();
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public static boolean isNoReset() {
        return Boolean.parseBoolean(properties.getProperty("no.reset", "false"));
    }

    public static boolean isFullReset() {
        return Boolean.parseBoolean(properties.getProperty("full.reset", "false"));
    }
}
