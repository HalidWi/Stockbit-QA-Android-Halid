package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Payment Page Object for entering payment details during checkout.
 */
public class PaymentPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/enterPaymentMethodTV")
    private WebElement paymentMethodTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement fullNameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cardNumberET")
    private WebElement cardNumberField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/expirationDateET")
    private WebElement expirationDateField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/securityCodeET")
    private WebElement securityCodeField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/billingAddressCB")
    private WebElement billingAddressCheckbox;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement reviewOrderButton;

    // Billing address fields (shown when checkbox is unchecked)
    @AndroidFindBy(accessibility = "Bill To Full Name* input field")
    private WebElement billToFullNameField;

    @AndroidFindBy(accessibility = "Bill To Address Line 1* input field")
    private WebElement billToAddressLine1Field;

    @AndroidFindBy(accessibility = "Bill To Address Line 2 input field")
    private WebElement billToAddressLine2Field;

    @AndroidFindBy(accessibility = "Bill To City* input field")
    private WebElement billToCityField;

    @AndroidFindBy(accessibility = "Bill To State/Region input field")
    private WebElement billToStateField;

    @AndroidFindBy(accessibility = "Bill To Zip Code* input field")
    private WebElement billToZipCodeField;

    @AndroidFindBy(accessibility = "Bill To Country* input field")
    private WebElement billToCountryField;

    // Error messages - app shows "Value looks invalid." for all fields
    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Value looks invalid\")")
    private WebElement validationError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameErrorTV")
    private WebElement fullNameError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cardNumberErrorTV")
    private WebElement cardNumberError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/expirationDateErrorTV")
    private WebElement expirationDateError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/securityCodeErrorTV")
    private WebElement securityCodeError;

    public PaymentPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if payment page is displayed.
     */
    public boolean isPaymentPageDisplayed() {
        try {
            waitFor(3); // Wait longer for page transition
            // Check for "Enter a payment method" text to confirm we're on payment page
            WebElement paymentTitle = driver.findElement(
                io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/enterPaymentMethodTV"));
            return paymentTitle.isDisplayed();
        } catch (Exception e) {
            // Fallback: check for Review Order button
            try {
                return isDisplayed(reviewOrderButton);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Enters full name.
     */
    public void enterFullName(String fullName) {
        enterText(fullNameField, fullName);
        logger.info("Entered full name: {}", fullName);
    }

    /**
     * Enters card number.
     */
    public void enterCardNumber(String cardNumber) {
        enterText(cardNumberField, cardNumber);
        logger.info("Entered card number");
    }

    /**
     * Enters expiration date.
     */
    public void enterExpirationDate(String expirationDate) {
        enterText(expirationDateField, expirationDate);
        logger.info("Entered expiration date");
    }

    /**
     * Enters security code.
     */
    public void enterSecurityCode(String securityCode) {
        enterText(securityCodeField, securityCode);
        logger.info("Entered security code");
    }

    /**
     * Clicks Review Order button.
     */
    public void clickReviewOrder() {
        scrollDown();
        click(reviewOrderButton);
        logger.info("Clicked Review Order button");
    }

    /**
     * Toggles billing address checkbox.
     */
    public void toggleBillingAddressCheckbox() {
        click(billingAddressCheckbox);
        logger.info("Toggled billing address checkbox");
    }

    /**
     * Unchecks billing address same as shipping checkbox.
     */
    public void uncheckBillingAddressSameAsShipping() {
        toggleBillingAddressCheckbox();
        logger.info("Unchecked billing address same as shipping");
    }

    /**
     * Checks billing address same as shipping checkbox.
     */
    public void checkBillingAddressSameAsShipping() {
        toggleBillingAddressCheckbox();
        logger.info("Checked billing address same as shipping");
    }

    /**
     * Checks if billing address fields are visible.
     */
    public boolean areBillingAddressFieldsVisible() {
        try {
            return isDisplayed(billToFullNameField);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Fills payment details.
     */
    public void fillPaymentDetails(String fullName, String cardNumber, String expirationDate, String securityCode) {
        enterFullName(fullName);
        enterCardNumber(cardNumber);
        enterExpirationDate(expirationDate);
        enterSecurityCode(securityCode);
        logger.info("Filled payment details");
    }

    /**
     * Fills billing address.
     */
    public void fillBillingAddress(String fullName, String address1, String city, String zipCode, String country) {
        enterText(billToFullNameField, fullName);
        enterText(billToAddressLine1Field, address1);
        enterText(billToCityField, city);
        enterText(billToZipCodeField, zipCode);
        enterText(billToCountryField, country);
        logger.info("Filled billing address");
    }

    /**
     * Fills payment details with wrong values.
     */
    public void fillWrongPaymentDetails() {
        enterFullName("A");  // Too short
        enterCardNumber("123");  // Invalid card
        enterExpirationDate("13/25");  // Invalid month
        enterSecurityCode("12");  // Too short
        logger.info("Filled wrong payment details");
    }

    /**
     * Fills payment details with correct values.
     */
    public void fillCorrectPaymentDetails() {
        fullNameField.clear();
        cardNumberField.clear();
        expirationDateField.clear();
        securityCodeField.clear();
        enterFullName("Rebecca Winter");
        enterCardNumber("3258125896581562");
        enterExpirationDate("03/25");
        enterSecurityCode("123");
        logger.info("Filled correct payment details");
    }

    /**
     * Checks if full name error is displayed.
     */
    public boolean isFullNameErrorDisplayed() {
        try {
            return isDisplayed(fullNameError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if card number error is displayed.
     */
    public boolean isCardNumberErrorDisplayed() {
        try {
            return isDisplayed(cardNumberError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if expiration date error is displayed.
     */
    public boolean isExpirationDateErrorDisplayed() {
        try {
            return isDisplayed(expirationDateError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if security code error is displayed.
     */
    public boolean isSecurityCodeErrorDisplayed() {
        try {
            return isDisplayed(securityCodeError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if any validation error is displayed.
     */
    public boolean isAnyValidationErrorDisplayed() {
        try {
            // Check for the generic "Value looks invalid" error message
            WebElement error = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Value looks invalid\")"));
            return error.isDisplayed();
        } catch (Exception e) {
            return isFullNameErrorDisplayed() || isCardNumberErrorDisplayed() || 
                   isExpirationDateErrorDisplayed() || isSecurityCodeErrorDisplayed();
        }
    }

    /**
     * Clears all payment fields.
     */
    public void clearAllFields() {
        fullNameField.clear();
        cardNumberField.clear();
        expirationDateField.clear();
        securityCodeField.clear();
        logger.info("Cleared all payment fields");
    }
}

