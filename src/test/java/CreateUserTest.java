import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CreateUserTest {
    private UserClient userClient;
    private User user;
    private Credentials credentials;
    private String accessToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
    }

    @Test
    @DisplayName("Check status code of user creation")
    public void userCanBeCreated () {
        ValidatableResponse responseCreate = userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        int statusCode = responseCreate.extract().statusCode();
        Assert.assertEquals("StatusCode should be 200", 200, statusCode);
    }

    @Test
    @DisplayName("Check that body of response has success:true")
    public void responseMessageIsTrue() {
        ValidatableResponse responseCreate = userClient.create(user);
        accessToken = responseCreate.extract().path("accessToken");
        boolean responseMessage = responseCreate.extract().path("success");
        Assert.assertTrue(responseMessage);
    }

    @After
    public void cleanUp() {
        userClient.delete(accessToken);
    }
}
