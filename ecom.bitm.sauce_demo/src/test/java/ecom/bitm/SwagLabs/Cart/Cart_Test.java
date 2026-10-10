package ecom.bitm.SwagLabs.Cart;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecom.bitm.saglabs.objRepo.CartPage;
import ecom.bitm.saglabs.objRepo.LoginPage;
import ecom.bitm.saglabs.objRepo.ProductPage;
import ecom.bitm.swaglabs.baseClass.BaseClass;
import ecom.bitm.swaglabs.utility.ExcelUtility;

public class Cart_Test extends BaseClass {

    private static final String FILE =
            "src/test/resources/TestData.xlsx";

    // TC_ID01 - Functional
    @Test(groups = {"functional"})
    public void verifyProductInCart() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CartData");

        String username =
                excel.getData("TC_ID01", "Username");

        String password =
                excel.getData("TC_ID01", "Password");

        String productName =
                excel.getData("TC_ID01", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);
        productPage.clickCart();

        Assert.assertTrue(
                cartPage.isCartPageDisplayed(),
                "Cart page is not displayed");

        Assert.assertTrue(
                cartPage.isProductPresent(productName),
                "Product is not displayed in cart");

        excel.closeWorkbook();
    }

    // TC_ID02 - Functional
    @Test(groups = {"functional"})
    public void removeProductFromCart() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CartData");

        String username =
                excel.getData("TC_ID02", "Username");

        String password =
                excel.getData("TC_ID02", "Password");

        String productName =
                excel.getData("TC_ID02", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);
        productPage.clickCart();

        Assert.assertTrue(
        		cartPage.isProductPresent(productName),
                "Product is not displayed in cart");

        cartPage.removeProduct(productName);

        Assert.assertFalse(
        		cartPage.isProductPresent(productName),
                "Product was not removed from cart");

        excel.closeWorkbook();
    }

    // TC_ID03 - Integration
    @Test(groups = {"integration"})
    public void continueShopping() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CartData");

        String username =
                excel.getData("TC_ID03", "Username");

        String password =
                excel.getData("TC_ID03", "Password");

        String productName =
                excel.getData("TC_ID03", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);
        productPage.clickCart();

        cartPage.clickContinueShopping();

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID04 - System
    @Test(groups = {"system"})
    public void cartToCheckout() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "CartData");

        String username =
                excel.getData("TC_ID04", "Username");

        String password =
                excel.getData("TC_ID04", "Password");

        String productName =
                excel.getData("TC_ID04", "ProductName");

        LoginPage loginPage = new LoginPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);
        productPage.clickCart();

        Assert.assertTrue(
                cartPage.isCartPageDisplayed(),
                "Cart page is not displayed");

        cartPage.clickCheckout();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("checkout"),
                "Checkout page is not displayed");

        excel.closeWorkbook();
    }
}