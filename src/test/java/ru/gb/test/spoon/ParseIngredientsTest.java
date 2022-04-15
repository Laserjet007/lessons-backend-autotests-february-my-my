package ru.gb.test.spoon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import java.util.Map;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ParseIngredientsTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("offset", 0)
                .addQueryParam("number", 10)
                .build();
    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"pizza", "Sushi"})
    public void ParseIngredients_Test(String queryParameter) {
//        String queryParameter = "en";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "language", queryParameter))
                .post("/recipes/parseIngredients")
                .prettyPeek()
                .then()
                .statusCode(200)
//                .body("en", Matchers.equalTo(queryParameter));
                .body("query", Matchers.containsStringIgnoringCase(queryParameter))           // проверка тела запроса (containsStringIgnoringCase - игнорирование большой буквы)
                .body("searchResults.results[0].name", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
}
