package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Checkout Login Page Object for login during checkout flow.
 */
public class CheckoutLoginPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(0)")
    private WebElement usernameField;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.widget.EditText\").instance(1)")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/loginBtn")
    private WebElement loginButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Provided credentials do not match')]")
    private WebElement invalidCredentialsError;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordErrorTV")
    private WebElement lockedUserError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Username is required')]")
    private WebElement usernameRequiredError;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Password is required')]")
    private WebElement passwordRequiredError;

    @AndroidFindBy(accessibility = "Login")
    private WebElement loginTitle;

    public CheckoutLoginPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if login page is displayed.
     */
    public boolean isLoginPageDisplayed() {
        try {
            waitFor(3);
            // Try login button first
            if (isDisplayed(loginButton)) {
                return true;
            }
            // Fallback: check for username field
            return isDisplayed(usernameField);
        } catch (Exception e) {
            logger.warn("Could not verify login page: {}", e.getMessage());
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
     * Clicks login button.
     */
    public void clickLogin() {
        click(loginButton);
        logger.info("Clicked login button");
    }

    /**
     * Performs login by clicking the username link to auto-fill credentials.
     */
    public void login(String username, String password) {
        try {
            // Try clicking the username link to auto-fill (recommended by app)
            WebElement usernameLink = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"" + username + "\")"));
            usernameLink.click();
            logger.info("Clicked username link to auto-fill: {}", username);
            waitFor(1);
        } catch (Exception e) {
            // Fallback: manually enter credentials
            logger.info("Username link not found, entering credentials manually");
            enterUsername(username);
            enterPassword(password);
        }
        clickLogin();
        waitFor(2); // Wait for page transition
        logger.info("Performed login with username: {}", username);
    }

    /**
     * Checks if locked user error is displayed.
     */
    public boolean isLockedUserErrorDisplayed() {
        // Hide keyboard first
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {}
        
        waitFor(2);
        
        // Check if error is already visible
        try {
            WebElement errorElement = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textStartsWith(\"Sorry\")"));
            if (errorElement.isDisplayed()) {
                return true;
            }
        } catch (Exception ignored) {}
        
        // Error not found, re-enter credentials and click login again
        logger.info("Error not found, re-entering credentials and clicking login again...");
        try {
            usernameField.clear();
            usernameField.sendKeys("alice@example.com");
            passwordField.clear();
            passwordField.sendKeys("10203040");
            waitFor(1);
            click(loginButton);
            waitFor(2);
            
            WebElement errorElement = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textStartsWith(\"Sorry\")"));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            logger.warn("Could not verify locked user error after retry: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Checks if invalid credentials error is displayed.
     */
    public boolean isInvalidCredentialsErrorDisplayed() {
        try {
            return isDisplayed(invalidCredentialsError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if username required error is displayed.
     */
    public boolean isUsernameRequiredErrorDisplayed() {
        try {
            return isDisplayed(usernameRequiredError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if password required error is displayed.
     */
    public boolean isPasswordRequiredErrorDisplayed() {
        try {
            return isDisplayed(passwordRequiredError);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets locked user error message text.
     */
    public String getLockedUserErrorMessage() {
        try {
            WebElement errorElement = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textStartsWith(\"Sorry\")"));
            return errorElement.getText();
        } catch (Exception e) {
            logger.warn("Could not get error message: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Clears all fields.
     */
    public void clearFields() {
        usernameField.clear();
        passwordField.clear();
        logger.info("Cleared all login fields");
    }

    /**
     * Logs in with locked user credentials.
     */
    public void loginWithLockedUser() {
        login("alice@example.com", "10203040");
    }

    /**
     * Logs in with valid user credentials.
     */
    public void loginWithValidUser() {
        login("bod@example.com", "10203040");
    }
}

