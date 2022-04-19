package ru.gb.test.spoon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.endpoints.SpoonEndpoints;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesByNutrientsTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("minSugar", 0)
                .addQueryParam("maxSugar", 100)
                .addQueryParam("minZinc", 0)
                .addQueryParam("maxZinc", 100)
                .addQueryParam("offset", 394)
                .addQueryParam("number", 10)
                .addQueryParam("random", "false")
                .addQueryParam("limitLicense", true)
                .build();

    }

    @ParameterizedTest//параметризуем тест (подходит больше для api тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"Asian Lettuce Wraps"})
    public void SearchRecipesByNutrients_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                .get(SpoonEndpoints.RECIPES_FIND_BY_NUTRIENTS.getEndpoint())                   //("/recipes/findByNutrients")
//                .prettyPeek()
                .then()
                .statusCode(200)
                .body("searchResults.results.title", Matchers.everyItem(Matchers
                        .containsStringIgnoringCase(queryParameter)));                         //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
}