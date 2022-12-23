import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LoginUserTest {
    private UserClient userClient;
    private User user;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        userClient.create(user);
    }

    @Test
    @DisplayName("Check status code of user login")
    public void userLoginStatus() {
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("StatusCode should be 200", 200, statusCode);
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
