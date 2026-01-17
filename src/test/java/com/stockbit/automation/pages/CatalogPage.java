package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Catalog Page Object representing the product catalog/listing page.
 */
public class CatalogPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\")")
    private WebElement productsTitle;

    @AndroidFindBy(accessibility = "View cart")
    private WebElement cartBadge;

    @AndroidFindBy(accessibility = "sort button")
    private WebElement sortButton;

    @AndroidFindBy(accessibility = "toggle button")
    private WebElement toggleViewButton;

    public CatalogPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if catalog page is displayed.
     */
    public boolean isCatalogPageDisplayed() {
        try {
            waitFor(2);
            return isDisplayed(productsTitle);
        } catch (Exception e) {
            logger.warn("Could not verify catalog page: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets product items list.
     */
    private List<WebElement> getProductItems() {
        return driver.findElements(By.id("com.saucelabs.mydemoapp.android:id/productIV"));
    }

    /**
     * Gets the number of products displayed.
     */
    public int getProductCount() {
        return getProductItems().size();
    }

    /**
     * Scrolls down to view more products.
     */
    public void scrollToViewMoreProducts() {
        scrollDown();
        logger.info("Scrolled down to view more products");
    }

    /**
     * Selects a product by index (0-based).
     */
    public void selectProduct(int index) {
        getProductItems().get(index).click();
        logger.info("Selected product at index: {}", index);
    }

    /**
     * Return the selected product name
     */
    public String returnSelectedProductName(int index) {
        try {
            // Find all product titles directly
            List<WebElement> titleElements = driver.findElements(
                io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV"));
            if (index < titleElements.size()) {
                String title = titleElements.get(index).getText();
                logger.info("Found product title at index {}: {}", index, title);
                return title;
            }
        } catch (Exception e) {
            logger.warn("Could not find title for product at index {}: {}", index, e.getMessage());
        }
        return "";
    }

    /**
     * Selects the first product.
     */
    public void selectFirstProduct() {
        selectProduct(0);
    }

    /**
     * Selects a product at the specified index.
     * Alias for selectProduct for better readability.
     */
    public void selectProductAtIndex(int index) {
        selectProduct(index);
    }

    /**
     * Return the first product title.
     */
    public String returnFirstSelectedProductName() {
        return returnSelectedProductName(0);
    }

    /**
     * Selects a product by name.
     */
    public void selectProductByName(String productName) {
        WebElement product = findElement(By.xpath(
                "//android.widget.TextView[@text='" + productName + "']/ancestor::android.view.ViewGroup[@content-desc='store item']"));
        click(product);
        logger.info("Selected product: {}", productName);
    }

    /**
     * Clicks on cart icon.
     */
    public void clickCartIcon() {
        click(cartBadge);
        logger.info("Clicked on cart icon");
    }

    /**
     * Gets cart item count from badge.
     */
    public String getCartBadgeCount() {
        waitFor(1); // Wait for badge to update
        
        // Try multiple locators for cart badge count
        try {
            WebElement badge = driver.findElement(io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV"));
            String count = getText(badge);
            logger.info("Cart badge count (via ID): {}", count);
            return count != null && !count.isEmpty() ? count : "0";
        } catch (Exception e) {
            logger.debug("Cart badge not found via ID");
        }
        
        // Fallback: try xpath with content-desc
        try {
            WebElement badge = findElement(By.xpath("//android.view.ViewGroup[@content-desc='cart badge']/android.widget.TextView"));
            String count = getText(badge);
            logger.info("Cart badge count (via xpath): {}", count);
            return count != null && !count.isEmpty() ? count : "0";
        } catch (Exception e) {
            logger.debug("Cart badge not found via xpath");
        }
        
        // Fallback: try accessibility id
        try {
            WebElement badge = driver.findElement(io.appium.java_client.AppiumBy.accessibilityId("cart badge"));
            WebElement countElement = badge.findElement(By.className("android.widget.TextView"));
            String count = getText(countElement);
            logger.info("Cart badge count (via accessibility): {}", count);
            return count != null && !count.isEmpty() ? count : "0";
        } catch (Exception e) {
            logger.debug("Cart badge not found via accessibility");
        }
        
        return "0";
    }

    /**
     * Clicks sort button.
     */
    public void clickSort() {
        click(sortButton);
        logger.info("Clicked sort button");
    }

    /**
     * Toggles between grid and list view.
     */
    public void toggleView() {
        click(toggleViewButton);
        logger.info("Toggled product view");
    }
}

