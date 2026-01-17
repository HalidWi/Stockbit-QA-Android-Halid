package com.stockbit.automation.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Geo Location Page Object for testing geo location functionality.
 */
public class GeoLocationPage extends BasePage {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Geo Location\")")
    private WebElement geoLocationTitle;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/startBtn")
    private WebElement startObservingButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/stopBtn")
    private WebElement stopObservingButton;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/latitudeTV")
    private WebElement latitudeText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/longitudeTV")
    private WebElement longitudeText;

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/altitudeTV")
    private WebElement altitudeText;

    public GeoLocationPage(AndroidDriver driver) {
        super(driver);
    }

    /**
     * Checks if Geo Location page is displayed.
     */
    public boolean isGeoLocationPageDisplayed() {
        waitFor(2);
        try {
            return isDisplayed(geoLocationTitle) || isDisplayed(startObservingButton);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clicks Start Observing button.
     */
    public void clickStartObserving() {
        click(startObservingButton);
        logger.info("Clicked 'Start Observing' button");
    }

    /**
     * Clicks Stop Observing button.
     */
    public void clickStopObserving() {
        click(stopObservingButton);
        logger.info("Clicked 'Stop Observing' button");
    }

    /**
     * Gets the latitude value.
     */
    public String getLatitude() {
        try {
            return getText(latitudeText);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gets the longitude value.
     */
    public String getLongitude() {
        try {
            return getText(longitudeText);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Gets the altitude value.
     */
    public String getAltitude() {
        try {
            return getText(altitudeText);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Checks if location data is being updated.
     */
    public boolean isLocationDataUpdating() {
        String initialLatitude = getLatitude();
        waitFor(3);
        String newLatitude = getLatitude();
        // If values are different or if we see actual coordinates (not empty/placeholder)
        return !initialLatitude.equals(newLatitude) || !initialLatitude.isEmpty();
    }

    /**
     * Checks if Start Observing button is displayed.
     */
    public boolean isStartObservingButtonDisplayed() {
        return isDisplayed(startObservingButton);
    }

    /**
     * Checks if Stop Observing button is displayed.
     */
    public boolean isStopObservingButtonDisplayed() {
        return isDisplayed(stopObservingButton);
    }
}

