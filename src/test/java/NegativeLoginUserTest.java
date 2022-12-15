import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NegativeLoginUserTest {

        User user = UserGenerator.getDefault();
        UserClient userClient = new UserClient();
        private String accessToken;


    @Test
    @DisplayName("Check that login user with wrong email is impossible")
    public void loginWithWrongEmail() {
        userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(new Credentials("wrong", user.getPassword()));
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("You shouldn't can login with wrong login, but you can", 401, statusCode);
    }
    @Test
    @DisplayName("Check that login user with wrong password is impossible")
    public void loginWithWrongPassword() {
        userClient.create(user);
        ValidatableResponse responseLogin = userClient.login(new Credentials(user.getEmail(), "wrong"));
        int statusCode = responseLogin.extract().statusCode();
        Assert.assertEquals("You shouldn't can login with wrong password, but you can", 401, statusCode);
    }
    @After
    public void cleanUp() {
        ValidatableResponse responseLogin1 = userClient.login(Credentials.from(user));
        accessToken = responseLogin1.extract().path("accessToken");
        userClient.delete(accessToken);
    }
    }
