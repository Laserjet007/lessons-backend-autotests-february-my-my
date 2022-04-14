package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AnalyzeRecipeSearchQueryTest {
    @Test
    public void AnalyzeRecipeSearchQuery() {
        String queryParameter = "salmon with fusilli and no nuts";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "q", queryParameter
                ))
                .then()
                .statusCode(200)
                .body("q", Matchers.equalTo(queryParameter));
    }
}
