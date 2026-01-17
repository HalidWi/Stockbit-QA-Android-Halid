package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Cart Page Object representing the shopping cart.
 */
public class CartPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"My Cart\")")
    private WebElement cartTitle;

    @AndroidFindBy(xpath = "//androidx.recyclerview.widget.RecyclerView[@content-desc=\"Displays list of selected products\"]/android.view.ViewGroup")
    private List<WebElement> cartItems;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Proceed To Checkout\")")
    private WebElement proceedToCheckoutButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/totalPriceTV\")")
    private WebElement totalPrice;

    @AndroidFindBy(accessibility = "total number")
    private WebElement totalItemsNumber;

    @AndroidFindBy(accessibility = "remove item")
    private WebElement removeItemButton;

    @AndroidFindBy(accessibility = "counter minus button")
    private WebElement minusButton;

    @AndroidFindBy(accessibility = "counter plus button")
    private WebElement plusButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/shoppingBt\")")
    private WebElement goShoppingButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'No Items')]")
    private WebElement noItemsMessage;

    public CartPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Navigates to the cart page by clicking the cart icon.
     */
    public void navigateToCart() {
        try {
            // Click on cart icon in header
            WebElement cartIcon = driver.findElement(AppiumBy.accessibilityId("View cart"));
            click(cartIcon);
            logger.info("Clicked on cart icon");
            waitFor(2);
        } catch (Exception e) {
            logger.warn("Could not click cart icon: {}", e.getMessage());
            // Try alternative locator
            try {
                WebElement cartRL = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartRL"));
                click(cartRL);
                logger.info("Clicked on cart icon (via ID)");
                waitFor(2);
            } catch (Exception ex) {
                logger.error("Failed to navigate to cart: {}", ex.getMessage());
            }
        }
    }

    /**
     * Checks if cart page is displayed.
     */
    public boolean isCartPageDisplayed() {
        try {
            waitFor(3);
            // Try the cart title first
            if (isDisplayed(cartTitle)) {
                return true;
            }
            // Fallback: check for proceed to checkout button
            return isDisplayed(proceedToCheckoutButton);
        } catch (Exception e) {
            logger.warn("Could not verify cart page: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Gets number of items in cart.
     */
    public int getCartItemCount() {
        return cartItems.size();
    }

    /**
     * Checks if cart is empty.
     */
    public boolean isCartEmpty() {
        try {
            return isDisplayed(noItemsMessage) || cartItems.isEmpty();
        } catch (Exception e) {
            return cartItems.isEmpty();
        }
    }

    /**
     * Gets total price.
     */
    public String getTotalPrice() {
        return getText(totalPrice);
    }

    /**
     * Gets total items number.
     */
    public String getTotalItemsNumber() {
        try {
            return getText(totalItemsNumber);
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * Clicks Proceed to Checkout.
     */
    public void clickProceedToCheckout() {
        click(proceedToCheckoutButton);
        logger.info("Clicked Proceed to Checkout");
    }

    /**
     * Removes item from cart by index.
     */
    public void removeItem(int index) {
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            WebElement removeBtn = item.findElement(By.xpath("//android.widget.TextView[@content-desc=\"Removes product from cart\"]"));
            click(removeBtn);
            logger.info("Removed item at index: {}", index);
        }
    }

    /**
     * Removes all items from cart.
     */
    public void removeAllItems() {
        while (!cartItems.isEmpty()) {
            removeItem(0);
            waitFor(1);
        }
        logger.info("Removed all items from cart");
    }

    /**
     * Increases quantity for item at index.
     */
    public void increaseQuantity(int index) {
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            WebElement plusBtn = item.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Increase item quantity\"]"));
            click(plusBtn);
            logger.info("Increased quantity for item at index: {}", index);
        }
    }

    /**
     * Decreases quantity for item at index.
     */
    public void decreaseQuantity(int index) {
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            WebElement minusBtn = item.findElement(By.xpath(".//android.view.ViewGroup[@content-desc='counter minus button']"));
            click(minusBtn);
            logger.info("Decreased quantity for item at index: {}", index);
        }
    }

    /**
     * Gets product name at index.
     * Uses multiple strategies to find the product name.
     */
    public String getProductName(int index) {
        waitFor(1);
        
        // Strategy 1: Use the titleTV resource-id
        try {
            List<WebElement> titleElements = driver.findElements(
                io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/titleTV"));
            if (index < titleElements.size()) {
                String name = titleElements.get(index).getText();
                if (name != null && !name.isEmpty()) {
                    logger.info("Found product name via titleTV: {}", name);
                    return name;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find product name via titleTV: {}", e.getMessage());
        }
        
        // Strategy 2: Use the cart item view group
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            try {
                // Look for TextView with product name pattern
                List<WebElement> textViews = item.findElements(By.xpath(".//android.widget.TextView"));
                for (WebElement tv : textViews) {
                    String text = tv.getText();
                    // Product names usually contain "Sauce" or "Lab"
                    if (text != null && (text.contains("Sauce") || text.contains("Lab") || text.contains("T-Shirt"))) {
                        logger.info("Found product name via cart item: {}", text);
                        return text;
                    }
                }
                // If no "Sauce" keyword found, return first non-price text
                for (WebElement tv : textViews) {
                    String text = tv.getText();
                    if (text != null && !text.isEmpty() && !text.startsWith("$") && !text.matches("^\\d+$")) {
                        logger.info("Found product name (fallback): {}", text);
                        return text;
                    }
                }
            } catch (Exception e) {
                logger.warn("Could not find product name at index: {}", index);
            }
        }
        return "";
    }

    /**
     * Gets product color at index.
     * Uses multiple strategies to find the product color.
     */
    public String getProductColor(int index) {
        waitFor(1);
        
        // Strategy 1: Look for color text element
        try {
            List<WebElement> colorElements = driver.findElements(
                io.appium.java_client.AppiumBy.id("com.saucelabs.mydemoapp.android:id/colorTV"));
            if (index < colorElements.size()) {
                String color = colorElements.get(index).getText();
                if (color != null && !color.isEmpty()) {
                    logger.info("Found product color via colorTV: {}", color);
                    return color;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find product color via colorTV: {}", e.getMessage());
        }
        
        // Strategy 2: Look for color in accessibility descriptions
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            try {
                // Look for ImageView with color content-desc
                List<WebElement> images = item.findElements(By.xpath(".//android.widget.ImageView[@content-desc]"));
                for (WebElement img : images) {
                    String contentDesc = img.getAttribute("content-desc");
                    if (contentDesc != null && !contentDesc.isEmpty() && 
                        !contentDesc.contains("product") && !contentDesc.contains("item")) {
                        logger.info("Found product color via content-desc: {}", contentDesc);
                        return contentDesc;
                    }
                }
            } catch (Exception e) {
                logger.debug("Could not find color via content-desc: {}", e.getMessage());
            }
        }
        
        // Strategy 3: Look for color text in any TextView within cart item
        if (index < cartItems.size()) {
            WebElement item = cartItems.get(index);
            try {
                List<WebElement> textViews = item.findElements(By.xpath(".//android.widget.TextView"));
                String[] colorNames = {"Black", "Blue", "Gray", "Red", "Green", "White", "Yellow", "Orange", "Purple", "Pink"};
                for (WebElement tv : textViews) {
                    String text = tv.getText();
                    if (text != null) {
                        for (String colorName : colorNames) {
                            if (text.toLowerCase().contains(colorName.toLowerCase())) {
                                logger.info("Found product color via text: {}", text);
                                return text;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.warn("Could not find product color at index: {}", index);
            }
        }
        return "";
    }

    /**
     * Checks if specific product exists in cart.
     */
    public boolean isProductInCart(String productName) {
        for (WebElement item : cartItems) {
            try {
                WebElement nameElement = item.findElement(By.xpath(".//android.widget.TextView"));
                if (getText(nameElement).contains(productName)) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }

    /**
     * Clicks Go Shopping button (when cart is empty).
     */
    public void clickGoShopping() {
        click(goShoppingButton);
        logger.info("Clicked Go Shopping");
    }

    public boolean isGoShoppingButtonDisplayed(){
        waitFor(1);
        return goShoppingButton.isDisplayed();
    }

    /**
     * Verifies item exists and proceeds to checkout.
     */
    public void verifyItemAndCheckout() {
        if (!isCartEmpty()) {
            logger.info("Cart has {} items", getCartItemCount());
            clickProceedToCheckout();
        } else {
            throw new RuntimeException("Cart is empty, cannot proceed to checkout");
        }
    }
}

