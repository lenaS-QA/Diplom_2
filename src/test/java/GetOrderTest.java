import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;


public class GetOrderTest {

    private UserClient userClient;
    private User user;

    private String accessToken;
    private OrderClient orderClient;
    private Order order;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getDefault();
        orderClient = new OrderClient();
        order = new Order();
    }

    private void fillListIngredients() {
        ValidatableResponse response = orderClient.getAllIngredients();
        List<String> list = response.extract().path("data._id");
        List<String> ingredients = order.getIngredients();
        ingredients.add(list.get(1));
        ingredients.add(list.get(5));
        ingredients.add(list.get(2));
    }

    @Test
    @DisplayName("Get orders with authorization")
    public void getOrdersWithAuthorization() {
        fillListIngredients();
        ValidatableResponse responseCreate = userClient.create(user);
        String accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse responseOrder = orderClient.getOrdersWithAuthorization(accessToken);
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isGetOrders = responseOrder.extract().path("success");

        Assert.assertEquals(200, statusCodeResponseOrder);
        Assert.assertTrue(isGetOrders);
    }

    @Test
    @DisplayName("Get orders without authorization")
    public void getOrdersWithoutAuthorization() {
        fillListIngredients();
        ValidatableResponse responseOrder = orderClient.getOrdersWithoutAuthorization();
        int statusCodeResponseOrder = responseOrder.extract().statusCode();
        boolean isGetOrders = responseOrder.extract().path("success");
        String message = responseOrder.extract().path("message");

        Assert.assertEquals(401, statusCodeResponseOrder);
        Assert.assertFalse(isGetOrders);
        Assert.assertEquals("You should be authorised", message);
    }
    @After
    public void cleanUp() {
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        userClient.delete(accessToken);
    }
}
