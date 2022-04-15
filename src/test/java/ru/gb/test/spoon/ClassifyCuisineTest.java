package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ClassifyCuisineTest {
    @Test
    public void ClassifyCuisine() {
        String queryParameter = "Mediterranean";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token"
                ))
                .post("/recipes/cuisine")
                .then()
                .statusCode(200)
                .body("Mediterranean", Matchers.equalTo(queryParameter));
    }
}
