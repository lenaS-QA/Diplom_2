package chrometests;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageobjects.LoginPage;
import pageobjects.Lending;
import pageobjects.PersonalAccountPage;
import pageobjects.RegistrationPage;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RegistrationTest {

    private WebDriver driver;
    private String userName = "Harry";
    private String email = "harry@yandex.ru";
    private String password = "99999999";

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    @DisplayName("Check possibility of registration")
    public void CheckThatRegistrationIsPossible() {
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
        registrationPage.fillNameField(userName);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        registrationPage.fillEmailField(email);
        registrationPage.fillPasswordField(password);
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        registrationPage.clickRegistrationButton();

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

        Assert.assertTrue("Profile isn't shown, registration was not successful", personalAccountPage.isProfileNameFieldDisplayed());
    }

    @Test
    @DisplayName("Check message of incorrect password in registration")
    public void checkIncorrectPasswordMessage() {
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
        registrationPage.fillNameField("User");
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        registrationPage.fillEmailField("user@yandex.ru");
        registrationPage.fillPasswordField("55555");
        driver.manage().timeouts().implicitlyWait(5, SECONDS);
        registrationPage.clickRegistrationButton();

        Assert.assertTrue(registrationPage.isIncorrectPasswordMessageDisplayed());
    }
}
