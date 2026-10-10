package ecom.bitm.saglabs.objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[normalize-space()='Products']")
    private WebElement productsTitle;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    public ProductPage(WebDriver driver) {
        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30));

        PageFactory.initElements(driver, this);
    }

    public boolean isProductsPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOf(productsTitle)
        ).isDisplayed();
    }

    public String getProductsPageTitle() {

        return wait.until(
                ExpectedConditions.visibilityOf(productsTitle)
        ).getText();
    }

    public int getProductCount() {

        wait.until(
                ExpectedConditions.visibilityOf(productsTitle));

        return productNames.size();
    }

    public void sortProductsByVisibleText(String sortOption) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        sortDropdown));

        Select select = new Select(dropdown);

        select.selectByVisibleText(sortOption);
    }

    public void sortProductsByValue(String value) {

        WebElement dropdown = wait.until(
                ExpectedConditions.elementToBeClickable(
                        sortDropdown));

        Select select = new Select(dropdown);

        select.selectByValue(value);
    }

    public void clickProduct(String productName) {

        String name = productName.trim();

        for (WebElement product : productNames) {

            if (product.getText()
                    .trim()
                    .equalsIgnoreCase(name)) {

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(product)
                ).click();

                return;
            }
        }

        throw new IllegalArgumentException(
                "Product not found: " + name);
    }

    public void addProductToCart(String productName) {

        String name = productName.trim();

        By addButton = By.xpath(
                "//div[contains(@class,'inventory_item')]" +
                "[.//div[contains(@class,'inventory_item_name')" +
                " and normalize-space()=\"" + name + "\"]]" +
                "//button[contains(@id,'add-to-cart')]");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(
                        addButton));

        button.click();

        wait.until(
                ExpectedConditions.visibilityOf(cartBadge));
    }

    public void addFirstProductToCart() {

        wait.until(
                ExpectedConditions.visibilityOf(productsTitle));

        if (productNames.isEmpty()) {

            throw new IllegalStateException(
                    "No products are displayed");
        }

        String productName =
                productNames.get(0).getText();

        addProductToCart(productName);
    }

    public void removeProductFromCart(String productName) {

        String name = productName.trim();

        By removeButton = By.xpath(
                "//div[contains(@class,'inventory_item')]" +
                "[.//div[contains(@class,'inventory_item_name')" +
                " and normalize-space()=\"" + name + "\"]]" +
                "//button[contains(@id,'remove')]");

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        removeButton)).click();
    }

    public void clickCart() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cartIcon)).click();

        wait.until(
                ExpectedConditions.urlContains("cart"));
    }

    public String getCartItemCount() {

        if (driver.findElements(
                By.className("shopping_cart_badge")).isEmpty()) {

            return "0";
        }

        return wait.until(
                ExpectedConditions.visibilityOf(cartBadge)
        ).getText();
    }

    public boolean isCartBadgeDisplayed() {

        return !driver.findElements(
                By.className("shopping_cart_badge")).isEmpty();
    }
}