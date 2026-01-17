@crash_app_debug @menu
Feature: Crash App Debug Functionality
  As a developer
  I want to intentionally crash the app
  So that I can test crash reporting mechanisms

  Background:
    Given the app is launched
    When I navigate to the "Crash app (debug)" menu

  Scenario: Verify Crash App Debug page is displayed
    Then I should see the Crash App Debug page
    # Note: Actually triggering crash is not recommended in automated tests
    # as it will terminate the app and session

