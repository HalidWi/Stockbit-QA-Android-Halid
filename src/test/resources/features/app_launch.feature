@smoke @launch
Feature: App Launch
  As a user
  I want to launch the MDA application
  So that I can use its features

  Background:
    Given the app is launched

  @critical
  Scenario: Verify app launches successfully
    Then the app should be running
    And I should see the home screen

  @debug
  Scenario: Capture app information on launch
    Then the app should be running
    And I capture the current activity
    And I log the page source

  Scenario: App remains stable after brief wait
    When I wait for 3 seconds
    Then the app should be running
    And I should see the home screen

