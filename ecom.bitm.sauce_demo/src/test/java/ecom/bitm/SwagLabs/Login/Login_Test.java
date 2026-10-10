package ecom.bitm.SwagLabs.Login;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecom.bitm.saglabs.objRepo.LoginPage;
import ecom.bitm.saglabs.objRepo.ProductPage;
import ecom.bitm.swaglabs.baseClass.BaseClass;
import ecom.bitm.swaglabs.utility.ExcelUtility;

public class Login_Test extends BaseClass {

    private static final String FILE =
            "src/test/resources/TestData.xlsx";

    // TC_ID01 - Functional + Smoke
    @Test(groups = {"functional", "smoke"})
    public void validLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ValidLoginData");

        String username =
                excel.getData("TC_ID01", "Username");

        String password =
                excel.getData("TC_ID01", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID03 - Negative
    @Test(groups = {"negative"})
    public void invalidLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "InvalidLoginData");

        String username =
                excel.getData("TC_ID03", "Username");

        String password =
                excel.getData("TC_ID03", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("Username and password"),
                "Invalid login error is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID04 - Integration
    @Test(groups = {"integration"})
    public void performanceUserLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ValidLoginData");

        String username =
                excel.getData("TC_ID04", "Username");

        String password =
                excel.getData("TC_ID04", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID05 - Integration
    @Test(groups = {"integration"})
    public void errorUserLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ValidLoginData");

        String username =
                excel.getData("TC_ID05", "Username");

        String password =
                excel.getData("TC_ID05", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID07 - System
    @Test(groups = {"system"})
    public void visualUserLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "ValidLoginData");

        String username =
                excel.getData("TC_ID07", "Username");

        String password =
                excel.getData("TC_ID07", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        ProductPage productPage =
                new ProductPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                productPage.isProductsPageDisplayed(),
                "Products page is not displayed");

        Assert.assertEquals(
                productPage.getProductsPageTitle(),
                "Products",
                "Products title is incorrect");

        excel.closeWorkbook();
    }

    // TC_ID09 - Negative
    @Test(groups = {"negative"})
    public void lockedUserLogin() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "InvalidLoginData");

        String username =
                excel.getData("TC_ID09", "Username");

        String password =
                excel.getData("TC_ID09", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("locked out"),
                "Locked user error is not displayed");

        excel.closeWorkbook();
    }

    // TC_ID13 - Negative
    @Test(groups = {"negative"})
    public void invalidPassword() throws IOException {

        ExcelUtility excel =
                new ExcelUtility(FILE, "InvalidLoginData");

        String username =
                excel.getData("TC_ID13", "Username");

        String password =
                excel.getData("TC_ID13", "Password");

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.login(username, password);

        Assert.assertTrue(
                loginPage.getErrorMessage()
                        .contains("Username and password"),
                "Wrong password error is not displayed");

        excel.closeWorkbook();
    }
}

