@login_menu @menu @smoke
Feature: Login Menu Functionality
  As a user
  I want to log in via the main menu
  So that I can access authenticated features

  Background:
    Given the app is launched
    When I navigate to the "Log In" menu

  Scenario: Successful login via menu
    Then I should see the Login menu page
    When I login via menu with valid user "bod@example.com" and password "10203040"
    Then I should be logged in successfully via menu

  Scenario: Failed login with locked user via menu
    Then I should see the Login menu page
    When I login via menu with locked user "alice@example.com" and password "10203040"
    Then I should see locked user error message via menu

