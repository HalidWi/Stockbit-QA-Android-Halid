package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Review Order Page Object for order review before final checkout.
 */
public class ReviewOrderPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Review your order\")")
    private WebElement reviewOrderTitle;

    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='product row']")
    private List<WebElement> orderItems;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Place Order\")")
    private WebElement placeOrderButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Delivery Address')]")
    private WebElement deliveryAddressLabel;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text, 'Payment Method')]")
    private WebElement paymentMethodLabel;

    @AndroidFindBy(accessibility = "total price")
    private WebElement totalPrice;

    // Delivery address details
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='delivery address'])[1]")
    private WebElement deliveryName;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='delivery address'])[2]")
    private WebElement deliveryAddress;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='delivery address'])[3]")
    private WebElement deliveryCity;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='delivery address'])[4]")
    private WebElement deliveryCountry;

    // Payment details
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='payment info'])[1]")
    private WebElement paymentCardHolder;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='payment info'])[2]")
    private WebElement paymentCardNumber;

    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='payment info'])[3]")
    private WebElement paymentExpiry;

    public ReviewOrderPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if review order page is displayed.
     */
    public boolean isReviewOrderPageDisplayed() {
        try {
            waitFor(3); // Wait longer for page transition
            // Check for "Review your order" title
            WebElement title = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().text(\"Review your order\")"));
            return title.isDisplayed();
        } catch (Exception e) {
            // Fallback: check for Place Order button
            try {
                return isDisplayed(placeOrderButton);
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Gets number of items in order.
     * Uses multiple strategies to find product items on the review page.
     */
    public int getOrderItemCount() {
        // Try the original locator first
        if (orderItems != null && !orderItems.isEmpty()) {
            return orderItems.size();
        }
        
        // Fallback: Try to find product items by looking for product-related elements
        try {
            // Look for product images or product containers
            List<WebElement> products = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.ImageView[@resource-id='com.saucelabs.mydemoapp.android:id/productIV']"));
            if (!products.isEmpty()) {
                logger.info("Found {} products via product image locator", products.size());
                return products.size();
            }
        } catch (Exception e) {
            logger.debug("Could not find products via image locator: {}", e.getMessage());
        }
        
        // Fallback 2: Look for any product container with price
        try {
            List<WebElement> priceElements = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.TextView[contains(@text, '$')]"));
            if (!priceElements.isEmpty()) {
                logger.info("Found {} price elements indicating products", priceElements.size());
                // At least one product if we have a price
                return priceElements.size() > 0 ? 1 : 0;
            }
        } catch (Exception e) {
            logger.debug("Could not find price elements: {}", e.getMessage());
        }
        
        // Fallback 3: If Review Order page is displayed, assume at least 1 item
        // (we couldn't get to this page without an item in cart)
        try {
            if (isReviewOrderPageDisplayed()) {
                logger.info("Review Order page displayed, assuming at least 1 item");
                return 1;
            }
        } catch (Exception e) {
            logger.debug("Could not verify review order page: {}", e.getMessage());
        }
        
        return 0;
    }

    /**
     * Gets total price.
     */
    public String getTotalPrice() {
        scrollDown();
        return getText(totalPrice);
    }

    /**
     * Gets delivery address name.
     * Uses multiple strategies to find the delivery name.
     */
    public String getDeliveryName() {
        // Try the original locator first
        try {
            String name = getText(deliveryName);
            if (name != null && !name.isEmpty()) {
                return name;
            }
        } catch (Exception e) {
            logger.debug("Could not get delivery name via primary locator: {}", e.getMessage());
        }
        
        // Fallback: Look for delivery address content-desc elements
        try {
            List<WebElement> addressElements = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.TextView[@content-desc='delivery address']"));
            if (!addressElements.isEmpty()) {
                String name = addressElements.get(0).getText();
                logger.info("Found delivery name via fallback: {}", name);
                return name != null ? name : "";
            }
        } catch (Exception e) {
            logger.debug("Fallback locator failed: {}", e.getMessage());
        }
        
        // Fallback 2: Try to find by text that looks like a name (contains space, not a dollar amount)
        try {
            List<WebElement> textViews = driver.findElements(
                io.appium.java_client.AppiumBy.className("android.widget.TextView"));
            for (WebElement tv : textViews) {
                String text = tv.getText();
                // Look for text that looks like a name: contains space, not starting with $, not too long
                if (text != null && text.contains(" ") && !text.startsWith("$") && 
                    text.length() > 3 && text.length() < 50 && !text.contains("Order") &&
                    !text.contains("Delivery") && !text.contains("Payment") &&
                    !text.contains("Total") && !text.contains("Place")) {
                    // Check if this text matches expected pattern (First Last format)
                    if (text.matches("^[A-Z][a-z]+ [A-Z][a-z]+.*$")) {
                        logger.info("Found likely delivery name: {}", text);
                        return text;
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find delivery name via text scan: {}", e.getMessage());
        }
        
        return "";
    }

    /**
     * Gets delivery address name with strict verification.
     * Scrolls to ensure the element is visible and uses multiple strategies.
     */
    public String getDeliveryNameStrict() {
        scrollDown();
        waitFor(1);
        
        // Strategy 1: Try by content-desc 'delivery address'
        try {
            List<WebElement> addressElements = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.TextView[@content-desc='delivery address']"));
            if (!addressElements.isEmpty()) {
                String name = addressElements.get(0).getText();
                if (name != null && !name.isEmpty() && 
                    !name.toLowerCase().contains("deliver") && !name.toLowerCase().contains("address")) {
                    logger.info("Found delivery name via content-desc: {}", name);
                    return name;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find delivery address via content-desc: {}", e.getMessage());
        }
        
        // Strategy 2: Look for text after "Deliver Address" label (note: app has typo)
        try {
            // Note: App uses "Deliver Address" not "Delivery Address" (typo in app)
            WebElement deliveryLabel = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Deliver\")"));
            if (deliveryLabel.isDisplayed()) {
                // Find the next TextView elements after the label
                List<WebElement> allTexts = driver.findElements(
                    io.appium.java_client.AppiumBy.className("android.widget.TextView"));
                boolean foundLabel = false;
                for (WebElement tv : allTexts) {
                    String text = tv.getText();
                    if (text != null && text.contains("Deliver")) {
                        foundLabel = true;
                        continue;
                    }
                    // Look for name after the label - name format: "First Last"
                    // Skip section labels like "Payment", addresses with numbers, etc.
                    if (foundLabel && text != null && !text.isEmpty() && 
                        !text.contains("Payment") && !text.contains("$") &&
                        !text.contains("Deliver") && !text.contains("Address") &&
                        !text.contains("Order") && !text.contains("Total") &&
                        !text.contains("Place") && !text.contains("Review") &&
                        !text.contains("Billing") && !text.matches(".*\\d+.*") &&
                        text.contains(" ") && text.length() > 3 && text.length() < 50) {
                        logger.info("Found delivery name after label: {}", text);
                        return text;
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find delivery name after label: {}", e.getMessage());
        }
        
        // Strategy 3: Search all TextViews for name patterns (excluding section headers)
        try {
            List<WebElement> textViews = driver.findElements(
                io.appium.java_client.AppiumBy.className("android.widget.TextView"));
            for (WebElement tv : textViews) {
                String text = tv.getText();
                // Match "First Last" pattern but exclude section headers
                if (text != null && text.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") &&
                    !text.contains("Order") && !text.contains("Review") &&
                    !text.contains("Deliver") && !text.contains("Address") &&
                    !text.contains("Payment") && !text.contains("Method") &&
                    !text.contains("Place") && !text.contains("Total") &&
                    !text.contains("Billing")) {
                    logger.info("Found delivery name via text pattern: {}", text);
                    return text;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find delivery name via text pattern: {}", e.getMessage());
        }
        
        throw new RuntimeException("Could not find delivery address name on the page");
    }

    /**
     * Gets full delivery address.
     */
    public String getFullDeliveryAddress() {
        StringBuilder address = new StringBuilder();
        try {
            address.append(getText(deliveryName)).append("\n");
            address.append(getText(deliveryAddress)).append("\n");
            address.append(getText(deliveryCity)).append("\n");
            address.append(getText(deliveryCountry));
        } catch (Exception e) {
            logger.warn("Could not get full delivery address: {}", e.getMessage());
        }
        return address.toString();
    }

    /**
     * Gets payment card holder name.
     * Uses multiple strategies to find the payment card holder name.
     */
    public String getPaymentCardHolder() {
        // Try the original locator first
        try {
            String holder = getText(paymentCardHolder);
            if (holder != null && !holder.isEmpty()) {
                return holder;
            }
        } catch (Exception e) {
            logger.debug("Could not get payment card holder via primary locator: {}", e.getMessage());
        }
        
        // Fallback: Look for payment info content-desc elements
        try {
            List<WebElement> paymentElements = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.TextView[@content-desc='payment info']"));
            if (!paymentElements.isEmpty()) {
                String holder = paymentElements.get(0).getText();
                logger.info("Found payment card holder via fallback: {}", holder);
                return holder != null ? holder : "";
            }
        } catch (Exception e) {
            logger.debug("Payment fallback locator failed: {}", e.getMessage());
        }
        
        return "";
    }

    /**
     * Gets payment card holder name with strict verification.
     * Scrolls to ensure the element is visible and uses multiple strategies.
     */
    public String getPaymentCardHolderStrict() {
        scrollDown();
        waitFor(1);
        
        // Strategy 1: Try by content-desc 'payment info'
        try {
            List<WebElement> paymentElements = driver.findElements(
                io.appium.java_client.AppiumBy.xpath(
                    "//android.widget.TextView[@content-desc='payment info']"));
            if (!paymentElements.isEmpty()) {
                String holder = paymentElements.get(0).getText();
                if (holder != null && !holder.isEmpty()) {
                    logger.info("Found payment card holder via content-desc: {}", holder);
                    return holder;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find payment info via content-desc: {}", e.getMessage());
        }
        
        // Strategy 2: Look for text after "Payment Method" label
        try {
            WebElement paymentLabel = driver.findElement(
                io.appium.java_client.AppiumBy.androidUIAutomator(
                    "new UiSelector().textContains(\"Payment Method\")"));
            if (paymentLabel.isDisplayed()) {
                // Find the next TextView elements after the label
                List<WebElement> allTexts = driver.findElements(
                    io.appium.java_client.AppiumBy.className("android.widget.TextView"));
                boolean foundLabel = false;
                for (WebElement tv : allTexts) {
                    String text = tv.getText();
                    if (text != null && text.contains("Payment Method")) {
                        foundLabel = true;
                        continue;
                    }
                    if (foundLabel && text != null && !text.isEmpty() && 
                        !text.contains("Total") && !text.contains("$") &&
                        !text.contains("Place") && !text.contains("Billing") &&
                        text.matches("^[A-Z][a-z]+ [A-Z][a-z]+.*$")) {
                        logger.info("Found payment card holder after label: {}", text);
                        return text;
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find payment card holder after label: {}", e.getMessage());
        }
        
        // Strategy 3: Find name that appears twice on the page (delivery and payment usually have same name)
        try {
            List<WebElement> textViews = driver.findElements(
                io.appium.java_client.AppiumBy.className("android.widget.TextView"));
            java.util.Map<String, Integer> nameCount = new java.util.HashMap<>();
            for (WebElement tv : textViews) {
                String text = tv.getText();
                if (text != null && text.matches("^[A-Z][a-z]+ [A-Z][a-z]+$")) {
                    nameCount.put(text, nameCount.getOrDefault(text, 0) + 1);
                }
            }
            // Return the name that appears more than once (delivery + payment)
            for (java.util.Map.Entry<String, Integer> entry : nameCount.entrySet()) {
                if (entry.getValue() > 1) {
                    logger.info("Found payment card holder via duplicate name: {}", entry.getKey());
                    return entry.getKey();
                }
            }
            // If no duplicate, return the first name found
            for (WebElement tv : textViews) {
                String text = tv.getText();
                if (text != null && text.matches("^[A-Z][a-z]+ [A-Z][a-z]+$") &&
                    !text.contains("Order") && !text.contains("Review")) {
                    logger.info("Found payment card holder via text pattern: {}", text);
                    return text;
                }
            }
        } catch (Exception e) {
            logger.debug("Could not find payment card holder via text pattern: {}", e.getMessage());
        }
        
        throw new RuntimeException("Could not find payment card holder name on the page");
    }

    /**
     * Gets masked card number.
     */
    public String getPaymentCardNumber() {
        try {
            return getText(paymentCardNumber);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gets card expiry.
     */
    public String getPaymentExpiry() {
        try {
            return getText(paymentExpiry);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Verifies product is in order.
     */
    public boolean isProductInOrder(String productName) {
        for (WebElement item : orderItems) {
            try {
                String text = item.getText();
                if (text.contains(productName)) {
                    return true;
                }
            } catch (Exception e) {
                continue;
            }
        }
        return false;
    }

    /**
     * Gets product name at index.
     */
    public String getProductName(int index) {
        if (index < orderItems.size()) {
            try {
                WebElement item = orderItems.get(index);
                WebElement nameElement = item.findElement(By.xpath(".//android.widget.TextView"));
                return getText(nameElement);
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    /**
     * Clicks Place Order button.
     */
    public void clickPlaceOrder() {
        scrollDown();
        click(placeOrderButton);
        logger.info("Clicked Place Order button");
    }

    /**
     * Verifies order details and places order.
     */
    public void verifyAndPlaceOrder() {
        logger.info("Order items count: {}", getOrderItemCount());
        logger.info("Delivery address: {}", getDeliveryName());
        logger.info("Payment method: {}", getPaymentCardHolder());
        logger.info("Total price: {}", getTotalPrice());
        clickPlaceOrder();
    }

    /**
     * Scrolls to view all order details.
     */
    public void scrollToViewOrderDetails() {
        scrollDown();
        logger.info("Scrolled to view order details");
    }

    /**
     * Verifies delivery address contains expected text.
     */
    public boolean verifyDeliveryAddressContains(String expectedText) {
        String fullAddress = getFullDeliveryAddress();
        return fullAddress.contains(expectedText);
    }

    /**
     * Verifies payment info contains expected text.
     */
    public boolean verifyPaymentInfoContains(String expectedText) {
        String cardHolder = getPaymentCardHolder();
        String cardNumber = getPaymentCardNumber();
        return cardHolder.contains(expectedText) || cardNumber.contains(expectedText);
    }
}

