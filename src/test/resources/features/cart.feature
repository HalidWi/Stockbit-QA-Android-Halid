@smoke @cart
Feature: Shopping Cart
  As a customer
  I want to manage my shopping cart
  So that I can review items before checkout

  @critical
  Scenario: View cart contents and update cart item quantity
    Given the app is launched
    And I have added a product to cart
    When I click on cart icon
    Then I should see the cart page
    And the cart should contain at least 1 item
    And total price should be displayed
    When I increase quantity of first item
    Then the quantity should be updated
    And total price should be updated

  @checkout
  Scenario: Proceed to checkout
    Given the app is launched
    And I have added a product to cart
    When I click on cart icon
    Then I should see the cart page
    When I click proceed to checkout
    Then I should see the login page

  @deleteCart
  Scenario: Empty cart shows message
    Given the app is launched
    And I have added a product to cart
    When I click on cart icon
    Then I should see the cart page
    When I remove all items from cart
    Then I should see empty cart message
    And go shopping button should be displayed
