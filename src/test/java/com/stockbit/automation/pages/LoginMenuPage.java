package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Login Menu Page Object for testing login via menu functionality.
 */
public class LoginMenuPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Login\")")
    private WebElement loginTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordErrorTV")
    private WebElement errorMessage;

    public LoginMenuPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if Login page is displayed.
     */
    public boolean isLoginPageDisplayed() {
        waitFor(2);
        try {
            return isDisplayed(usernameField) || isDisplayed(loginTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enters username.
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
        logger.info("Entered username: {}", username);
    }

    /**
     * Enters password.
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
        logger.info("Entered password");
    }

    /**
     * Clicks the Login button.
     */
    public void clickLogin() {
        click(loginButton);
        logger.info("Clicked login button");
    }

    /**
     * Performs login with credentials.
     */
    public void login(String username, String password) {
        // First try to click on the pre-filled username link if available
        try {
            WebElement userLink = driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + username + "\")"
            ));
            click(userLink);
            waitFor(1);
        } catch (Exception e) {
            // If link not found, enter credentials manually
            enterUsername(username);
            enterPassword(password);
        }
        clickLogin();
        waitFor(2);
        logger.info("Performed login with username: {}", username);
    }

    /**
     * Checks if logged in successfully (login page is no longer visible).
     */
    public boolean isLoggedInSuccessfully() {
        waitFor(2);
        try {
            // If we can't see the login button anymore, login was successful
            return !isDisplayed(loginButton);
        } catch (Exception e) {
            return true; // Element not found means we navigated away
        }
    }

    /**
     * Checks if error message is displayed.
     */
    public boolean isErrorMessageDisplayed() {
        try {
            return isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the error message text.
     */
    public String getErrorMessage() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }
}

