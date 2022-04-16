package ru.gb.test.spoon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesIngredientsTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("ingredients", "carrots,tomatoes")
                .addQueryParam("number", 10)
                .addQueryParam("limitLicense", true)
                .addQueryParam("ranking", 1)
                .addQueryParam("ignorePantry", false)
                .build();
    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"Farfalle With Broccoli, Carrots and Tomatoes"})
    public void SearchRecipesIngredients_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                .get("/recipes/findByIngredients")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("searchResults.results.title", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))
    }
}
