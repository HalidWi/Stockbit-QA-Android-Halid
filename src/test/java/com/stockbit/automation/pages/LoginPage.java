package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Login Page Object representing the login screen of the app.
 * Update locators based on actual app elements.
 */
public class LoginPage extends BasePage {

    // Example locators - update based on actual app inspection
    @AndroidFindBy(id = "com.app.mda:id/username_field")
    private WebElement usernameField;

    @AndroidFindBy(id = "com.app.mda:id/password_field")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.app.mda:id/login_button")
    private WebElement loginButton;

    @AndroidFindBy(id = "com.app.mda:id/register_link")
    private WebElement registerLink;

    @AndroidFindBy(id = "com.app.mda:id/forgot_password_link")
    private WebElement forgotPasswordLink;

    @AndroidFindBy(id = "com.app.mda:id/error_message")
    private WebElement errorMessage;

    public LoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Enters username into the username field.
     *
     * @param username the username to enter
     */
    public void enterUsername(String username) {
        enterText(usernameField, username);
        logger.info("Entered username: {}", username);
    }

    /**
     * Enters password into the password field.
     *
     * @param password the password to enter
     */
    public void enterPassword(String password) {
        enterText(passwordField, password);
        logger.info("Entered password");
    }

    /**
     * Clicks the login button.
     */
    public void clickLoginButton() {
        click(loginButton);
        logger.info("Clicked login button");
    }

    /**
     * Performs login with given credentials.
     *
     * @param username the username
     * @param password the password
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        logger.info("Performed login action");
    }

    /**
     * Clicks on register link.
     */
    public void clickRegisterLink() {
        click(registerLink);
        logger.info("Clicked register link");
    }

    /**
     * Clicks on forgot password link.
     */
    public void clickForgotPasswordLink() {
        click(forgotPasswordLink);
        logger.info("Clicked forgot password link");
    }

    /**
     * Gets error message text.
     *
     * @return error message string
     */
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    /**
     * Checks if error message is displayed.
     *
     * @return true if error message is visible
     */
    public boolean isErrorMessageDisplayed() {
        return isDisplayed(errorMessage);
    }

    /**
     * Checks if login page is displayed.
     *
     * @return true if login elements are visible
     */
    public boolean isLoginPageDisplayed() {
        try {
            return isDisplayed(loginButton);
        } catch (Exception e) {
            return false;
        }
    }
}

