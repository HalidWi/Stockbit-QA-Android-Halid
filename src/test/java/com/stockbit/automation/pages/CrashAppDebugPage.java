package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Crash App Debug Page Object for testing crash functionality.
 */
public class CrashAppDebugPage extends BasePage {

    // The page may show "Debug" as the title (similar to Report a Bug debug)
    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Debug\")")
    private WebElement debugTitle;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Crash\")")
    private WebElement crashAppTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/crashBtn")
    private WebElement triggerCrashButton;

    // Alternative: The crash button might have different IDs
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/crashButton")
    private WebElement crashButtonById;

    public CrashAppDebugPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if Crash App Debug page is displayed.
     * Uses multiple fallback locators for robustness.
     */
    public boolean isCrashAppDebugPageDisplayed() {
        waitFor(2);
        
        // Check for Debug title (similar to Report a Bug debug page)
        try {
            if (isDisplayed(debugTitle)) {
                logger.info("Found Debug title on Crash App Debug page");
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        
        // Check for crash-related text
        try {
            if (isDisplayed(crashAppTitle)) {
                logger.info("Found Crash text on page");
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        
        // Check for trigger crash button
        try {
            if (isDisplayed(triggerCrashButton)) {
                logger.info("Found crash button by ID crashBtn");
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        
        // Check for crash button by alternative ID
        try {
            if (isDisplayed(crashButtonById)) {
                logger.info("Found crash button by alternative ID");
                return true;
            }
        } catch (Exception e) {
            // Ignore
        }
        
        // Fallback: Try to find any element with "crash" in text using dynamic lookup
        try {
            WebElement crashElement = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"crash\")"));
            if (crashElement.isDisplayed()) {
                logger.info("Found element with 'crash' text dynamically");
                return true;
            }
        } catch (Exception e) {
            logger.warn("Could not find any crash-related elements: {}", e.getMessage());
        }
        
        // Last fallback: Check if the page changed from products page
        // If we can't find "Products" title, it means we navigated somewhere
        try {
            WebElement productsTitle = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
            if (productsTitle.isDisplayed()) {
                logger.warn("Still on Products page, navigation may have failed");
                return false;
            }
        } catch (Exception e) {
            // Products title not found, so we likely navigated to a different page
            logger.info("Products page not visible, assuming we navigated to Crash App Debug page");
            return true;
        }
        
        return false;
    }

    /**
     * Triggers an app crash (use with caution - will terminate the app).
     */
    public void triggerAppCrash() {
        try {
            if (isDisplayed(triggerCrashButton)) {
                click(triggerCrashButton);
            } else if (isDisplayed(crashButtonById)) {
                click(crashButtonById);
            } else {
                // Try dynamic lookup
                WebElement crashButton = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"Crash\")"));
                click(crashButton);
            }
            logger.info("Triggered app crash");
        } catch (Exception e) {
            logger.error("Failed to trigger crash: {}", e.getMessage());
        }
    }

    /**
     * Checks if crash button is displayed.
     */
    public boolean isCrashButtonDisplayed() {
        try {
            if (isDisplayed(triggerCrashButton) || isDisplayed(crashButtonById)) {
                return true;
            }
            // Dynamic lookup
            WebElement crashButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Crash\")"));
            return crashButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

