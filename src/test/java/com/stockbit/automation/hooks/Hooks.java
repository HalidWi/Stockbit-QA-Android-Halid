package com.stockbit.automation.hooks;

import com.stockbit.automation.context.TestContext;
import com.stockbit.automation.drivers.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

/**
 * Cucumber Hooks for setup and teardown operations.
 * Integrates with Allure for reporting.
 */
public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);
    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    /**
     * Runs before each scenario.
     * Initializes the driver and logs scenario start.
     *
     * @param scenario Current Cucumber scenario
     */
    @Before
    public void setUp(Scenario scenario) {
        logger.info("========================================");
        logger.info("Starting Scenario: {}", scenario.getName());
        logger.info("Tags: {}", scenario.getSourceTagNames());
        logger.info("========================================");

        // Initialize driver - this will start the app
        testContext.getDriver();
        
        // Dismiss any system overlays (notification shade, quick settings)
        dismissSystemOverlays();

        logger.info("Driver initialized successfully for scenario: {}", scenario.getName());
    }
    
    /**
     * Ensures the app is in foreground and dismisses any system overlays.
     * This is important because if the notification shade is open, element searches will fail.
     */
    private void dismissSystemOverlays() {
        try {
            AndroidDriver driver = DriverManager.getDriver();
            
            // Check if the app is in foreground by checking the current package
            String currentPackage = driver.getCurrentPackage();
            logger.info("Current package: {}", currentPackage);
            
            // If system UI is showing, activate the app
            if (currentPackage != null && (currentPackage.contains("systemui") || 
                currentPackage.contains("launcher") || !currentPackage.contains("saucelabs"))) {
                logger.info("System UI or launcher is showing, activating app...");
                driver.activateApp("com.saucelabs.mydemoapp.android");
                Thread.sleep(1000);
            }
            
            logger.info("App is in foreground");
        } catch (Exception e) {
            logger.warn("Could not check/dismiss system overlays: {}", e.getMessage());
        }
    }

    /**
     * Runs after each scenario.
     * Captures screenshot on failure and quits driver.
     *
     * @param scenario Current Cucumber scenario
     */
    @After
    public void tearDown(Scenario scenario) {
        logger.info("========================================");
        logger.info("Finishing Scenario: {}", scenario.getName());
        logger.info("Status: {}", scenario.getStatus());
        logger.info("========================================");

        try {
            if (scenario.isFailed() && DriverManager.isDriverInitialized()) {
                captureScreenshot(scenario, "Failure Screenshot");
            }
        } catch (Exception e) {
            logger.error("Error capturing screenshot: {}", e.getMessage());
        } finally {
            // Clean up context
            testContext.clearContext();

            // Quit driver
            DriverManager.quitDriver();
        }
    }

    /**
     * Runs after each step.
     * Can be used for step-level screenshots or logging.
     *
     * @param scenario Current Cucumber scenario
     */
    @AfterStep
    public void afterStep(Scenario scenario) {
        // Capture screenshot after each step for debugging (optional)
        // Uncomment below line if needed
        // captureScreenshot(scenario, "Step Screenshot");

        if (scenario.isFailed()) {
            logger.error("Step failed in scenario: {}", scenario.getName());
        }
    }

    /**
     * Captures screenshot and attaches to Allure report and Cucumber report.
     *
     * @param scenario   Current Cucumber scenario
     * @param screenshotName Name for the screenshot
     */
    private void captureScreenshot(Scenario scenario, String screenshotName) {
        try {
            if (DriverManager.isDriverInitialized()) {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

                // Attach to Cucumber report
                scenario.attach(screenshot, "image/png", screenshotName);

                // Attach to Allure report
                Allure.addAttachment(screenshotName, "image/png",
                        new ByteArrayInputStream(screenshot), ".png");

                logger.info("Screenshot captured: {}", screenshotName);
            }
        } catch (Exception e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
        }
    }
}

