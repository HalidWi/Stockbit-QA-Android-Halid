@webview @menu @smoke
Feature: WebView Functionality
  As a user
  I want to interact with the WebView
  So that I can browse external websites within the app

  Background:
    Given the app is launched
    When I navigate to the "WebView" menu

  Scenario: Navigate to Google.com in WebView
    Then I should see the WebView page
    When I enter URL "https://google.com" in the WebView
    And I click "Go To Site" button
    Then the WebView should load successfully
    And I should be able to interact with the WebView

