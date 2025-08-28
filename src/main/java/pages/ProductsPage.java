package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {
    private final WebDriver driver;

    private final By productsTitle = By.cssSelector(".title"); // "Products"
    private final By addBackpack = By.id("add-to-cart-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By cartBadge = By.className("shopping_cart_badge");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAt() {
        return driver.findElement(productsTitle).isDisplayed();
    }

    public void addBackpack() {
        driver.findElement(addBackpack).click();
    }

    public void openCart() {
        driver.findElement(cartIcon).click();
    }

    public String getCartCount() {
        return driver.findElements(cartBadge).isEmpty() ? "0" : driver.findElement(cartBadge).getText();
    }
}

