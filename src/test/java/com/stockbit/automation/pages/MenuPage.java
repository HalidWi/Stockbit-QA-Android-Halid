package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Menu Page Object representing the burger menu navigation.
 */
public class MenuPage extends BasePage {

    @AndroidFindBy(accessibility = "View menu")
    private WebElement burgerMenuButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(17)")
    private WebElement qrCodeScannerMenuItem;

    public MenuPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Opens the burger menu.
     */
    public void openMenu() {
        click(burgerMenuButton);
        logger.info("Opened burger menu");
        waitFor(1);
    }

    /**
     * Navigates to Catalog page.
     */
    public void navigateToCatalog() {

        navigateTo("Catalog");
        logger.info("Navigated to Catalog");
    }

    /**
     * Navigates to Cart page.
     */
    public void navigateToCart() {
        navigateTo("Catalog");
        logger.info("Navigated to Cart");
    }

    /**
     * Navigates to Login page.
     */
    public void navigateToLogin() {
        navigateTo("Log In");
        logger.info("Navigated to Log In");
    }

    /**
     * Navigates to Webview page.
     */
    public void navigateToWebview() {
        navigateTo("WebView");
        logger.info("Navigated to Webview");
    }

    /**
     * Navigates to QR Code Scanner page.
     */
    public void navigateToQRCodeScanner() {
        openMenu();
        click(qrCodeScannerMenuItem);
        logger.info("Navigated to QR Code Scanner");
    }

    /**
     * Navigates to Geo Location page.
     */
    public void navigateToGeoLocation() {
        navigateTo("Geo Location");
        logger.info("Navigated to Geo Location");
    }

    /**
     * Navigates to Drawing page.
     */
    public void navigateToDrawing() {
        navigateTo("Drawing");
        logger.info("Navigated to Drawing");
    }

    /**
     * Navigates to About page.
     */
    public void navigateToAbout() {
        navigateTo("About");
        logger.info("Navigated to About");
    }

    /**
     * Resets the app state.
     */
    public void resetApp() {
        navigateTo("Reset App State");
        logger.info("Reset App clicked");
    }

    /**
     * Navigates to Biometrics page.
     */
    public void navigateToBiometrics() {
        navigateTo("FingerPrint");
        logger.info("Navigated to FingerPrint");
    }

    /**
     * Navigates to Report a Bug page.
     */
    public void navigateToReportBug() {
        navigateTo("Report a Bug");
        logger.info("Navigated to Report a Bug");
    }

    /**
     * Navigates to Report a Bug (debug) page.
     */
    public void navigateToReportBugDebug() {
        navigateTo("Report a Bug (debug)");
        logger.info("Navigated to Report a Bug (debug)");
    }

    /**
     * Navigates to Crash app (debug) page.
     */
    public void navigateToCrashAppDebug() {
        navigateTo("Crash app (debug)");
        logger.info("Navigated to Crash app (debug)");
    }

    /**
     * Checks if burger menu button is displayed.
     */
    public boolean isMenuButtonDisplayed() {
        return isDisplayed(burgerMenuButton);
    }

    private WebElement menuItemByText(String text) {
        waitFor(2); // Wait for menu animation to complete
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"" + text + "\")"
        ));
    }

    public void navigateTo(String menuName) {
        openMenu();
        click(menuItemByText(menuName));
        logger.info("Navigated to {}", menuName);
    }
}

