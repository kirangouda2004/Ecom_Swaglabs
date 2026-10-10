package ecom.bitm.SwagLabs.Checkout;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecom.bitm.saglabs.objRepo.CartPage;
import ecom.bitm.saglabs.objRepo.CheckoutPage;
import ecom.bitm.saglabs.objRepo.LoginPage;
import ecom.bitm.saglabs.objRepo.ProductPage;
import ecom.bitm.swaglabs.baseClass.BaseClass;
import ecom.bitm.swaglabs.utility.ExcelUtility;

public class Checkout_Test extends BaseClass {

    private static final String FILE =
            "src/test/resources/TestData.xlsx";

    // TC_ID01 - Functional
    @Test(groups = {"functional"})
    public void checkoutDetails() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CheckoutData");

        String username =
                excel.getData("TC_ID01", "Username");
        String password =
                excel.getData("TC_ID01", "Password");
        String productName =
                excel.getData("TC_ID01", "ProductName");
        String firstName =
                excel.getData("TC_ID01", "FirstName");
        String lastName =
                excel.getData("TC_ID01", "LastName");
        String postalCode =
                excel.getData("TC_ID01", "PostalCode");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(username, password);
        productPage.addProductToCart(productName);
        productPage.clickCart();
        cartPage.clickCheckout();

        Assert.assertTrue(
                checkoutPage.isCheckoutPageDisplayed(),
                "Checkout page is not displayed");

        checkoutPage.enterCheckoutDetails(
                firstName, lastName, postalCode);

        checkoutPage.clickContinue();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-two"),
                "Checkout overview page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID02 - Functional
    @Test(groups = {"functional"})
    public void completeOrder() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CheckoutData");

        String username =
                excel.getData("TC_ID02", "Username");
        String password =
                excel.getData("TC_ID02", "Password");
        String productName =
                excel.getData("TC_ID02", "ProductName");
        String firstName =
                excel.getData("TC_ID02", "FirstName");
        String lastName =
                excel.getData("TC_ID02", "LastName");
        String postalCode =
                excel.getData("TC_ID02", "PostalCode");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(username, password);
        productPage.addProductToCart(productName);
        productPage.clickCart();
        cartPage.clickCheckout();

        checkoutPage.enterCheckoutDetails(
                firstName, lastName, postalCode);

        checkoutPage.clickContinue();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-two"),
                "Checkout overview page is not displayed");

        checkoutPage.clickFinish();

        Assert.assertTrue(
                checkoutPage.isOrderCompleteDisplayed(),
                "Order completion message is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID03 - Integration
    @Test(groups = {"integration"})
    public void cancelCheckout() throws IOException {

        ExcelUtility excel = new ExcelUtility(FILE, "CheckoutData");

        String username = excel.getData("TC_ID03", "Username");
        String password = excel.getData("TC_ID03", "Password");
        String productName = excel.getData("TC_ID03", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);
        productPage.clickCart();

        Assert.assertTrue(
            cartPage.isCartPageDisplayed(),
            "Cart page is not displayed"
        );

        cartPage.clickCheckout();

        Assert.assertTrue(
            checkoutPage.isCheckoutPageDisplayed(),
            "Checkout page is not displayed"
        );

        checkoutPage.clickCancel();

        Assert.assertTrue(
            cartPage.isCartPageDisplayed(),
            "User is not returned to Cart page"
        );

        excel.closeWorkbook();
    }
    // TC_ID04 - Negative
    @Test(groups = {"negative"})
    public void emptyCheckoutDetails() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CheckoutData");

        String username =
                excel.getData("TC_ID04", "Username");
        String password =
                excel.getData("TC_ID04", "Password");
        String productName =
                excel.getData("TC_ID04", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        loginPage.login(username, password);
        productPage.addProductToCart(productName);
        productPage.clickCart();
        cartPage.clickCheckout();

        checkoutPage.clickContinue();

        checkoutPage.clickContinue();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout-step-one"),
                "Checkout should not continue without required details");

        Assert.assertTrue(
                checkoutPage.getErrorMessage()
                        .contains("First Name is required"),
                "Required field error is not displayed");

        excel.closeWorkbook();
    }
}