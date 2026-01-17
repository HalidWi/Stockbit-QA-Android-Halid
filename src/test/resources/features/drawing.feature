@drawing @menu @smoke
Feature: Drawing Canvas Functionality
  As a user
  I want to draw on a canvas and manage my drawings
  So that I can test drawing features

  Background:
    Given the app is launched
    When I navigate to the "Drawing" menu

  Scenario: Draw, Save, and Clear a Drawing
    Then I should see the Drawing page
    When I draw a simple shape on the canvas
    And I click "Save" drawing button
    And I click "Clear" drawing button
    Then the canvas should be cleared
