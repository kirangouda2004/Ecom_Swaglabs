package ecom.bitm.SwagLabs.Product;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecom.bitm.saglabs.objRepo.LoginPage;
import ecom.bitm.saglabs.objRepo.ProductPage;
import ecom.bitm.swaglabs.baseClass.BaseClass;
import ecom.bitm.swaglabs.utility.ExcelUtility;

public class Product_Test extends BaseClass {

    private static final String FILE =
            "src/test/resources/TestData.xlsx";

    // TC_ID01 - Functional
    @Test(groups = {"functional"})
    public void sortProducts() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ProductData");

        String username =
                excel.getData("TC_ID01", "Username");

        String password =
                excel.getData("TC_ID01", "Password");

        String sortOption =
                excel.getData("TC_ID01", "SortOption");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        productPage.sortProductsByVisibleText(sortOption);

        Assert.assertTrue(
                productPage.getProductCount() > 0,
                "Products are not displayed");

        excel.closeWorkbook();
    }

    // TC_ID02 - Functional
    @Test(groups = {"functional"})
    public void addProductToCart() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ProductData");

        String username =
                excel.getData("TC_ID02", "Username");

        String password =
                excel.getData("TC_ID02", "Password");

        String productName =
                excel.getData("TC_ID02", "ProductName");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);

        Assert.assertTrue(
                productPage.isCartBadgeDisplayed(),
                "Cart badge is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID03 - Integration
    @Test(groups = {"integration"})
    public void productAndCartIntegration() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ProductData");

        String username =
                excel.getData("TC_ID03", "Username");

        String password =
                excel.getData("TC_ID03", "Password");

        String productName =
                excel.getData("TC_ID03", "ProductName");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        productPage.addProductToCart(productName);

        Assert.assertEquals(
                productPage.getCartItemCount(),
                "1",
                "Product is not added to cart");

        productPage.clickCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "Cart page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID04 - System
    @Test(groups = {"system"})
    public void productSystemFlow() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ProductData");

        String username =
                excel.getData("TC_ID04", "Username");

        String password =
                excel.getData("TC_ID04", "Password");

        String productName =
                excel.getData("TC_ID04", "ProductName");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        productPage.addProductToCart(productName);

        Assert.assertEquals(
                productPage.getCartItemCount(),
                "1",
                "Product is not added to cart");

        productPage.clickCart();

        Assert.assertTrue(
                driver.getCurrentUrl().contains("cart"),
                "Cart page is not displayed");

        excel.closeWorkbook();
    }
}