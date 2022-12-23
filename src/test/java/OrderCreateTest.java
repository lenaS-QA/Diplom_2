import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import static org.hamcrest.Matchers.notNullValue;

public class OrderCreateTest {
    private User user;
    private Order order;
    private UserClient userClient;
    private OrderClient orderClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = UserGenerator.getDefault();
        order = new Order();
        userClient = new UserClient();
        orderClient = new OrderClient();
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
    @DisplayName("Create order with authorization")
    public void createOrderWithAuthorization() {
        fillListIngredients();
        ValidatableResponse responseCreate = userClient.create(user);
        String accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse response = orderClient.createOrderWithAuthorization(order, accessToken);
        int statusCode = response.extract().statusCode();
        boolean isCreate = response.extract().path("success");
        int orderNumber = response.extract().path("order.number");
        String orderId = response.extract().path("order._id");

        Assert.assertEquals(200, statusCode);
        Assert.assertEquals(true, isCreate);
        Assert.assertEquals(notNullValue(), orderNumber);
        Assert.assertNotNull(orderId);
    }

    @Test
    @DisplayName("Create order without authorization")
    public void CreateOrderWithoutAuthorization() {
        fillListIngredients();
        ValidatableResponse response = orderClient.createOrderWithoutAuthorization(order);
        int statusCode = response.extract().statusCode();
        boolean isCreate = response.extract().path("success");
        int orderNumber = response.extract().path("order.number");

        Assert.assertEquals(200, statusCode);
        Assert.assertEquals(true, isCreate);
        Assert.assertNotNull(orderNumber);
    }

    @Test
    @DisplayName("Create order without ingredients")
    public void orderCreateWithoutAuthorizationAndIngredients() {
        ValidatableResponse responseCreate = userClient.create(user);
        String accessToken = responseCreate.extract().path("accessToken");
        ValidatableResponse response = orderClient.createOrderWithAuthorization(order, accessToken);
        int statusCode = response.extract().statusCode();
        String message = response.extract().path("message");
        boolean isCreate = response.extract().path("success");

        Assert.assertEquals(400, statusCode);
        Assert.assertEquals("Ingredient ids must be provided", message);
        Assert.assertFalse(isCreate);
    }

        @Test
        @DisplayName("Create order without authorization and change hash ingredient")
        public void orderCreateWithoutAuthorizationAndChangeHashIngredient() {
            ValidatableResponse responseCreate = userClient.create(user);
            String accessToken = responseCreate.extract().path("accessToken");
            ValidatableResponse response = orderClient.getAllIngredients();
            List<String> list = response.extract().path("data._id");
            List<String> ingredients = order.getIngredients();
            ingredients.add(list.get(0));
            ingredients.add(list.get(5).replaceAll("a", "l"));
            ingredients.add(list.get(0));
            response = orderClient.createOrderWithoutAuthorization(order);
            int statusCode = response.extract().statusCode();

            Assert.assertEquals(500, statusCode);
        }

    @After
    public void cleanUp() {
        ValidatableResponse responseLogin = userClient.login(Credentials.from(user));
        accessToken = responseLogin.extract().path("accessToken");
        userClient.delete(accessToken);
    }
}