package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Shipping Address Page Object for checkout flow.
 */
public class ShippingAddressPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/checkoutTitleTV")
    private WebElement checkoutTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameET")
    private WebElement fullNameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ET")
    private WebElement addressLine1Field;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address2ET")
    private WebElement addressLine2Field;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityET")
    private WebElement cityField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/stateET")
    private WebElement stateRegionField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipET")
    private WebElement zipCodeField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryET")
    private WebElement countryField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/paymentBtn")
    private WebElement toPaymentButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/fullNameErrorTV")
    private WebElement fullNameError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/address1ErrorTV")
    private WebElement addressError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cityErrorTV")
    private WebElement cityError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/zipErrorTV")
    private WebElement zipCodeError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/countryErrorTV")
    private WebElement countryError;

    public ShippingAddressPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if shipping address page is displayed.
     */
    public boolean isShippingAddressPageDisplayed() {
        try {
            waitFor(3); // Wait longer for page transition
            // Check for "Enter a shipping address" text to confirm we're on shipping page
            WebElement shippingTitle = driver.findElement(
                io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/enterShippingAddressTV"));
            return shippingTitle.isDisplayed();
        } catch (Exception e) {
            // Fallback: check for To Payment button
            try {
                return isDisplayed(toPaymentButton);
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
     * Enters address line 1.
     */
    public void enterAddressLine1(String address) {
        enterText(addressLine1Field, address);
        logger.info("Entered address line 1: {}", address);
    }

    /**
     * Enters address line 2.
     */
    public void enterAddressLine2(String address) {
        enterText(addressLine2Field, address);
        logger.info("Entered address line 2: {}", address);
    }

    /**
     * Enters city.
     */
    public void enterCity(String city) {
        enterText(cityField, city);
        logger.info("Entered city: {}", city);
    }

    /**
     * Enters state/region.
     */
    public void enterStateRegion(String stateRegion) {
        enterText(stateRegionField, stateRegion);
        logger.info("Entered state/region: {}", stateRegion);
    }

    /**
     * Enters zip code.
     */
    public void enterZipCode(String zipCode) {
        enterText(zipCodeField, zipCode);
        logger.info("Entered zip code: {}", zipCode);
    }

    /**
     * Enters country.
     */
    public void enterCountry(String country) {
        enterText(countryField, country);
        logger.info("Entered country: {}", country);
    }

    /**
     * Clicks To Payment button.
     */
    public void clickToPayment() {
        scrollDown();
        click(toPaymentButton);
        logger.info("Clicked To Payment button");
    }

    /**
     * Fills all mandatory shipping address fields.
     */
    public void fillMandatoryFields(String fullName, String address, String city, String zipCode, String country) {
        enterFullName(fullName);
        enterAddressLine1(address);
        enterCity(city);
        enterZipCode(zipCode);
        enterCountry(country);
        logger.info("Filled all mandatory shipping address fields");
    }

    /**
     * Fills complete shipping address.
     */
    public void fillCompleteAddress(String fullName, String address1, String address2, 
                                    String city, String state, String zipCode, String country) {
        enterFullName(fullName);
        enterAddressLine1(address1);
        enterAddressLine2(address2);
        enterCity(city);
        enterStateRegion(state);
        enterZipCode(zipCode);
        enterCountry(country);
        logger.info("Filled complete shipping address");
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
     * Checks if address error is displayed.
     */
    public boolean isAddressErrorDisplayed() {
        try {
            return isDisplayed(addressError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if city error is displayed.
     */
    public boolean isCityErrorDisplayed() {
        try {
            return isDisplayed(cityError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if zip code error is displayed.
     */
    public boolean isZipCodeErrorDisplayed() {
        try {
            return isDisplayed(zipCodeError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if country error is displayed.
     */
    public boolean isCountryErrorDisplayed() {
        try {
            return isDisplayed(countryError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if any validation error is displayed.
     */
    public boolean isAnyValidationErrorDisplayed() {
        return isFullNameErrorDisplayed() || isAddressErrorDisplayed() || 
               isCityErrorDisplayed() || isZipCodeErrorDisplayed() || isCountryErrorDisplayed();
    }
}

