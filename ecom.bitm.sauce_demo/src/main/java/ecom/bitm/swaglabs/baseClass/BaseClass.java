package ecom.bitm.swaglabs.baseClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseClass {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Properties properties;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        System.out.println("===== Swag Labs Test Suite Started =====");
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        System.out.println("===== Test Execution Started =====");
    }

    @BeforeClass(alwaysRun = true)
    public void beforeClass() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/CommonData.properties");
        properties.load(fis);
        fis.close();

        System.out.println("===== Test Class Started =====");
    }


@Parameters("browser")
@BeforeMethod(alwaysRun = true)
public void setUp(@Optional("chrome") String browserParameter)
        throws IOException {

    String browser = browserParameter;
    
    if (browser == null || browser.trim().isEmpty()) {
        browser = properties.getProperty("browser", "chrome");
    }

    browser = browser.trim().toLowerCase();
    String url = properties.getProperty("url");

    if (browser.equals("chrome")) {
    	final Map<String, Object> chromePrefs = new HashMap<>();
    	chromePrefs.put("credentials_enable_service", false);
    	chromePrefs.put("profile.password_manager_enabled", false);
    	chromePrefs.put("profile.password_manager_leak_detection", false); // <======== This is the important one

    	final ChromeOptions chromeOptions = new ChromeOptions();
    	chromeOptions.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(chromeOptions);
        
    } else if (browser.equals("edge")) {
        driver = new EdgeDriver();
    } else if (browser.equals("firefox")) {
        driver = new FirefoxDriver();
    } else {
        throw new IllegalArgumentException(
                "Invalid browser: " + browser);
    }

    driver.manage().window().maximize();

    driver.manage().timeouts()
            .pageLoadTimeout(Duration.ofSeconds(30));

    driver.manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(10));

    wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(20));

    driver.get(url);
}


//    @AfterMethod(alwaysRun = true)
//    public void tearDown(ITestResult result) {
//
//        if (result.getStatus() == ITestResult.FAILURE) {
//
//            String[] groups =
//                    result.getMethod().getGroups();
//
//            for (String group : groups) {
//
//                if (group.equalsIgnoreCase("negative")) {
//                    takeScreenshot(result.getName());
//                    break;
//                }
//            }
//        }
//
//        if (driver != null) {
//            driver.quit();
//        }
//    }
@AfterMethod(alwaysRun = true)
public void tearDown(ITestResult result) {

    if (driver != null) {
        String[] groups = result.getMethod().getGroups();

        for (String group : groups) {
            if (group.equalsIgnoreCase("negative")) {
                takeScreenshot(result.getName());
                break;
            }
        }

        driver.quit();
        driver = null;
    }
}

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        System.out.println("===== Test Class Completed =====");
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        System.out.println("===== Test Execution Completed =====");
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        System.out.println("===== Swag Labs Test Suite Completed =====");
    }

    protected void waitForElement(WebElement element) {
        wait.until(
                ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementClickable(WebElement element) {
        wait.until(
                ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitForElementLocated(By locator) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(WebElement element) {
        waitForElementClickable(element);
        element.click();
    }

    protected void enterText(
            WebElement element,
            String text) {

        waitForElement(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForElement(element);
        return element.getText();
    }

    protected void selectByVisibleText(
            WebElement element,
            String text) {

        waitForElement(element);

        Select select = new Select(element);

        select.selectByVisibleText(text);
    }

    protected void selectByValue(
            WebElement element,
            String value) {

        waitForElement(element);

        Select select = new Select(element);

        select.selectByValue(value);
    }

    protected void selectByIndex(
            WebElement element,
            int index) {

        waitForElement(element);

        Select select = new Select(element);

        select.selectByIndex(index);
    }

    protected void mouseHover(WebElement element) {

        waitForElement(element);

        Actions actions = new Actions(driver);

        actions.moveToElement(element).perform();
    }

    protected void doubleClick(WebElement element) {

        waitForElement(element);

        Actions actions = new Actions(driver);

        actions.doubleClick(element).perform();
    }

    protected void rightClick(WebElement element) {

        waitForElement(element);

        Actions actions = new Actions(driver);

        actions.contextClick(element).perform();
    }

    protected void acceptAlert() {

        wait.until(
                ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        alert.accept();
    }

    protected void dismissAlert() {

        wait.until(
                ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        alert.dismiss();
    }

    protected String getAlertText() {

        wait.until(
                ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();

        return alert.getText();
    }

    protected void switchToFrame(WebElement frame) {

        wait.until(
                ExpectedConditions
                        .frameToBeAvailableAndSwitchToIt(frame));
    }

    protected void switchToFrame(int frameIndex) {
        driver.switchTo().frame(frameIndex);
    }

    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }

    protected void goBack() {
        driver.navigate().back();
    }

    protected void goForward() {
        driver.navigate().forward();
    }

    protected void refreshPage() {
        driver.navigate().refresh();
    }

    protected void scrollToElement(WebElement element) {

        org.openqa.selenium.JavascriptExecutor js =
                (org.openqa.selenium.JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].scrollIntoView(true);",
                element);
    }

    protected void clickUsingJavaScript(WebElement element) {

        org.openqa.selenium.JavascriptExecutor js =
                (org.openqa.selenium.JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element);
    }
    
    protected void takeScreenshot(String fileName) {
        try {
            File screenshotFolder = new File("Screenshot");

            if (!screenshotFolder.exists()) {
                screenshotFolder.mkdirs();
            }

            File destination = new File(
                    screenshotFolder,
                    fileName + ".png"
            );

            File screenshot = ((org.openqa.selenium.TakesScreenshot) driver)
                    .getScreenshotAs(org.openqa.selenium.OutputType.FILE);

            java.nio.file.Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Screenshot saved: "
                    + destination.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}

//    protected void takeScreenshot(String fileName) {
//
//        File screenshot =
//                ((org.openqa.selenium.TakesScreenshot) driver)
//                        .getScreenshotAs(
//                                org.openqa.selenium.OutputType.FILE);
//
//        File destination =
//                new File(
//                        "Screenshot/" + fileName + ".png");
//
//        screenshot.renameTo(destination);
//    }
//}