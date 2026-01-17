@reset @menu @smoke
Feature: Reset App State Functionality
  As a user
  I want to reset the application to its initial state
  So that I can start fresh

  Scenario: Reset App State via Menu
    Given the app is launched
    When I navigate to the "Reset App State" menu
    Then I should see the reset confirmation popup
    When I click the "RESET APP" button in the popup
    Then the app state should be reset successfully

