package chrometests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class LoginTest {

    private WebDriver driver;
    private String email = "harry@yandex.ru";
    private String password = "999999999";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Check login from lending")
    public void loginFromLending() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickLendingLoginButton();

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
    @DisplayName("Check login from button in personal account")
    public void loginFromPersonalAccountButton() {
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
    @DisplayName("Check login from registration page")
    public void loginWithLoginButtonFromRegistrationPage() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickLendingLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRegistrationButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        RegistrationPage registrationPage = new RegistrationPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        registrationPage.clickLoginButton();

        LoginPage loginPage1 = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage1.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage1.fillPasswordField(password);
        loginPage1.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        Assert.assertTrue("Profile isn't shown, login was not successful", personalAccountPage.isProfileNameFieldDisplayed());
    }

    @Test
    @DisplayName("Check login from recover password page")
    public void loginWithRecoverPasswordButton() {
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending = new Lending(driver);
        lending.open();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);
        lending.clickLendingLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickRecoverPasswordButton();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);

        RecoverPasswordPage recoverPasswordPage = new RecoverPasswordPage(driver);
        recoverPasswordPage.clickLoginButtonOnRecoverPasswordPage();
        driver.manage().timeouts().implicitlyWait(7, SECONDS);

        LoginPage loginPage1 = new LoginPage(driver);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage1.fillEmailField(email);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        loginPage1.fillPasswordField(password);
        loginPage1.clickLoginButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        Lending lending1 = new Lending(driver);
        lending1.clickPersonalAccountButton();
        driver.manage().timeouts().implicitlyWait(5, SECONDS);

        PersonalAccountPage personalAccountPage = new PersonalAccountPage(driver);

        Assert.assertTrue("Profile isn't shown, login was not successful", personalAccountPage.isProfileNameFieldDisplayed());
    }
}
