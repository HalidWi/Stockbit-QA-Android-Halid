package com.stockbit.automation.drivers;

import com.stockbit.automation.config.AppiumConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

/**
 * Manages AndroidDriver lifecycle using ThreadLocal for parallel execution support.
 * Implements Singleton pattern per thread.
 */
public class DriverManager {

    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<AndroidDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Initializes and returns the AndroidDriver instance.
     * Creates a new driver if one doesn't exist for the current thread.
     *
     * @return AndroidDriver instance
     */
    public static AndroidDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            initializeDriver();
        }
        return driverThreadLocal.get();
    }

    /**
     * Initializes the AndroidDriver with UiAutomator2 options.
     */
    private static void initializeDriver() {
        logger.info("Initializing AndroidDriver with UiAutomator2...");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(AppiumConfig.getPlatformName())
                .setAutomationName(AppiumConfig.getAutomationName())
                .setDeviceName(AppiumConfig.getDeviceName())
                .setApp(AppiumConfig.getAppPath())
                .setNoReset(AppiumConfig.isNoReset())
                .setFullReset(AppiumConfig.isFullReset())
                .setAutoGrantPermissions(true)
                .setNewCommandTimeout(Duration.ofSeconds(600));

        // Add wait options for app startup - increased for stability
        options.setCapability("appium:appWaitActivity", "*");
        options.setCapability("appium:appWaitDuration", 120000);
        options.setCapability("appium:uiautomator2ServerLaunchTimeout", 120000);
        options.setCapability("appium:uiautomator2ServerInstallTimeout", 120000);
        
        // Additional stability options
        options.setCapability("appium:skipServerInstallation", false);
        options.setCapability("appium:skipDeviceInitialization", false);
        options.setCapability("appium:disableWindowAnimation", true);
        options.setCapability("appium:adbExecTimeout", 60000);

        try {
            URL appiumServerUrl = new URL(AppiumConfig.getAppiumServerUrl());
            AndroidDriver driver = new AndroidDriver(appiumServerUrl, options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(AppiumConfig.getImplicitWait()));
            driverThreadLocal.set(driver);
            logger.info("AndroidDriver initialized successfully");
        } catch (MalformedURLException e) {
            logger.error("Invalid Appium server URL: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize AndroidDriver", e);
        }
    }

    /**
     * Quits the driver and removes it from ThreadLocal.
     */
    public static void quitDriver() {
        AndroidDriver driver = driverThreadLocal.get();
        if (driver != null) {
            logger.info("Quitting AndroidDriver...");
            driver.quit();
            driverThreadLocal.remove();
            logger.info("AndroidDriver quit successfully");
        }
    }

    /**
     * Checks if driver is initialized for current thread.
     *
     * @return true if driver exists, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}

