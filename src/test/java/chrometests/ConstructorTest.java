package chrometests;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.Lending;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ConstructorTest {
    private WebDriver driver;
    private String email = "harry@yandex.ru";
    private String password = "9999999";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Check transition to sauces in constructor")
    public void clickHeaderToSauceTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickSauceAnchor();

        Assert.assertTrue(lending.isSauceHeaderVisible());
    }

    @Test
    @DisplayName("Check transition to bugs in constructor")
    public void clickHeaderToBugsTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickSauceAnchor();
        lending.clickBugsAnchor();

        Assert.assertTrue(lending.isBugsHeaderVisible());
    }

    @Test
    @DisplayName("Check transition to fillings in constructor")
    public void clickHeaderToFillingsTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickFillingsAnchor();

        Assert.assertTrue(lending.isFillingsHeaderVisible());
    }

    @Test
    @DisplayName("Check transition to sauces in constructor with using scroll")
    public void scrollToSauceTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.scrollToSauce();

        Assert.assertTrue(lending.isSauceHeaderVisible());
    }

    @Test
    @DisplayName("Check transition to bugs in constructor with using scroll")
    public void scrollToBugsTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.scrollToSauce();
        lending.scrollToBugs();

        Assert.assertTrue(lending.isBugsHeaderVisible());
    }

    @Test
    @DisplayName("Check transition to fillings in constructor with using scroll")
    public void scrollToFillingsTransition() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.scrollToFillings();

        Assert.assertTrue(lending.isFillingsHeaderVisible());
    }
}
