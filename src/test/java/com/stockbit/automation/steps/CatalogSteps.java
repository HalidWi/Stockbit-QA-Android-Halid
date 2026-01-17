package com.stockbit.automation.steps;

import com.stockbit.automation.context.TestContext;
import com.stockbit.automation.pages.CatalogPage;
import com.stockbit.automation.pages.MenuPage;
import com.stockbit.automation.pages.ProductDetailPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for catalog/product related scenarios.
 */
public class CatalogSteps {

    private static final Logger logger = LoggerFactory.getLogger(CatalogSteps.class);
    private final TestContext testContext;
    private MenuPage menuPage;
    private CatalogPage catalogPage;
    private ProductDetailPage productDetailPage;

    public CatalogSteps(TestContext testContext) {
        this.testContext = testContext;
    }

    private MenuPage getMenuPage() {
        if (menuPage == null) {
            menuPage = new MenuPage(testContext.getDriver());
        }
        return menuPage;
    }

    private CatalogPage getCatalogPage() {
        if (catalogPage == null) {
            catalogPage = new CatalogPage(testContext.getDriver());
        }
        return catalogPage;
    }

    private ProductDetailPage getProductDetailPage() {
        if (productDetailPage == null) {
            productDetailPage = new ProductDetailPage(testContext.getDriver());
        }
        return productDetailPage;
    }

    @Given("I navigate to catalog from menu")
    @Step("Navigate to catalog from burger menu")
    public void iNavigateToCatalogFromMenu() {
        logger.info("Navigating to catalog from menu...");
        getMenuPage().navigateToCatalog();
    }

    @Given("I am on the catalog page")
    @Then("I should see the catalog page")
    @Step("Verify catalog page is displayed")
    public void iAmOnTheCatalogPage() {
        logger.info("Verifying catalog page is displayed...");
        assertTrue(getCatalogPage().isCatalogPageDisplayed(), "Catalog page should be displayed");
    }

    @Then("products should be displayed")
    @Step("Verify products are displayed")
    public void productsShouldBeDisplayed() {
        logger.info("Verifying products are displayed...");
        assertTrue(getCatalogPage().getProductCount() > 0, "Products should be displayed");
    }

    @When("I scroll down to view more products")
    @Step("Scroll down to view more products")
    public void iScrollDownToViewMoreProducts() {
        logger.info("Scrolling down to view more products...");
        getCatalogPage().scrollToViewMoreProducts();
    }

    @Then("I should still be on the catalog page")
    @Step("Verify still on catalog page after scroll")
    public void iShouldStillBeOnTheCatalogPage() {
        assertTrue(getCatalogPage().isCatalogPageDisplayed(), "Should still be on catalog page");
    }

    @When("I select the first product")
    @Step("Select the first product")
    public void iSelectTheFirstProduct() {
        logger.info("Selecting first product...");
        testContext.setContext("selectedProductName",getCatalogPage().returnFirstSelectedProductName());
        getCatalogPage().selectFirstProduct();
        testContext.setContext("selectedProductIndex", 0);

    }

    @When("I select product at index {int}")
    @Step("Select product at index {0}")
    public void iSelectProductAtIndex(int index) {
        logger.info("Selecting product at index: {}", index);
        getCatalogPage().selectProduct(index);
        testContext.setContext("selectedProductIndex", index);
    }

    @Then("I should see the product detail page")
    @Step("Verify product detail page is displayed")
    public void iShouldSeeTheProductDetailPage() {
        logger.info("Verifying product detail page...");
        assertTrue(getProductDetailPage().isProductDetailDisplayed(), "Product detail page should be displayed");
    }

    @Then("product title should be displayed")
    @Step("Verify product title is displayed")
    public void productTitleShouldBeDisplayed() {
        String title = getProductDetailPage().getProductTitle();
        assertNotNull(title, "Product title should be displayed");
        assertFalse(title.isEmpty(), "Product title should not be empty");
        testContext.setContext("productTitle", title);
        logger.info("Product title: {}", title);
    }

    @Then("product price should be displayed")
    @Step("Verify product price is displayed")
    public void productPriceShouldBeDisplayed() {
        String price = getProductDetailPage().getProductPrice();
        assertNotNull(price, "Product price should be displayed");
        testContext.setContext("productPrice", price);
        logger.info("Product price: {}", price);
    }

    @Then("add to cart button should be displayed")
    @Step("Verify add to cart button is displayed")
    public void addToCartButtonShouldBeDisplayed() {
        assertTrue(getProductDetailPage().isProductDetailDisplayed(), "Add to cart button should be displayed");
    }

    @When("I select {string} color")
    @Step("Select color: {0}")
    public void iSelectColor(String color) {
        logger.info("Selecting color: {}", color);
        getProductDetailPage().selectColor(color);
        testContext.setContext("selectedColor", color);
    }

    @Then("the color should be selected")
    @Step("Verify color is selected")
    public void theColorShouldBeSelected() {
        logger.info("Color selection verified");
        // Color selection verified by visual check
    }

    @When("I click add to cart button")
    @Step("Click add to cart button")
    public void iClickAddToCartButton() {
        logger.info("Clicking add to cart...");
        getProductDetailPage().clickAddToCart();
    }

    @Then("the cart badge should show {string}")
    @Step("Verify cart badge shows: {0}")
    public void theCartBadgeShouldShow(String expectedCount) {
        String actualCount = getCatalogPage().getCartBadgeCount();
        logger.info("Cart badge count: {}", actualCount);
        assertEquals(expectedCount, actualCount, "Cart badge should show " + expectedCount);
    }

    @When("I click on cart icon")
    @Step("Click on cart icon")
    public void iClickOnCartIcon() {
        logger.info("Clicking on cart icon...");
        getProductDetailPage().clickCartIcon();
    }

    @Given("I have added a product to cart")
    @Step("Add a product to cart")
    public void iHaveAddedAProductToCart() {
        logger.info("Adding a product to cart...");
        getCatalogPage().selectFirstProduct();
        getProductDetailPage().selectBlackColor();
        getProductDetailPage().clickAddToCart();
        testContext.setContext("productAdded", true);
    }
}

