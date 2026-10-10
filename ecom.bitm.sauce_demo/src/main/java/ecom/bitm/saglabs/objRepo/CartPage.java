package ecom.bitm.saglabs.objRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    //private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//span[normalize-space()='Your Cart']")
    private WebElement cartTitle;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartProductNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartProductPrices;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartQuantities;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30));

        PageFactory.initElements(driver, this);
    }

    public boolean isCartPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOf(cartTitle)
        ).isDisplayed();
    }

    public String getCartPageTitle() {

        return wait.until(
                ExpectedConditions.visibilityOf(cartTitle)
        ).getText();
    }

    public int getCartItemCount() {

        wait.until(
                ExpectedConditions.visibilityOf(cartTitle));

        return cartProductNames.size();
    }

    public boolean isProductPresent(String productName) {

        String name = productName.trim();

        wait.until(
                ExpectedConditions.visibilityOf(cartTitle));

        for (WebElement product : cartProductNames) {

            if (product.getText()
                    .trim()
                    .equalsIgnoreCase(name)) {

                return true;
            }
        }

        return false;
    }

    public void removeProduct(String productName) {

        String name = productName.trim();

        for (WebElement product : cartProductNames) {

            if (product.getText()
                    .trim()
                    .equalsIgnoreCase(name)) {

                WebElement productContainer =
                        product.findElement(
                                By.xpath(
                                        "./ancestor::div[contains(@class,'cart_item')]"
                        ));

                WebElement removeButton =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        productContainer.findElement(
                                                By.tagName("button")
                                        )
                                )
                        );

                removeButton.click();

                return;
            }
        }

        throw new IllegalArgumentException(
                "Product not found in cart: " + name);
    }

    public void clickContinueShopping() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueShoppingButton)).click();

        wait.until(
                ExpectedConditions.urlContains("inventory"));
    }

    public void clickCheckout() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        checkoutButton)).click();

        wait.until(
                ExpectedConditions.urlContains("checkout-step-one"));
    }

    public String getProductPrice(String productName) {

        String name = productName.trim();

        for (int i = 0;
             i < cartProductNames.size();
             i++) {

            if (cartProductNames.get(i)
                    .getText()
                    .trim()
                    .equalsIgnoreCase(name)) {

                return cartProductPrices
                        .get(i)
                        .getText();
            }
        }

        return null;
    }

    public String getProductQuantity(String productName) {

        String name = productName.trim();

        for (int i = 0;
             i < cartProductNames.size();
             i++) {

            if (cartProductNames.get(i)
                    .getText()
                    .trim()
                    .equalsIgnoreCase(name)) {

                return cartQuantities
                        .get(i)
                        .getText();
            }
        }

        return null;
    }
}