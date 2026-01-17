@report_bug @menu @smoke
Feature: Report a Bug Functionality
  As a user
  I want to report a bug with feedback
  So that I can provide input on issues

  Background:
    Given the app is launched
    When I navigate to the "Report a Bug" menu

  Scenario: Submit a bug report
    Then I should see the Report Bug page
    When I enter bug feedback "i found a bug in the payment method section"
    And I click "Send" bug report button
    Then I should see bug report success message

