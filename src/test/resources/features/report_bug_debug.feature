@report_bug_debug @menu
Feature: Report a Bug Debug Functionality
  As a developer
  I want to test predefined bug report forms
  So that I can quickly submit various bug scenarios

  Background:
    Given the app is launched
    When I navigate to the "Report a Bug (debug)" menu

  Scenario: Navigate to Report Bug Debug page
    Then I should see the Report Bug Debug page

