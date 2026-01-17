# Automation Test Suite Summary

## Overview
This document provides a comprehensive summary of all automated test cases in the **Stockbit QA Android Automation** project. The test suite is built using **Appium 2 + UiAutomator2**, **Cucumber-JVM**, **JUnit 5**, and **PicoContainer** for the My Demo App (MDA) Android application.

---

## Test Suite Statistics
| Category                 | Count |
|--------------------------|-------|
| **Total Feature Files**  | 13    |
| **Total Test Scenarios** | 23    |
| **Critical/Smoke Tests** | 11    |
| **Negative Tests**       | 3     |
| **Menu Feature Tests**   | 10    |

---

## Feature Files Summary

### 1. E2E Checkout Flow (`checkout_e2e.feature`)
**Tags:** `@e2e`, `@checkout`

| Scenario                                               | Tags                            | Description                                                              | Validations                                                                                                                                                                      |
|--------------------------------------------------------|---------------------------------|--------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Complete E2E checkout with locked user then valid user | `@critical`, `@smoke`, `@halid` | Full end-to-end purchase flow from product selection to order completion | Product selection, cart verification (name/color), login, shipping address validation, payment validation, order review (delivery address, payment details), checkout completion |
| Add product to cart and verify                         | `@smoke`                        | Quick smoke test for adding product to cart                              | Product added to cart, cart page displayed                                                                                                                                       |
| Login with locked user shows error                     | `@negative`, `@login`           | Negative test for locked user authentication                             | Error message "Sorry this user has been locked out" displayed                                                                                                                    |
| Shipping address validation errors                     | `@negative`, `@validation`      | Negative test for empty shipping form submission                         | Validation errors for Full Name, Address, City, Zip Code, Country                                                                                                                |
| Payment validation errors                              | `@negative`, `@validation`      | Negative test for empty payment form submission                          | Validation errors for Full Name, Card Number, Expiration Date, Security Code                                                                                                     |
| Billing address toggle functionality                   | `@functional`                   | Tests billing address checkbox toggle                                    | Billing address fields show/hide based on checkbox state                                                                                                                         |

---

### 2. Product Catalog (`catalog.feature`)
**Tags:** `@smoke`, `@catalog`

| Scenario                    | Tags        | Description                                  | Validations                                                             |
|-----------------------------|-------------|----------------------------------------------|-------------------------------------------------------------------------|
| View product catalog        | `@critical` | Verify catalog page loads with products      | Catalog page displayed, products visible                                |
| Scroll through products     | -           | Test scrolling functionality in product list | Can scroll and remain on catalog page                                   |
| Select a product            | -           | Open product detail page                     | Product detail page displayed with title, price, and add to cart button |
| Select product color        | -           | Test color selection on product detail       | Selected color is highlighted                                           |
| Add product to cart         | `@cart`     | Add product with specific color to cart      | Cart badge shows "1"                                                    |
| View cart from product page | `@cart`     | Navigate to cart after adding product        | Cart page displayed with at least 1 item                                |

---

### 3. Shopping Cart (`cart.feature`)
**Tags:** `@smoke`, `@cart`

| Scenario                 | Tags        | Description                    | Validations                                           |
|--------------------------|-------------|--------------------------------|-------------------------------------------------------|
| View cart contents       | -           | View items in shopping cart    | Cart page displayed, items present, total price shown |
| Remove item from cart    | -           | Remove single item from cart   | Cart becomes empty after removal                      |
| Update item quantity     | -           | Increase item quantity         | Quantity updated, total price recalculated            |
| Proceed to checkout      | `@checkout` | Navigate from cart to checkout | Login page displayed                                  |
| Empty cart shows message | -           | Verify empty cart state        | Empty cart message and "Go Shopping" button displayed |

---

### 4. Reset App State (`reset_app_state.feature`)
**Tags:** `@reset`, `@menu`, `@smoke`

| Scenario                 | Tags | Description                                     | Validations                                                                        |
|--------------------------|------|-------------------------------------------------|------------------------------------------------------------------------------------|
| Reset App State via Menu | -    | Reset application to initial state through menu | Confirmation popup displayed, "RESET APP" button clicks, app state reset confirmed |

---

### 5. WebView (`webview.feature`)
**Tags:** `@webview`, `@menu`, `@smoke`

| Scenario                          | Tags | Description                               | Validations                                                  |
|-----------------------------------|------|-------------------------------------------|--------------------------------------------------------------|
| Navigate to Google.com in WebView | -    | Load external website in embedded WebView | WebView page displayed, URL entered, site loads successfully |

---

### 6. Geo Location (`geolocation.feature`)
**Tags:** `@geolocation`, `@menu`, `@smoke`

| Scenario                              | Tags | Description                        | Validations                                                                         |
|---------------------------------------|------|------------------------------------|-------------------------------------------------------------------------------------|
| Start and Stop Observing Geo Location | -    | Test location observation controls | Geo Location page displayed, Start/Stop buttons functional, location data displayed |

---

### 7. Drawing (`drawing.feature`)
**Tags:** `@drawing`, `@menu`, `@smoke`

| Scenario                        | Tags | Description                       | Validations                                                                                      |
|---------------------------------|------|-----------------------------------|--------------------------------------------------------------------------------------------------|
| Draw, Save, and Clear a Drawing | -    | Test drawing canvas functionality | Drawing page displayed, can draw on canvas, save confirmation, canvas cleared after clear action |

---

### 8. About (`about.feature`)
**Tags:** `@about`, `@menu`, `@smoke`

| Scenario                                             | Tags | Description                            | Validations                                                          |
|------------------------------------------------------|------|----------------------------------------|----------------------------------------------------------------------|
| Verify About page and navigate to Sauce Labs website | -    | View app information and external link | About page displayed, Sauce Labs link visible, link navigation works |

---

### 9. Report a Bug (`report_bug.feature`)
**Tags:** `@report_bug`, `@menu`, `@smoke`

| Scenario            | Tags | Description                             | Validations                                                                   |
|---------------------|------|-----------------------------------------|-------------------------------------------------------------------------------|
| Submit a bug report | -    | Submit feedback through bug report form | Report Bug page displayed, feedback entered, success message after submission |

---

### 10. Report a Bug Debug (`report_bug_debug.feature`)
**Tags:** `@report_bug_debug`, `@menu`

| Scenario                          | Tags | Description                           | Validations                     |
|-----------------------------------|------|---------------------------------------|---------------------------------|
| Navigate to Report Bug Debug page | -    | Access debug bug report functionality | Report Bug Debug page displayed |

---

### 11. Crash App Debug (`crash_app_debug.feature`)
**Tags:** `@crash_app_debug`, `@menu`

| Scenario                                 | Tags | Description                                           | Validations                    |
|------------------------------------------|------|-------------------------------------------------------|--------------------------------|
| Verify Crash App Debug page is displayed | -    | Access crash debug functionality (without triggering) | Crash App Debug page displayed |

---

### 12. Login Menu (`login_menu.feature`)
**Tags:** `@login_menu`, `@menu`, `@smoke`

| Scenario                               | Tags | Description                                 | Validations                                                             |
|----------------------------------------|------|---------------------------------------------|-------------------------------------------------------------------------|
| Successful login via menu              | -    | Login with valid credentials from main menu | Login page displayed, successful authentication, redirected to products |
| Failed login with locked user via menu | -    | Login with locked user from main menu       | Login page displayed, error message for locked user                     |

---

### 13. QR Code Scanner (`qr_scanner.feature`)
**Tags:** `@qr_scanner`, `@menu`

| Scenario               | Tags | Description                            | Validations                                                                  |
|------------------------|------|----------------------------------------|------------------------------------------------------------------------------|
| Access QR Code Scanner | -    | Open QR scanner with camera permission | Camera permission handled, QR Scanner page displayed, camera preview visible |

---

## Test Execution Tags

### By Priority
| Tag         | Description             | Scenarios |
|-------------|-------------------------|-----------|
| `@critical` | Business-critical tests | 2         |
| `@smoke`    | Quick sanity checks     | 11        |

### By Feature Area
| Tag         | Description      | Scenarios |
|-------------|------------------|-----------|
| `@e2e`      | End-to-end flows | 6         |
| `@checkout` | Checkout process | 7         |
| `@cart`     | Shopping cart    | 7         |
| `@catalog`  | Product catalog  | 6         |
| `@menu`     | Menu navigation  | 10        |
| `@login`    | Authentication   | 3         |

### By Test Type
| Tag           | Description            | Scenarios |
|---------------|------------------------|-----------|
| `@negative`   | Negative/error testing | 3         |
| `@functional` | Functional testing     | 1         |
| `@validation` | Form validation        | 2         |

---

## Running Tests

### Run All Tests
```bash
mvn clean test
```

### Run by Tag
```bash
# Critical tests only
mvn test "-Dcucumber.filter.tags=@critical"

# Smoke tests
mvn test "-Dcucumber.filter.tags=@smoke"

# E2E checkout
mvn test "-Dcucumber.filter.tags=@halid"

# Menu features
mvn test "-Dcucumber.filter.tags=@menu"
```

### Run Specific Feature
```bash
# Checkout E2E
mvn test "-Dcucumber.filter.tags=@e2e"

# Reset App State
mvn test "-Dcucumber.filter.tags=@reset"

# Drawing feature
mvn test "-Dcucumber.filter.tags=@drawing"
```

---

## Key Test Flows

### Primary E2E Flow (`@halid`)
```
App Launch → Catalog → Select Product → Choose Color → Add to Cart → 
Verify Cart (name/color) → Proceed to Checkout → Login (bod@example.com) → 
Shipping Address (validation + fill) → Payment (validation + fill) → 
Review Order (verify delivery/payment details) → Place Order → 
Checkout Complete → Return to Shopping
```

### Login Flow Validation
- **Valid User:** `bod@example.com` / `10203040` → Success
- **Locked User:** `alice@example.com` / `10203040` → Error Message

### Form Validation Coverage
- **Shipping Address:** Full Name, Address Line 1, City, Zip Code, Country
- **Payment:** Full Name, Card Number, Expiration Date, Security Code

---

## Page Objects

| Page Object            | Purpose                                           |
|------------------------|---------------------------------------------------|
| `BasePage`             | Common utilities (click, enterText, scroll, wait) |
| `MenuPage`             | Burger menu navigation                            |
| `CatalogPage`          | Product listing and selection                     |
| `ProductDetailPage`    | Product details and color selection               |
| `CartPage`             | Shopping cart management                          |
| `CheckoutLoginPage`    | Checkout authentication                           |
| `ShippingAddressPage`  | Shipping form                                     |
| `PaymentPage`          | Payment form                                      |
| `ReviewOrderPage`      | Order review                                      |
| `CheckoutCompletePage` | Order confirmation                                |
| `WebViewPage`          | WebView interactions                              |
| `GeoLocationPage`      | Location controls                                 |
| `DrawingPage`          | Canvas drawing                                    |
| `AboutPage`            | App information                                   |
| `ResetAppStatePage`    | App reset                                         |
| `ReportBugPage`        | Bug reporting                                     |
| `ReportBugDebugPage`   | Debug bug reports                                 |
| `CrashAppDebugPage`    | Crash debugging                                   |
| `LoginMenuPage`        | Menu login                                        |
| `QRScannerPage`        | QR scanning                                       |

---

## Notes

- **WebView/QR Scanner:** These features require device permissions and may behave differently on emulator vs physical device
- **Crash App Debug:** Test only verifies page access; actual crash not triggered to maintain test stability
- **Drawing:** Save/clear verification is lenient due to platform limitations in detecting canvas state changes
- **Allure Reports:** Generated in `target/allure-results` for detailed test reporting

---