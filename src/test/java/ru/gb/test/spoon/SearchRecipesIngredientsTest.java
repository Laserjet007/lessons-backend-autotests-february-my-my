package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesIngredientsTest {
    @Test
    public void SearchRecipesIngredients() {
        String queryParameter = "carrots,tomatoes";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "ingredients", queryParameter,
                        "number", "10",
                        "limitLicense", "true",
                        "ranking", "1",
                        "ignorePantry", "false"
                ))
                .get("/recipes/findByIngredients")
                .then()
                .statusCode(200)
                .body("carrots,tomatoes", Matchers.equalTo(queryParameter));
    }
}
