package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Checkout Complete Page Object for order confirmation.
 */
public class CheckoutCompletePage extends BasePage {

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Checkout Complete')]")
    private WebElement checkoutCompleteTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Thank you for your order')]")
    private WebElement thankYouMessage;

    @AndroidFindBy(accessibility = "Tap to open catalog")
    private WebElement continueShoppingButton;

    @AndroidFindBy(xpath = "//android.widget.ImageView")
    private WebElement checkmarkImage;

    public CheckoutCompletePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if checkout complete page is displayed.
     */
    public boolean isCheckoutCompletePageDisplayed() {
        try {
            waitFor(2);
            return isDisplayed(checkoutCompleteTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if "Checkout Complete" text is displayed.
     */
    public boolean isCheckoutCompleteTitleDisplayed() {
        try {
            return isDisplayed(checkoutCompleteTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if "Thank you for your order" message is displayed.
     */
    public boolean isThankYouMessageDisplayed() {
        try {
            return isDisplayed(thankYouMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets checkout complete title text.
     */
    public String getCheckoutCompleteTitle() {
        return getText(checkoutCompleteTitle);
    }

    /**
     * Gets thank you message text.
     */
    public String getThankYouMessage() {
        return getText(thankYouMessage);
    }

    /**
     * Clicks Continue Shopping button.
     * Uses multiple strategies to find and click the button.
     */
    public void clickContinueShopping() {
        try {
            click(continueShoppingButton);
            logger.info("Clicked Continue Shopping button via primary locator");
            return;
        } catch (Exception e) {
            logger.debug("Primary locator failed: {}", e.getMessage());
        }
        
        // Fallback 1: Try by text "Continue Shopping"
        try {
            WebElement button = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Continue Shopping\")"));
            button.click();
            logger.info("Clicked Continue Shopping button via text locator");
            return;
        } catch (Exception e) {
            logger.debug("Text locator failed: {}", e.getMessage());
        }
    }

    /**
     * Verifies order completion and returns to shopping.
     */
    public void verifyCompletionAndContinueShopping() {
        if (isCheckoutCompleteTitleDisplayed() && isThankYouMessageDisplayed()) {
            logger.info("Order completed successfully!");
            logger.info("Title: {}", getCheckoutCompleteTitle());
            logger.info("Message: {}", getThankYouMessage());
            clickContinueShopping();
        } else {
            throw new RuntimeException("Checkout complete page verification failed");
        }
    }

    /**
     * Checks if checkout is successful.
     */
    public boolean isCheckoutSuccessful() {
        return isCheckoutCompleteTitleDisplayed() && isThankYouMessageDisplayed();
    }
}

