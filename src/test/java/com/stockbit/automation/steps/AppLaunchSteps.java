package com.stockbit.automation.steps;

import com.stockbit.automation.context.TestContext;
import com.stockbit.automation.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for app launch scenarios.
 */
public class AppLaunchSteps {

    private static final Logger logger = LoggerFactory.getLogger(AppLaunchSteps.class);
    private final TestContext testContext;
    private HomePage homePage;

    public AppLaunchSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the app is launched")
    @Step("Launch the application")
    public void theAppIsLaunched() {
        logger.info("App is being launched...");
        homePage = new HomePage(testContext.getDriver());
        // App is already launched when driver is initialized
        logger.info("App launched successfully");
    }

    @Then("the app should be running")
    @Step("Verify the app is running")
    public void theAppShouldBeRunning() {
        logger.info("Verifying app is running...");
        assertNotNull(testContext.getDriver(), "Driver should not be null");
        assertTrue(homePage.isHomePageDisplayed(), "App should be displayed");
        logger.info("App is running and home page is displayed");
    }

    @Then("I should see the home screen")
    @Step("Verify home screen is displayed")
    public void iShouldSeeTheHomeScreen() {
        logger.info("Verifying home screen is displayed...");
        assertTrue(homePage.isHomePageDisplayed(), "Home screen should be displayed");
        logger.info("Home screen verified");
    }

    @When("I wait for {int} seconds")
    @Step("Wait for {0} seconds")
    public void iWaitForSeconds(int seconds) {
        logger.info("Waiting for {} seconds...", seconds);
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.info("Wait completed");
    }

    @Then("I capture the current activity")
    @Step("Capture current activity name")
    public void iCaptureTheCurrentActivity() {
        String activity = homePage.getCurrentActivity();
        logger.info("Current Activity: {}", activity);
        testContext.setContext("currentActivity", activity);
        assertNotNull(activity, "Activity should not be null");
    }

    @Then("I log the page source")
    @Step("Log page source for debugging")
    public void iLogThePageSource() {
        String pageSource = homePage.getPageSource();
        logger.info("Page Source length: {} characters", pageSource.length());
        // Log first 500 characters for debugging
        logger.debug("Page Source (first 500 chars): {}", 
                pageSource.substring(0, Math.min(500, pageSource.length())));
    }
}

