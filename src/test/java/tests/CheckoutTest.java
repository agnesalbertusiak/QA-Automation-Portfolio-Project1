package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

public class CheckoutTest extends BaseTest {

    @Test
    public void completeCheckoutFlow() {
        // Login
        new LoginPage(driver()).login(ConfigReader.get("username"), ConfigReader.get("password"));
        ProductsPage products = new ProductsPage(driver());
        Assert.assertTrue(products.isAt(), "Not on Products page after login");

        // Add item and go to cart
        products.addBackpack();
        products.openCart();

        // Checkout steps
        CartPage cart = new CartPage(driver());
        cart.clickCheckout();

        CheckoutPage checkout = new CheckoutPage(driver());
        checkout.enterInfo("Agnes", "A", "60601");
        checkout.continueToOverview();
        checkout.finishOrder();

        // Assert
        String done = checkout.getCompleteText();
        log().info("Completion text: " + done);
        Assert.assertTrue(done.toLowerCase().contains("thank you"), "Order completion message not shown");
    }
}

