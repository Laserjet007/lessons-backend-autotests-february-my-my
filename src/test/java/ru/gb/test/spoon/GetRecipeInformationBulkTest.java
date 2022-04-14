package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class GetRecipeInformationBulkTest {
    @Test
    public void GetRecipeInformationBulk() {
        String queryParameter = "715538,716429";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "ids", queryParameter,
                        "includeNutrition", "false"
                ))

//                    .get(baseUrl + "/recipes/findByIngredients/ingredients")
                .then()
                .statusCode(200)
                .body("715538,716429", Matchers.equalTo(queryParameter));
    }
}
