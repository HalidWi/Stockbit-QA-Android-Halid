package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Product Detail Page Object representing individual product view.
 */
public class ProductDetailPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/productTV")
    private WebElement productTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/priceTV")
    private WebElement productPrice;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/descTV")
    private WebElement productDescription;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    private WebElement addToCartButton;

    @AndroidFindBy(accessibility = "View cart")
    private WebElement cartBadge;

    @AndroidFindBy(accessibility = "counter minus button")
    private WebElement minusButton;

    @AndroidFindBy(accessibility = "counter plus button")
    private WebElement plusButton;

    @AndroidFindBy(accessibility = "counter amount")
    private WebElement quantityAmount;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[contains(@content-desc, 'color')]")
    private List<WebElement> colorOptions;

    @AndroidFindBy(accessibility = "Black color")
    private WebElement blackColorOption;

    @AndroidFindBy(accessibility = "Blue color")
    private WebElement blueColorOption;

    @AndroidFindBy(accessibility = "Gray color")
    private WebElement grayColorOption;

    @AndroidFindBy(accessibility = "Red color")
    private WebElement redColorOption;

    @AndroidFindBy(accessibility = "review star")
    private WebElement reviewStar;

    @AndroidFindBy(accessibility = "navigate back")
    private WebElement backButton;

    public ProductDetailPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if product detail page is displayed.
     */
    public boolean isProductDetailDisplayed() {
        waitFor(2);
        
        // Try add to cart button first (most reliable indicator)
        try {
            WebElement addBtn = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"));
            if (addBtn.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("Add to cart button not found via ID");
        }
        
        // Fallback: try accessibility id
        try {
            WebElement addBtn = driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart"));
            if (addBtn.isDisplayed()) {
                return true;
            }
        } catch (Exception e) {
            logger.debug("Add to cart button not found via accessibility");
        }
        
        // Fallback: check for product title
        try {
            WebElement title = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
            return title.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets product title.
     */
    public String getProductTitle() {
        try {
            return getText(productTitle);
        } catch (Exception e) {
            try {
                WebElement title = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/productTV"));
                return getText(title);
            } catch (Exception ex) {
                logger.warn("Could not get product title: {}", ex.getMessage());
                return "";
            }
        }
    }

    /**
     * Gets product price.
     */
    public String getProductPrice() {
        try {
            return getText(productPrice);
        } catch (Exception e) {
            try {
                WebElement price = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/priceTV"));
                return getText(price);
            } catch (Exception ex) {
                logger.warn("Could not get product price: {}", ex.getMessage());
                return "";
            }
        }
    }

    /**
     * Gets product description.
     */
    public String getProductDescription() {
        try {
            return getText(productDescription);
        } catch (Exception e) {
            try {
                WebElement desc = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/descTV"));
                return getText(desc);
            } catch (Exception ex) {
                logger.warn("Could not get product description: {}", ex.getMessage());
                return "";
            }
        }
    }

    /**
     * Selects black color.
     */
    public void selectBlackColor() {
        click(blackColorOption);
        logger.info("Selected black color");
    }

    /**
     * Selects blue color.
     */
    public void selectBlueColor() {
        click(blueColorOption);
        logger.info("Selected blue color");
    }

    /**
     * Selects gray color.
     */
    public void selectGrayColor() {
        click(grayColorOption);
        logger.info("Selected gray color");
    }

    /**
     * Selects red color.
     */
    public void selectRedColor() {
        click(redColorOption);
        logger.info("Selected red color");
    }

    /**
     * Selects a color by name.
     */
    public void selectColor(String color) {
        switch (color.toLowerCase()) {
            case "black":
                selectBlackColor();
                break;
            case "blue":
                selectBlueColor();
                break;
            case "gray":
            case "grey":
                selectGrayColor();
                break;
            case "red":
                selectRedColor();
                break;
            default:
                logger.warn("Unknown color: {}", color);
        }
    }

    /**
     * Increases quantity.
     */
    public void increaseQuantity() {
        click(plusButton);
        logger.info("Increased quantity");
    }

    /**
     * Decreases quantity.
     */
    public void decreaseQuantity() {
        click(minusButton);
        logger.info("Decreased quantity");
    }

    /**
     * Sets specific quantity.
     */
    public void setQuantity(int quantity) {
        // Get current quantity and adjust
        int current = getCurrentQuantity();
        while (current < quantity) {
            increaseQuantity();
            current++;
        }
        while (current > quantity && current > 1) {
            decreaseQuantity();
            current--;
        }
        logger.info("Set quantity to: {}", quantity);
    }

    /**
     * Gets current quantity.
     */
    public int getCurrentQuantity() {
        try {
            String text = getText(quantityAmount);
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * Clicks Add to Cart button.
     */
    public void clickAddToCart() {
        boolean clicked = false;
        
        // Try ID locator first
        try {
            WebElement addBtn = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartBt"));
            addBtn.click();
            clicked = true;
            logger.info("Clicked Add to Cart via ID");
        } catch (Exception e) {
            logger.debug("Add to cart button not found via ID");
        }
        
        // Fallback: try accessibility id
        if (!clicked) {
            try {
                WebElement addBtn = driver.findElement(AppiumBy.accessibilityId("Tap to add product to cart"));
                addBtn.click();
                clicked = true;
                logger.info("Clicked Add to Cart via accessibility");
            } catch (Exception e) {
                logger.debug("Add to cart button not found via accessibility");
            }
        }
        
        // Fallback: try text
        if (!clicked) {
            try {
                WebElement addBtn = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().textContains(\"Add To Cart\")"));
                addBtn.click();
                clicked = true;
                logger.info("Clicked Add to Cart via text");
            } catch (Exception e) {
                logger.error("Could not click Add to Cart button: {}", e.getMessage());
                throw new RuntimeException("Add to Cart button not found");
            }
        }
        
        // Wait for cart update
        waitFor(1);
    }

    /**
     * Clicks on cart icon.
     */
    public void clickCartIcon() {
        try {
            click(cartBadge);
        } catch (Exception e) {
            // Fallback
            try {
                WebElement cart = driver.findElement(AppiumBy.accessibilityId("View cart"));
                cart.click();
            } catch (Exception ex) {
                WebElement cart = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartIV"));
                cart.click();
            }
        }
        logger.info("Clicked on cart icon");
    }

    /**
     * Navigates back.
     */
    public void goBack() {
        click(backButton);
        logger.info("Navigated back");
    }

    /**
     * Adds product to cart with color selection.
     */
    public void addProductToCart(String color) {
        selectColor(color);
        clickAddToCart();
        logger.info("Added product with {} color to cart", color);
    }
}

