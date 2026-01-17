@e2e @checkout
Feature: E2E Checkout Flow
  As a customer
  I want to complete the entire checkout process
  So that I can purchase products from the catalog

  Background:
    Given the app is launched
    And I navigate to catalog from menu

  @critical @smoke
  Scenario: Complete E2E checkout with locked user then valid user
    # Step 1: Select product from catalog
    Given I am on the catalog page
    When I scroll down to view more products
    And I select the first product
    Then I should see the product detail page

    # Step 2: Add product to cart
    When I select "Blue" color
    And I click add to cart button
    And I click on cart icon
    Then I should see the cart page
    And the cart should contain at least 1 item
    And the selected item name in cart should be correct
    And the selected item color in cart should be correct

    # Step 3: Proceed to checkout
    When I click proceed to checkout
    Then I should see the login page

    # Step 4-5: Login with valid user (locked user test moved to separate scenario)
    When I login with valid user "bod@example.com" and password "10203040"
    Then I should see the shipping address page

    # Step 6: Test shipping address validation
    When I click to payment button without filling fields
    Then I should see shipping address validation errors

    # Step 7: Fill shipping address and proceed
    When I fill shipping address with:
      | Full Name     | Rebecca Winter          |
      | Address Line 1| Mandorley 112           |
      | City          | Truro                   |
      | Zip Code      | 89750                   |
      | Country       | United Kingdom          |
    And I click to payment button
    Then I should see the payment page

    # Step 8: Test payment validation (empty fields show "Value looks invalid" errors)
    When I click review order button without filling fields
    Then I should see payment validation errors

    # Step 9: Fill correct payment details
    And I fill payment details with:
      | Full Name       | Rebecca Winter    |
      | Card Number     | 3258125896581562  |
      | Expiration Date | 03/25             |
      | Security Code   | 123               |

    # Step 10: Proceed to review order
    When I click review order button
    Then I should see the review order page

    # Step 13: Verify order details
    And the order should contain the selected product
    When I scroll down to see order details
    Then the delivery address name should be "Rebecca Winter"
    And the payment card holder name should be "Rebecca Winter"

    # Step 14: Place order
    When I click place order button
    Then I should see the checkout complete page
    And I should see "Checkout Complete" message
    And I should see "Thank you for your order" message

    # Step 15: Return to shopping
    When I click continue shopping button
    Then I should be back on the products page