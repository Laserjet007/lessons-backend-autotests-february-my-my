package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ParseIngredientsTest {
    @Test
    public void ParseIngredients() {
        String queryParameter = "en";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "language", queryParameter
                ))
                .then()
                .statusCode(200)
                .body("en", Matchers.equalTo(queryParameter));
    }
}
