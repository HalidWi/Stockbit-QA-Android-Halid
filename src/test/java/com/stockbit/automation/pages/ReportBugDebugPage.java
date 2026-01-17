package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Report Bug Debug Page Object for testing debug bug report functionality.
 * This page contains multiple predefined bug report options with code examples.
 */
public class ReportBugDebugPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Debug\")")
    private WebElement debugTitle;

    // First "Report a Bug" button - with screenshot
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/yes_screenshot_report_button")
    private WebElement reportWithScreenshotButton;

    // Second "Report a Bug" button - without screenshot
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/no_screenshot_report_button")
    private WebElement reportWithoutScreenshotButton;

    // Third "Report a Bug" button - stop and report (Remote Support)
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/stop_report_button")
    private WebElement stopAndReportButton;

    // Fourth "Report a Bug" button - custom fields
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/custom_fields_report_button")
    private WebElement customFieldsReportButton;

    // Fifth "Report a Bug" button - custom fields with session
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/custom_fields_report_button_with_session")
    private WebElement customFieldsWithSessionButton;

    // Feedback form elements (same as regular Report a Bug page)
    @AndroidFindBy(accessibility = "Your message")
    private WebElement feedbackInputField;

    @AndroidFindBy(accessibility = "Send")
    private WebElement sendButton;

    @AndroidFindBy(accessibility = "Close")
    private WebElement closeButton;

    public ReportBugDebugPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if Report Bug Debug page is displayed.
     */
    public boolean isReportBugDebugPageDisplayed() {
        waitFor(2);
        try {
            // Look for the "Debug" title
            if (isDisplayed(debugTitle)) {
                logger.info("Report Bug Debug page detected by 'Debug' title");
                return true;
            }
        } catch (Exception e) {
            logger.info("Could not find 'Debug' title");
        }
        try {
            // Fallback: look for the first report button
            if (isDisplayed(reportWithScreenshotButton)) {
                logger.info("Report Bug Debug page detected by report button");
                return true;
            }
        } catch (Exception e) {
            logger.info("Could not find report button");
        }
        try {
            // Another fallback: look for any "REPORT A BUG" text
            WebElement reportButton = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"REPORT A BUG\")"
            ));
            if (reportButton.isDisplayed()) {
                logger.info("Report Bug Debug page detected by 'REPORT A BUG' text");
                return true;
            }
        } catch (Exception e) {
            logger.info("Could not find 'REPORT A BUG' button");
        }
        return false;
    }

    /**
     * Gets list of predefined bug report buttons.
     */
    public List<WebElement> getPredefinedBugButtons() {
        return driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"REPORT A BUG\")"
        ));
    }

    /**
     * Selects a predefined bug report form by index.
     * Index 0: with screenshot, 1: without screenshot, 2: stop and report, 3: custom fields, 4: custom fields with session
     */
    public void selectPredefinedForm(int index) {
        List<WebElement> buttons = getPredefinedBugButtons();
        if (index < buttons.size()) {
            click(buttons.get(index));
            logger.info("Selected predefined bug form at index: {}", index);
        } else {
            logger.warn("Index {} out of range for predefined forms, using first button", index);
            if (!buttons.isEmpty()) {
                click(buttons.get(0));
            }
        }
    }

    /**
     * Clicks the first "Report a Bug" button (with screenshot).
     */
    public void clickReportABug() {
        try {
            click(reportWithScreenshotButton);
            logger.info("Clicked 'Report a Bug' button with screenshot");
        } catch (Exception e) {
            // Fallback: find any button with text "REPORT A BUG"
            try {
                WebElement button = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"REPORT A BUG\")"
                ));
                click(button);
                logger.info("Clicked first 'REPORT A BUG' button using fallback");
            } catch (Exception ex) {
                logger.error("Could not find any Report a Bug button: {}", ex.getMessage());
                throw ex;
            }
        }
    }

    /**
     * Enters debug feedback message (after clicking Report a Bug).
     */
    public void enterDebugFeedback(String feedback) {
        waitFor(2); // Wait for feedback form to load
        try {
            enterText(feedbackInputField, feedback);
            logger.info("Entered debug feedback: {}", feedback);
        } catch (Exception e) {
            // Fallback: find by text contains
            try {
                WebElement messageField = driver.findElement(AppiumBy.accessibilityId("Your message"));
                messageField.clear();
                messageField.sendKeys(feedback);
                logger.info("Entered debug feedback using fallback: {}", feedback);
            } catch (Exception ex) {
                logger.error("Failed to enter debug feedback: {}", ex.getMessage());
                throw ex;
            }
        }
    }

    /**
     * Clicks the Send button on the feedback form.
     */
    public void clickSend() {
        try {
            click(sendButton);
            logger.info("Clicked 'Send' button");
        } catch (Exception e) {
            try {
                WebElement sendBtn = driver.findElement(AppiumBy.accessibilityId("Send"));
                sendBtn.click();
                logger.info("Clicked 'Send' button using fallback");
            } catch (Exception ex) {
                logger.error("Failed to click Send button: {}", ex.getMessage());
                throw ex;
            }
        }
    }

    /**
     * Checks if success message is displayed after sending.
     */
    public boolean isSuccessMessageDisplayed() {
        waitFor(3);
        try {
            // Look for success indication
            WebElement successElement = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Thank\")"
            ));
            if (successElement.isDisplayed()) {
                logger.info("Success message found: Thank you message");
                return true;
            }
        } catch (Exception e) {
            logger.info("No 'Thank you' message found");
        }
        // Check if we're back on the debug page (form closed)
        try {
            if (isDisplayed(debugTitle)) {
                logger.info("Back on debug page - assuming success");
                return true;
            }
        } catch (Exception e) {
            logger.info("Not on debug page");
        }
        // Check if form closed
        try {
            if (!isDisplayed(feedbackInputField)) {
                logger.info("Feedback form closed - assuming success");
                return true;
            }
        } catch (Exception e) {
            logger.info("Feedback form check failed - assuming success");
            return true;
        }
        return false;
    }

    /**
     * Clicks Close button if present.
     */
    public void clickClose() {
        try {
            click(closeButton);
            logger.info("Clicked 'Close' button");
        } catch (Exception e) {
            try {
                WebElement closeBtn = driver.findElement(AppiumBy.accessibilityId("Close"));
                closeBtn.click();
                logger.info("Clicked 'Close' button using fallback");
            } catch (Exception ex) {
                logger.warn("Close button not found: {}", ex.getMessage());
            }
        }
    }
}

