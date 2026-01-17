@qr_scanner @menu
Feature: QR Code Scanner Functionality
  As a user
  I want to use the QR Code Scanner
  So that I can scan QR codes

  Background:
    Given the app is launched
    When I navigate to the "QR Code Scanner" menu

  Scenario: Access QR Code Scanner
    Then I should grant camera permission if requested
    And I should see the QR Code Scanner page
    And I should see the camera preview for scanning
    # Note: Actual QR code scanning via automation requires
    # physical QR code or mock implementation

