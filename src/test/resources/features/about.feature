@about @menu @smoke
Feature: About Page Information
  As a user
  I want to view information about the app
  So that I can understand its details and origin

  Background:
    Given the app is launched
    When I navigate to the "About" menu

  Scenario: Verify About page and navigate to Sauce Labs website
    Then I should see the About page
    And I should see the Sauce Labs website link
    When I click on the Sauce Labs website link
    Then I should be redirected to Sauce Labs website

