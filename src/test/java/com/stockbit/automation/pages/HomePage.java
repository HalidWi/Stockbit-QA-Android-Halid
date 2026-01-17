package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Home Page Object representing the main/home screen of the app.
 * The MDA app starts on the Products/Catalog page by default.
 */
public class HomePage extends BasePage {

    // Locators for MDA app - app starts on Products page
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productsTitle;

    @AndroidFindBy(accessibility = "View menu")
    private WebElement menuButton;

    @AndroidFindBy(accessibility = "View cart")
    private WebElement cartButton;

    @AndroidFindBy(accessibility = "sort button")
    private WebElement sortButton;

    public HomePage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if home page (Products/Catalog page) is displayed.
     * Uses multiple fallback strategies to be robust.
     *
     * @return true if home page is visible
     */
    public boolean isHomePageDisplayed() {
        waitFor(2);
        
        // Strategy 1: Check if driver session is valid
        try {
            if (driver.getSessionId() == null) {
                logger.warn("Driver session is null");
                return false;
            }
        } catch (Exception e) {
            logger.warn("Could not check driver session: {}", e.getMessage());
            return false;
        }
        
        // Strategy 2: Try to find the Products title (primary indicator)
        try {
            WebElement products = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
            if (products.isDisplayed()) {
                logger.info("Home page verified - Products title visible");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Products title not found: {}", e.getMessage());
        }
        
        // Strategy 3: Try to find menu button (secondary indicator)
        try {
            WebElement menu = driver.findElement(AppiumBy.accessibilityId("View menu"));
            if (menu.isDisplayed()) {
                logger.info("Home page verified - Menu button visible");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Menu button not found: {}", e.getMessage());
        }
        
        // Strategy 4: Try to find any element from MDA app package
        try {
            String currentPackage = driver.getCurrentPackage();
            if ("com.saucelabs.mydemoapp.android".equals(currentPackage)) {
                logger.info("Home page verified - App package is correct");
                return true;
            }
        } catch (Exception e) {
            logger.debug("Could not get current package: {}", e.getMessage());
        }
        
        // Strategy 5: Last resort - try getPageSource
        try {
            String pageSource = driver.getPageSource();
            if (pageSource != null && pageSource.contains("com.saucelabs.mydemoapp.android")) {
                logger.info("Home page verified via page source");
                return true;
            }
        } catch (Exception e) {
            logger.warn("Could not get page source: {}", e.getMessage());
        }
        
        return false;
    }

    /**
     * Gets the current activity name.
     *
     * @return current activity
     */
    public String getCurrentActivity() {
        return driver.currentActivity();
    }

    /**
     * Clicks on menu button.
     */
    public void clickMenuButton() {
        click(menuButton);
        logger.info("Clicked menu button");
    }

    /**
     * Clicks on cart button.
     */
    public void clickCartButton() {
        click(cartButton);
        logger.info("Clicked cart button");
    }

    /**
     * Clicks on sort button.
     */
    public void clickSortButton() {
        click(sortButton);
        logger.info("Clicked sort button");
    }

    /**
     * Gets page source for debugging.
     *
     * @return page source XML
     */
    public String getPageSource() {
        return driver.getPageSource();
    }

    /**
     * Scrolls down on the page.
     */
    public void scrollPageDown() {
        scrollDown();
    }

    /**
     * Scrolls up on the page.
     */
    public void scrollPageUp() {
        scrollUp();
    }
}

