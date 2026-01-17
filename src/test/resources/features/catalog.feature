@smoke @catalog
Feature: Product Catalog
  As a customer
  I want to browse the product catalog
  So that I can find products to purchase

  @critical
  Scenario: View product catalog
    Given the app is launched
    Then I should see the catalog page
    And products should be displayed

  Scenario: Scroll through products
    Given the app is launched
    Then I should see the catalog page
    When I scroll down to view more products
    Then I should still be on the catalog page

  Scenario: Select a product & select color
    Given the app is launched
    Then I should see the catalog page
    When I select the first product
    Then I should see the product detail page
    And product title should be displayed
    And product price should be displayed
    And add to cart button should be displayed
    When I select "Blue" color
    Then the color should be selected