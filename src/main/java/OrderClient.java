import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String INGREDIENTS_PATH = "/api/ingredients";
    private static final String ORDER_PATH = "/api/orders";

    public ValidatableResponse getAllIngredients() {
        return given()
                .spec(getSpec())
                .log().all()
                .get(INGREDIENTS_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getOrdersWithAuthorization(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .log().all()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getOrdersWithoutAuthorization() {
        return given()
                .spec(getSpec())
                .log().all()
                .get(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse getAllOrders() {
        return given()
                .spec(getSpec())
                .log().all()
                .get(ORDER_PATH + "all")
                .then()
                .log().all();
    }

    public ValidatableResponse createOrderWithAuthorization(Order order, String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .log().all()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse createOrderWithoutAuthorization(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .log().all()
                .post(ORDER_PATH)
                .then()
                .log().all();
    }
}
