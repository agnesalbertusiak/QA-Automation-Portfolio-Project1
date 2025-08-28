package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

public class CartTest extends BaseTest {

    @Test
    public void addItemUpdatesCartBadge() {
        new LoginPage(driver()).login(ConfigReader.get("username"), ConfigReader.get("password"));

        ProductsPage products = new ProductsPage(driver());
        Assert.assertTrue(products.isAt(), "Not on Products page after login");

        products.addBackpack();
        String count = products.getCartCount();
        log().info("Cart badge count = " + count);
        Assert.assertEquals(count, "1", "Cart count should be 1 after adding one item");
    }
}

