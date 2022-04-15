package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesByNutrientsTest {
    @Test
    public void SearchRecipesByNutrients() {
        String queryParameter = "10";
        given()
        .when()
        .queryParams(Map.of("apiKey", "token",
                        "maxCalories", queryParameter,
                        "maxCarbs", "10",
                        "maxProtein", "10",
                        "minCalories", "50"
                ))

//                     .get(baseUrl + "/recipes/findByIngredients/ingredients")
        .then()
        .statusCode(200)
        .body("minCarbs", Matchers.equalTo(queryParameter));
    }
}