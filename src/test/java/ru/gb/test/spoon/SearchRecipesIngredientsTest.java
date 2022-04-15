package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import java.util.Map;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesIngredientsTest {

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"pizza", "Sushi"})
    public void SearchRecipesIngredients_Test(String queryParameter) {
//        String queryParameter = "carrots,tomatoes";
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
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("carrots,tomatoes", Matchers.equalTo(queryParameter));
    }
}
