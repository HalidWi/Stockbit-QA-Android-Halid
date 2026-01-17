package com.stockbit.automation.pages;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * QR Scanner Page Object for testing QR code scanner functionality.
 */
public class QRScannerPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"QR Code\")")
    private WebElement qrScannerTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cameraView")
    private WebElement cameraPreview;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/scannerView")
    private WebElement scannerView;

    public QRScannerPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if QR Scanner page is displayed.
     */
    public boolean isQRScannerPageDisplayed() {
        waitFor(2);
        try {
            return isDisplayed(qrScannerTitle) || isDisplayed(cameraPreview) || isDisplayed(scannerView);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if camera preview is displayed.
     */
    public boolean isCameraPreviewDisplayed() {
        try {
            return isDisplayed(cameraPreview) || isDisplayed(scannerView);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Grants camera permission if prompted.
     */
    public void grantCameraPermissionIfPrompted() {
        try {
            // Try different permission button locators for different Android versions
            WebElement allowButton = null;
            
            try {
                allowButton = driver.findElement(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"));
            } catch (Exception e1) {
                try {
                    allowButton = driver.findElement(AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button"));
                } catch (Exception e2) {
                    try {
                        allowButton = driver.findElement(AppiumBy.androidUIAutomator(
                                "new UiSelector().text(\"While using the app\")"
                        ));
                    } catch (Exception e3) {
                        try {
                            allowButton = driver.findElement(AppiumBy.androidUIAutomator(
                                    "new UiSelector().text(\"Allow\")"
                            ));
                        } catch (Exception e4) {
                            logger.info("No permission dialog found");
                            return;
                        }
                    }
                }
            }
            
            if (allowButton != null && allowButton.isDisplayed()) {
                click(allowButton);
                logger.info("Granted camera permission");
            }
        } catch (Exception e) {
            logger.info("Camera permission prompt not found or already granted");
        }
    }

    /**
     * Note: Actual QR code scanning via automation requires either:
     * 1. Using a physical device with a real QR code
     * 2. Injecting an image into the camera feed (device-specific)
     * 3. Mocking the QR scanner response
     * 
     * This method is a placeholder for such functionality.
     */
    public void scanQRCode() {
        logger.info("QR code scanning initiated - note: actual scanning requires physical QR code or mock");
    }
}

