package chrometests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.Lending;
import pageobjects.LoginPage;
import pageobjects.PersonalAccountPage;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PersonalAccountTest {
    private WebDriver driver;
    private String email = "harry@yandex.ru";
    private String password = "9999999";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Check login and creating of personal account")
    public void loginFromLending() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillPasswordField(password);
        loginPage.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        Assert.assertTrue("Profile isn't shown, login was not successful", personalAccountPage.isProfileNameFieldDisplayed());
    }

    @Test
    @DisplayName("Check transition from personal account to constructor")
    public void checkTransitionFromPersonalAccountToConstructor(){
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillPasswordField(password);
        loginPage.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.clickConstructorButton();

        Lending lending2 = new Lending(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Assert.assertTrue("Constructor isn't displayed", lending2.isConstructorVisible());
    }

    @Test
    @DisplayName("Check transition from personal account to constructor with using of logo")
    public void checkTransitionFromPersonalAccountToConstructorWithLogo(){
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillPasswordField(password);
        loginPage.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.clickLogoField();

        Lending lending2 = new Lending(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Assert.assertTrue("Constructor isn't displayed", lending2.isConstructorVisible());
    }

    @Test
    @DisplayName("Check logout from personal account")
    public void isAccountLogoutPossible() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        lending.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage.fillPasswordField(password);
        loginPage.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);
        personalAccountPage.clickLogoutButton();

        LoginPage loginPage1 = new LoginPage(driver);

        Assert.assertTrue(loginPage1.isLoginHeaderDisplayed());
    }
}
