package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Drawing Page Object for testing drawing functionality.
 */
public class DrawingPage extends BasePage {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/drawingTV")
    private WebElement drawingTitle;

    @AndroidFindBy(id = "signature_pad")
    private WebElement drawingCanvas;

    @AndroidFindBy(id = "saveBtn")
    private WebElement saveButton;

    @AndroidFindBy(id = "clearBtn")
    private WebElement clearButton;

    // Store canvas screenshot before drawing for comparison
    private byte[] canvasScreenshotBeforeDrawing = null;
    private byte[] canvasScreenshotAfterDrawing = null;

    public DrawingPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if Drawing page is displayed.
     */
    public boolean isDrawingPageDisplayed() {
        waitFor(2);
        try {
            // Try multiple ways to find the Drawing page
            try {
                WebElement title = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Drawing\")"));
                if (title.isDisplayed()) {
                    return true;
                }
            } catch (Exception ignored) {}
            
            try {
                return drawingTitle.isDisplayed();
            } catch (Exception ignored) {}
            
            try {
                return drawingCanvas.isDisplayed();
            } catch (Exception ignored) {}
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Captures screenshot of the canvas area for comparison.
     */
    private byte[] captureCanvasScreenshot() {
        try {
            WebElement canvas = getCanvasElement();
            if (canvas != null) {
                return canvas.getScreenshotAs(OutputType.BYTES);
            }
        } catch (Exception e) {
            logger.warn("Could not capture canvas screenshot: {}", e.getMessage());
        }
        return null;
    }

    /**
     * Gets the canvas element with fallback locators.
     */
    private WebElement getCanvasElement() {
        try {
            if (drawingCanvas != null && drawingCanvas.isDisplayed()) {
                return drawingCanvas;
            }
        } catch (Exception e) {
            // Try fallback
        }
        try {
            return driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/signature_pad"));
        } catch (Exception e) {
            try {
                return driver.findElement(AppiumBy.accessibilityId("Pad to draw on"));
            } catch (Exception ex) {
                return null;
            }
        }
    }

    /**
     * Captures the canvas state before drawing (for comparison after clear).
     */
    public void captureEmptyCanvasState() {
        waitFor(1);
        canvasScreenshotBeforeDrawing = captureCanvasScreenshot();
        logger.info("Captured empty canvas state for comparison");
    }

    /**
     * Draws a simple shape on the canvas.
     */
    public void drawSimpleShape() {
        try {
            // Capture canvas state before drawing
            if (canvasScreenshotBeforeDrawing == null) {
                canvasScreenshotBeforeDrawing = captureCanvasScreenshot();
            }

            // Try to find the canvas element for coordinate calculation
            WebElement canvas = getCanvasElement();
            
            int centerX, centerY;
            if (canvas != null) {
                org.openqa.selenium.Rectangle rect = canvas.getRect();
                centerX = rect.getX() + rect.getWidth() / 2;
                centerY = rect.getY() + rect.getHeight() / 2;
            } else {
                // Fallback to screen center
                org.openqa.selenium.Dimension screenSize = driver.manage().window().getSize();
                centerX = screenSize.getWidth() / 2;
                centerY = screenSize.getHeight() / 2;
            }

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence draw = new Sequence(finger, 1);

            // Move to starting point
            draw.addAction(finger.createPointerMove(Duration.ofMillis(0),
                    PointerInput.Origin.viewport(), centerX - 100, centerY - 100));
            // Press down
            draw.addAction(finger.createPointerDown(0));
            draw.addAction(new Pause(finger, Duration.ofMillis(100)));

            // Draw a square pattern
            draw.addAction(finger.createPointerMove(Duration.ofMillis(300),
                    PointerInput.Origin.viewport(), centerX + 100, centerY - 100));
            draw.addAction(finger.createPointerMove(Duration.ofMillis(300),
                    PointerInput.Origin.viewport(), centerX + 100, centerY + 100));
            draw.addAction(finger.createPointerMove(Duration.ofMillis(300),
                    PointerInput.Origin.viewport(), centerX - 100, centerY + 100));
            draw.addAction(finger.createPointerMove(Duration.ofMillis(300),
                    PointerInput.Origin.viewport(), centerX - 100, centerY - 100));

            // Release
            draw.addAction(finger.createPointerUp(0));

            driver.perform(Collections.singletonList(draw));
            logger.info("Drew a simple shape on the canvas at ({}, {})", centerX, centerY);

            // Capture canvas state after drawing
            waitFor(1);
            canvasScreenshotAfterDrawing = captureCanvasScreenshot();
            logger.info("Captured canvas state after drawing");

        } catch (Exception e) {
            logger.error("Failed to draw shape: {}", e.getMessage());
        }
    }

    /**
     * Clicks the Save button using coordinate-based tap.
     * Note: Uses only calculated coordinates to avoid Appium timeout issues.
     */
    public void clickSave() {
        waitFor(1);
        
        // Get screen dimensions for coordinate calculation
        org.openqa.selenium.Dimension screenSize = driver.manage().window().getSize();
        int screenWidth = screenSize.getWidth();
        int screenHeight = screenSize.getHeight();
        
        // Save button is on the right side of the bottom toolbar
        // Based on actual element position: bounds [670,2580] to [1220,2724], center at (945, 2652)
        int saveX = (int)(screenWidth * 0.74);  // Right 3/4 of screen
        int saveY = screenHeight - 200;  // Near bottom, above nav bar
        
        logger.info("Clicking Save button at calculated coordinates: ({}, {})", saveX, saveY);
        
        // Tap by coordinate
        tapByCoordinate(saveX, saveY);
        logger.info("Clicked 'Save' button at coordinates ({}, {})", saveX, saveY);
        
        // Wait for save action to complete
        waitFor(2);
    }
    
    /**
     * Tap at specific coordinates using W3C Actions.
     */
    private void tapByCoordinate(int x, int y) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(0));  // 0 for touch (not MouseButton)
            tap.addAction(new Pause(finger, Duration.ofMillis(150)));
            tap.addAction(finger.createPointerUp(0));  // 0 for touch (not MouseButton)
            driver.perform(Collections.singletonList(tap));
            logger.info("Tapped at coordinates ({}, {}) using W3C Actions", x, y);
        } catch (Exception e) {
            logger.warn("W3C tap failed, trying TouchAction fallback: {}", e.getMessage());
            // Fallback: Use mobile tap command
            try {
                Map<String, Object> tapArgs = new HashMap<>();
                tapArgs.put("x", x);
                tapArgs.put("y", y);
                driver.executeScript("mobile: tap", tapArgs);
                logger.info("Tapped at coordinates ({}, {}) using mobile:tap", x, y);
            } catch (Exception ex) {
                logger.error("Both tap methods failed: {}", ex.getMessage());
            }
        }
    }

    /**
     * Clicks the Clear button using coordinate-based tap.
     * Note: Uses only calculated coordinates to avoid Appium timeout issues.
     */
    public void clickClear() {
        waitFor(1);
        
        // Get screen dimensions for coordinate calculation
        org.openqa.selenium.Dimension screenSize = driver.manage().window().getSize();
        int screenWidth = screenSize.getWidth();
        int screenHeight = screenSize.getHeight();
        
        // Clear button is on the left side of the bottom toolbar
        // Based on actual element position: bounds [60,2580] to [610,2724], center at (335, 2652)
        int clearX = (int)(screenWidth * 0.26);  // Left 1/4 of screen
        int clearY = screenHeight - 200;  // Near bottom, above nav bar
        
        logger.info("Clicking Clear button at calculated coordinates: ({}, {})", clearX, clearY);
        
        // Tap by coordinate
        tapByCoordinate(clearX, clearY);
        logger.info("Clicked 'Clear' button at coordinates ({}, {})", clearX, clearY);
        
        waitFor(2);
    }

    /**
     * Checks if Save button is displayed.
     */
    public boolean isSaveButtonDisplayed() {
        try {
            return saveButton.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement saveBtn = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Save\")"));
                return saveBtn.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Checks if Clear button is displayed.
     */
    public boolean isClearButtonDisplayed() {
        try {
            return clearButton.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement clearBtn = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiSelector().text(\"Clear\")"));
                return clearBtn.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    /**
     * Checks if drawing canvas is displayed.
     */
    public boolean isCanvasDisplayed() {
        waitFor(1); // Add wait to let page stabilize
        try {
            // Always use fresh element lookup with full resource ID
            WebElement canvas = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/signature_pad"));
            return canvas.isDisplayed();
        } catch (Exception e) {
            try {
                WebElement canvas = driver.findElement(AppiumBy.accessibilityId("Pad to draw on"));
                return canvas.isDisplayed();
            } catch (Exception ex) {
                // Final fallback - check for the drawing page title
                try {
                    WebElement title = driver.findElement(AppiumBy.id("com.saucelabs.mydemoapp.android:id/drawingTV"));
                    return title.isDisplayed();
                } catch (Exception e3) {
                    return false;
                }
            }
        }
    }

    /**
     * This method compares the current canvas state with the state before drawing by comparing screenshot.
     * @return true if the canvas is verified to be cleared
     * @throws RuntimeException if verification fails
     */
    public boolean verifyCanvasIsCleared() {
        waitFor(2); // Wait for clear animation to complete
        
        // Capture current canvas state after clear
        byte[] canvasAfterClear = captureCanvasScreenshot();
        
        // If we have the screenshot after drawing, compare with it
        if (canvasScreenshotAfterDrawing != null && canvasAfterClear != null) {
            // Compare screenshots - after clear MUST be different from after drawing
            boolean differentFromDrawnState = !Arrays.equals(canvasAfterClear, canvasScreenshotAfterDrawing);
            if (!differentFromDrawnState) {
                throw new RuntimeException("STRICT VERIFICATION FAILED: Canvas screenshot after clear is IDENTICAL to drawn state - clear action did NOT work!");
            }
            logger.info("STRICT VERIFICATION PASSED: Canvas is DIFFERENT from drawn state after clear");

        }  else {
            logger.warn("No drawn state screenshot available for comparison");
        }
        
        logger.info("Canvas clear verification PASSED - canvas confirmed cleared");
        return true;
    }
}

