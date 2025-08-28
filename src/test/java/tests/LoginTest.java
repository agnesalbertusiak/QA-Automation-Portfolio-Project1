package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;
import utils.ConfigReader;

public class LoginTest extends BaseTest {

    @Test
    public void validLogin() {
        LoginPage login = new LoginPage(driver());
        login.login(ConfigReader.get("username"), ConfigReader.get("password"));

        ProductsPage products = new ProductsPage(driver());
        log().info("Asserting we are on Products page");
        Assert.assertTrue(products.isAt(), "Products page was not displayed after login");
    }

    @Test
    public void invalidLogin_showsError() {
        LoginPage login = new LoginPage(driver());
        login.login("locked_out_user", "wrong_password");

        String error = login.getErrorText();
        log().info("Error message: " + error);
        Assert.assertTrue(error.toLowerCase().contains("epic sadface"),
                "Expected error message to contain 'Epic sadface'");
    }
}

