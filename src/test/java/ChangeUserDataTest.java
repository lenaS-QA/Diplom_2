import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ChangeUserDataTest {
    private UserClient userClient;
    private User user;
    private User user1;
    private String accessToken;
    private String accessToken1;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        user1 = new User("tom25@yandex.ru", "5555555", "Tomas");
    }

    @Test
    @DisplayName("Change user data with login")
    public void changeEmailAndNameWithLogin() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        userClient.login(Credentials.from(user));
        //User user1 = new User("tom22@yandex.ru", "55555", "Tomas");
        ValidatableResponse responseChangeData = userClient.changeData(user1, accessToken);
        String actualEmail = responseChangeData.extract().path("user.email");
        String actualName = responseChangeData.extract().path("user.name");
        ValidatableResponse responseLoginWithNewData = userClient.login(Credentials.from(user1));
        int statusCodeResponseChange = responseChangeData.extract().statusCode();
        int statusCodeResponseLogin = responseLoginWithNewData.extract().statusCode();
        Assert.assertEquals(200, statusCodeResponseChange);
        Assert.assertEquals("tom25@yandex.ru", actualEmail);
        Assert.assertEquals("Tomas", actualName);
        Assert.assertEquals(200, statusCodeResponseLogin);
        ValidatableResponse responseLogin2 = userClient.login(Credentials.from(user1));
        accessToken1 = responseLogin2.extract().path("accessToken");
        userClient.delete(accessToken1);
    }
    @Test
    @DisplayName("Change user data without login")
    public void changeEmailAndNameWithoutLogin() {
        userClient.create(user);
        //User user1 = new User("tom2@yandex.ru", "55555", "Tomas");
        ValidatableResponse responseChangeDataWithoutToken = userClient.changeDataWithoutToken(user1);
        boolean isEmailChanged = responseChangeDataWithoutToken.extract().path("success");
        String message = responseChangeDataWithoutToken.extract().path("message");
        ValidatableResponse responseLoginWithNewData = userClient.login(Credentials.from(user1));
        int statusCodeResponseChange = responseChangeDataWithoutToken.extract().statusCode();
        int statusCodeResponseLogin = responseLoginWithNewData.extract().statusCode();
        Assert.assertEquals(401, statusCodeResponseChange);
        Assert.assertFalse(isEmailChanged);
        Assert.assertEquals("You should be authorised", message);
        Assert.assertEquals(401, statusCodeResponseLogin);
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseLogin1 = userClient.login(Credentials.from(user));
        accessToken = responseLogin1.extract().path("accessToken");
        userClient.delete(accessToken);
    }
}

