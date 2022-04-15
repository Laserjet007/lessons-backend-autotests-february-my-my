package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class IngredientsWidgetTest {

    @Test
    public void IngredientsWidget() {
        String queryParameter = "en";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token"
                ))
                .post("/recipes/visualizeIngredients")
                .then()
                .statusCode(200)
                .body("language", Matchers.equalTo(queryParameter));
    }
}
