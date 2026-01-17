package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Reset App State Page Object for testing reset app functionality.
 */
public class ResetAppStatePage extends BasePage {

    @AndroidFindBy(id = "android:id/alertTitle")
    private WebElement confirmationPopupTitle;

    @AndroidFindBy(id = "android:id/message")
    private WebElement confirmationPopupMessage;

    @AndroidFindBy(id = "android:id/button1")
    private WebElement primaryDialogButton;

    @AndroidFindBy(id = "android:id/button2")
    private WebElement secondaryDialogButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productsTitle;

    public ResetAppStatePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if reset confirmation popup is displayed.
     */
    public boolean isConfirmationPopupDisplayed() {
        waitFor(2);
        try {
            // Try to find the alert dialog title
            if (isDisplayed(confirmationPopupTitle)) {
                logger.info("Found confirmation popup with title");
                return true;
            }
        } catch (Exception e) {
            logger.info("Alert title not found via id, trying alternative locators");
        }

        // Try finding by text content
        try {
            WebElement resetPopup = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Reset App\")"
            ));
            if (resetPopup.isDisplayed()) {
                logger.info("Found popup with 'Reset App' text");
                return true;
            }
        } catch (Exception e) {
            logger.info("No popup with 'Reset App' text found");
        }

        // Try finding dialog buttons
        try {
            List<WebElement> buttons = driver.findElements(AppiumBy.androidUIAutomator(
                    "new UiSelector().className(\"android.widget.Button\")"
            ));
            if (!buttons.isEmpty()) {
                for (WebElement btn : buttons) {
                    String text = btn.getText();
                    if (text != null && (text.toUpperCase().contains("RESET") || text.toUpperCase().contains("CANCEL"))) {
                        logger.info("Found dialog button with text: {}", text);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.info("No dialog buttons found");
        }

        return false;
    }

    /**
     * Clicks Reset App button in the confirmation popup.
     * Tries multiple locator strategies to find the RESET APP button.
     */
    public void clickResetAppButton() {
        waitFor(1);
        
        // Strategy 1: Try by button ID (android:id/button1 is typically the positive button)
        try {
            if (isDisplayed(primaryDialogButton)) {
                String text = primaryDialogButton.getText();
                logger.info("Primary dialog button text: {}", text);
                click(primaryDialogButton);
                logger.info("Clicked primary dialog button (RESET APP)");
                return;
            }
        } catch (Exception e) {
            logger.info("Primary dialog button not found via ID");
        }

        // Strategy 2: Try finding by exact text "RESET APP" (uppercase)
        try {
            WebElement resetButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"RESET APP\")"
            ));
            click(resetButton);
            logger.info("Clicked 'RESET APP' button (uppercase)");
            return;
        } catch (Exception e) {
            logger.info("Button with text 'RESET APP' not found");
        }

        // Strategy 3: Try finding by text "Reset App" (title case)
        try {
            WebElement resetButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Reset App\")"
            ));
            click(resetButton);
            logger.info("Clicked 'Reset App' button (title case)");
            return;
        } catch (Exception e) {
            logger.info("Button with text 'Reset App' not found");
        }

        // Strategy 4: Try finding button containing "RESET" text
        try {
            WebElement resetButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"RESET\")"
            ));
            click(resetButton);
            logger.info("Clicked button containing 'RESET'");
            return;
        } catch (Exception e) {
            logger.info("Button containing 'RESET' not found");
        }

        // Strategy 5: Try finding button containing "Reset" text (case insensitive approach)
        try {
            List<WebElement> buttons = driver.findElements(AppiumBy.className("android.widget.Button"));
            for (WebElement btn : buttons) {
                String text = btn.getText();
                if (text != null && text.toUpperCase().contains("RESET")) {
                    click(btn);
                    logger.info("Clicked button with text: {}", text);
                    return;
                }
            }
        } catch (Exception e) {
            logger.info("Could not find reset button via button class scan");
        }

        throw new RuntimeException("Could not find RESET APP button in the popup");
    }

    /**
     * Clicks Cancel button.
     */
    public void clickCancel() {
        try {
            if (isDisplayed(secondaryDialogButton)) {
                click(secondaryDialogButton);
                logger.info("Clicked 'Cancel' button via secondary dialog button");
                return;
            }
        } catch (Exception e) {
            logger.info("Secondary dialog button not found");
        }

        try {
            WebElement cancelButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"CANCEL\")"
            ));
            click(cancelButton);
            logger.info("Clicked 'CANCEL' button");
        } catch (Exception e) {
            try {
                WebElement cancelButton = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Cancel\")"
                ));
                click(cancelButton);
                logger.info("Clicked 'Cancel' button");
            } catch (Exception ex) {
                logger.warn("Cancel button not found");
            }
        }
    }

    /**
     * Clicks OK button if present.
     */
    public void clickOk() {
        try {
            WebElement okButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"OK\")"
            ));
            click(okButton);
            logger.info("Clicked 'OK' button");
        } catch (Exception e) {
            logger.warn("OK button not found");
        }
    }

    /**
     * Checks if app state has been reset (returns to products page).
     */
    public boolean isAppResetSuccessfully() {
        waitFor(2);
        try {
            // After reset, we should be back on the products page
            return isDisplayed(productsTitle);
        } catch (Exception e) {
            logger.warn("Could not verify products page after reset: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets the confirmation popup message.
     */
    public String getConfirmationMessage() {
        try {
            return getText(confirmationPopupMessage);
        } catch (Exception e) {
            return "";
        }
    }
}

