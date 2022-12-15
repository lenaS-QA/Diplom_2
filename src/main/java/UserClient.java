import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String CREATE_USER_PATH = "api/auth/register";
    private static final String DELETE_USER_PATH = "api/auth/user";
    private static final String LOGIN_USER_PATH = "api/auth/login";
    private static final String CHANGE_DATA_PATH = "api/auth/user";


    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(user)
                .when()
                .post(CREATE_USER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse login(Credentials credentials) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(LOGIN_USER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse delete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .when()
                .delete(DELETE_USER_PATH)
                .then()
                .log().all();
    }

    public ValidatableResponse changeData(User user, String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .spec(getSpec())
                .body(user)
                .when()
                .patch(CHANGE_DATA_PATH)
                .then();
    }

    public ValidatableResponse changeDataWithoutToken(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .patch(CHANGE_DATA_PATH)
                .then();
    }
}
