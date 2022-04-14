package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ClassifyCuisineTest {
    @Test
    public void ClassifyCuisine() {
        String queryParameter = "Mediterranean";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token"
                ))
                .then()
                .statusCode(200)
                .body("Mediterranean", Matchers.equalTo(queryParameter));
    }
}
