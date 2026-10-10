package ecom.bitm.saglabs.objRepo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;

    // Locators using the By method
    private By usernameTextField = By.id("user-name");
    private By passwordTextField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("h3[data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Enter username
    public void enterUsername(String username) {
        driver.findElement(usernameTextField).clear();
        driver.findElement(usernameTextField).sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        driver.findElement(passwordTextField).clear();
        driver.findElement(passwordTextField).sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Login with username and password
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Get login error message
    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    // Check whether login button is displayed
    public boolean isLoginButtonDisplayed() {
        return driver.findElement(loginButton).isDisplayed();
    }

    // Check whether password is masked
    public boolean isPasswordMasked() {
        return driver.findElement(passwordTextField)
                .getAttribute("type")
                .equals("password");
    }
}