package ecom.bitm.saglabs.objRepo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HamburgerMenuPage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    public HamburgerMenuPage(WebDriver driver) {

        PageFactory.initElements(
                driver, this);
    }

    public void clickMenuButton() {

        menuButton.click();
    }

    public void clickAllItems() {

        allItemsLink.click();
    }

    public void clickAbout() {

        aboutLink.click();
    }

    public void clickLogout() {

        logoutLink.click();
    }

    public void clickResetAppState() {

        resetAppStateLink.click();
    }

    public void closeMenu() {

        closeMenuButton.click();
    }

    public boolean isMenuDisplayed() {

        return closeMenuButton.isDisplayed();
    }
}