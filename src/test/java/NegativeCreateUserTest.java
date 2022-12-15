import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

public class NegativeCreateUserTest {

    @Test
    @DisplayName("Check status code of double user creation")
    public void DoubleUserCreation() {
        User user1 = UserGenerator.getDefault();
        User user2 = UserGenerator.getDefault();
        UserClient userClient = new UserClient();
        userClient.create(user1);
        ValidatableResponse response = userClient.create(user2);
        int statusCode = response.extract().statusCode();
        Assert.assertEquals("You shouldn't can create two identical users, but you can", 403, statusCode);
    }
    @Test
    @DisplayName("Check message of double user creation")
    public void DoubleUserCreationMessage() {
        User user1 = UserGenerator.getDefault();
        User user2 = UserGenerator.getDefault();
        UserClient userClient = new UserClient();
        userClient.create(user1);
        ValidatableResponse response = userClient.create(user2);
        String message = response.extract().path("message");
        Assert.assertEquals("You shouldn't can create two identical users, but you can", "User already exists", message);
    }
    @Test
    @DisplayName("Check message of user creation with existing email")
    public void WithoutLoginCourierCreationMessage() {
        User user = new User(null, "89458", "Lari");
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");
        Assert.assertEquals("The message isn't right", "Email, password and name are required fields", message);
    }
    @Test
    @DisplayName("Check message of user creation with existing password")
    public void WithoutPasswordCourierCreationMessage() {
        User user = new User("bezparolya@yandex.ru", null, "Lari");
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");
        Assert.assertEquals("The message isn't right", "Email, password and name are required fields", message);
    }
    @Test
    @DisplayName("Check message of user creation with existing name")
    public void WithoutNameCourierCreationMessage() {
        User user = new User("bezparolya@yandex.ru", "89458", null);
        UserClient userClient = new UserClient();
        ValidatableResponse response = userClient.create(user);
        String message = response.extract().path("message");
        Assert.assertEquals("The message isn't right", "Email, password and name are required fields", message);
    }
}
