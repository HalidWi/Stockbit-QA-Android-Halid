package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * WebView Page Object for testing webview functionality.
 */
public class WebViewPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/urlET")
    private WebElement urlInput;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/goBtn")
    private WebElement goToSiteButton;

    @AndroidFindBy(className = "android.webkit.WebView")
    private WebElement webViewElement;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"WebView\")")
    private WebElement webViewTitle;

    public WebViewPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if WebView page is displayed.
     */
    public boolean isWebViewPageDisplayed() {
        waitFor(2);
        try {
            return isDisplayed(urlInput) || isDisplayed(webViewTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Enters URL into the input field.
     */
    public void enterUrl(String url) {
        enterText(urlInput, url);
        logger.info("Entered URL: {}", url);
    }

    /**
     * Clicks the Go To Site button.
     */
    public void clickGoToSite() {
        click(goToSiteButton);
        logger.info("Clicked 'Go To Site' button");
    }

    /**
     * Checks if WebView is loaded.
     */
    public boolean isWebViewLoaded() {
        try {
            waitFor(5);
            return isDisplayed(webViewElement);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Switches to WebView context if available.
     */
    public void switchToWebViewContext() {
        try {
            var contexts = driver.getContextHandles();
            for (String context : contexts) {
                if (context.contains("WEBVIEW")) {
                    driver.context(context);
                    logger.info("Switched to WEBVIEW context: {}", context);
                    return;
                }
            }
            logger.warn("WEBVIEW context not found. Current contexts: {}", contexts);
        } catch (Exception e) {
            logger.error("Failed to switch to WEBVIEW context: {}", e.getMessage());
        }
    }

    /**
     * Switches back to native app context.
     */
    public void switchToNativeAppContext() {
        driver.context("NATIVE_APP");
        logger.info("Switched back to NATIVE_APP context");
    }

    /**
     * Gets the current URL in the WebView.
     */
    public String getCurrentUrlInWebView() {
        String currentUrl = "";
        String originalContext = driver.getContext();
        try {
            switchToWebViewContext();
            currentUrl = driver.getCurrentUrl();
        } catch (Exception e) {
            logger.error("Failed to get current URL in WebView: {}", e.getMessage());
        } finally {
            driver.context(originalContext);
        }
        return currentUrl;
    }
}

