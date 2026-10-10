package ecom.bitm.saglabs.objRepo;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private WebDriverWait wait;

    @FindBy(xpath = "//span[normalize-space()='Checkout: Your Information']")
    private WebElement checkoutTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameTextField;

    @FindBy(id = "last-name")
    private WebElement lastNameTextField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeTextField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement orderCompleteMessage;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    public CheckoutPage(WebDriver driver) {

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30));

        PageFactory.initElements(driver, this);
    }

    public boolean isCheckoutPageDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOf(checkoutTitle)
        ).isDisplayed();
    }

    public void enterFirstName(String firstName) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(
                        firstNameTextField));

        field.clear();
        field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(
                        lastNameTextField));

        field.clear();
        field.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {

        WebElement field = wait.until(
                ExpectedConditions.visibilityOf(
                        postalCodeTextField));

        field.clear();
        field.sendKeys(postalCode);
    }

    public void enterCheckoutDetails(
            String firstName,
            String lastName,
            String postalCode) {

        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        continueButton)).click();
    }

    public void clickCancel() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        cancelButton)).click();

        wait.until(
                ExpectedConditions.urlContains("cart"));
    }

    public void clickFinish() {

        wait.until(
                ExpectedConditions.elementToBeClickable(
                        finishButton)).click();

        wait.until(
                ExpectedConditions.visibilityOf(
                        orderCompleteMessage));
    }

    public boolean isOrderCompleteDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        orderCompleteMessage)
        ).isDisplayed();
    }

    public String getOrderCompleteMessage() {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        orderCompleteMessage)
        ).getText();
    }

    public String getErrorMessage() {

        return wait.until(
                ExpectedConditions.visibilityOf(
                        errorMessage)
        ).getText();
    }
}