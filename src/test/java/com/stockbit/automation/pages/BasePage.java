package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Base Page class implementing common actions for all page objects.
 * Uses Page Factory pattern with Appium decorators.
 */
public abstract class BasePage {

    protected final AndroidDriver driver;
    protected final WebDriverWait wait;
    protected final Logger logger;

    private static final int DEFAULT_TIMEOUT = 20;
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;

    public BasePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.logger = LoggerFactory.getLogger(this.getClass());
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(DEFAULT_TIMEOUT)), this);
    }

    /**
     * Waits for element to be visible and clicks it with retry logic.
     *
     * @param element WebElement to click
     */
    protected void click(WebElement element) {
        Exception lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                wait.until(ExpectedConditions.visibilityOf(element));
                wait.until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                logger.debug("Clicked element: {}", element);
                waitFor(1); // Small delay after click for stability
                return;
            } catch (Exception e) {
                lastException = e;
                logger.warn("Click attempt {} failed: {}", attempt, e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        throw new RuntimeException("Failed to click element after " + MAX_RETRIES + " attempts", lastException);
    }

    /**
     * Waits for element to be visible and enters text.
     *
     * @param element WebElement to enter text into
     * @param text    Text to enter
     */
    protected void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
        logger.debug("Entered text '{}' into element: {}", text, element);
    }

    /**
     * Gets text from element after waiting for visibility.
     *
     * @param element WebElement to get text from
     * @return Text content of element
     */
    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    /**
     * Checks if element is displayed.
     *
     * @param element WebElement to check
     * @return true if displayed, false otherwise
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Waits for element to be visible.
     *
     * @param element WebElement to wait for
     */
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    /**
     * Waits for element located by given locator to be visible.
     *
     * @param locator By locator
     * @return WebElement once visible
     */
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Finds element by locator.
     *
     * @param locator By locator
     * @return WebElement
     */
    protected WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    /**
     * Finds elements by locator.
     *
     * @param locator By locator
     * @return List of WebElements
     */
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }

    /**
     * Checks if element is present (exists in DOM).
     *
     * @param locator By locator
     * @return true if present, false otherwise
     */
    protected boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    /**
     * Waits for specified duration.
     *
     * @param seconds Duration in seconds
     */
    protected void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Scrolls down on the screen.
     */
    protected void scrollDown() {
        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
                "left", 100,
                "top", 500,
                "width", 200,
                "height", 500,
                "direction", "down",
                "percent", 0.75
        ));
    }

    /**
     * Scrolls up on the screen.
     */
    protected void scrollUp() {
        driver.executeScript("mobile: scrollGesture", java.util.Map.of(
                "left", 100,
                "top", 200,
                "width", 200,
                "height", 500,
                "direction", "up",
                "percent", 0.75
        ));
    }
}

