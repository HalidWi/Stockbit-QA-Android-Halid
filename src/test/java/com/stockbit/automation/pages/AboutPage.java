package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * About Page Object for testing about page functionality.
 */
public class AboutPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/aboutTV")
    private WebElement aboutTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/webTV")
    private WebElement sauceLabsWebsiteLink;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Sauce Labs\")")
    private WebElement websiteLinkText;

    public AboutPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if About page is displayed.
     */
    public boolean isAboutPageDisplayed() {
        waitFor(2);
        try {
            return isDisplayed(aboutTitle);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks on Sauce Labs Website link.
     */
    public void clickSauceLabsWebsiteLink() {
        try {
            // Try clicking the link by text
            click(sauceLabsWebsiteLink);
        } catch (Exception e) {
            // Fallback to link text element
            try {
                click(websiteLinkText);
            } catch (Exception ex) {
                // Try finding by partial text
                WebElement link = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"Sauce Labs\")"
                ));
                click(link);
            }
        }
        logger.info("Clicked 'Sauce Labs Website' link");
    }

    /**
     * Checks if Sauce Labs link is displayed.
     */
    public boolean isSauceLabsLinkDisplayed() {
        try {
            return isDisplayed(sauceLabsWebsiteLink) || isDisplayed(websiteLinkText);
        } catch (Exception e) {
            return false;
        }
    }
}

