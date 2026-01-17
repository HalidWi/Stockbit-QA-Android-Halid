@geolocation @menu @smoke
Feature: Geo Location Functionality
  As a user
  I want to control and view geo location data
  So that I can test location-based features

  Background:
    Given the app is launched
    When I navigate to the "Geo Location" menu

  Scenario: Start and Stop Observing Geo Location
    Then I should see the Geo Location page
    When I click "Start Observing" button
    Then I should see location data being displayed
    When I click "Stop Observing" button
    Then I should verify location buttons are functional

