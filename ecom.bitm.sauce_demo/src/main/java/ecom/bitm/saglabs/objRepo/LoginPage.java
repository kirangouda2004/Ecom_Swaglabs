package ecom.bitm.saglabs.objRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    //private WebDriver driver;

    // Locators using @FindBy
    @FindBy(id = "user-name")
    private WebElement usernameTextField;

    @FindBy(id = "password")
    private WebElement passwordTextField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    // Constructor
    public LoginPage(WebDriver driver) {
        //this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Enter username
    public void enterUsername(String username) {
        usernameTextField.clear();
        usernameTextField.sendKeys(username);
    }

    // Enter password
    public void enterPassword(String password) {
        passwordTextField.clear();
        passwordTextField.sendKeys(password);
    }

    // Click login button
    public void clickLogin() {
        loginButton.click();
    }

    // Login with username and password
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // Get login error message
    public String getErrorMessage() {
        return errorMessage.getText();
    }

    // Check whether login button is displayed
    public boolean isLoginButtonDisplayed() {
        return loginButton.isDisplayed();
    }

    // Check whether password is masked
    public boolean isPasswordMasked() {
        return passwordTextField
                .getAttribute("type")
                .equals("password");
    }
}
