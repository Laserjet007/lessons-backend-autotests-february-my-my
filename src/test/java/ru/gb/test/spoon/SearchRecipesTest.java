package ru.gb.test.spoon;

//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesTest {
    @Test
        public void SearchRecipes() {
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
                    .then()
                    .statusCode(200)
                    .body("ingredients", Matchers.equalTo(queryParameter));
        }
    }
