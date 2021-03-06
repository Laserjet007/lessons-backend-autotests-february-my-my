package ru.gb.test.spoon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.endpoints.spoon.SpoonEndpoints;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class AnalyzeRecipeSearchQueryTest {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("q", "salmon with fusilli and no nuts")
                .build();
//        responseSpecification = new ResponseSpecBuilder()
//                .expectBody("name", Matchers.equalTo("fusilli"))
//                .expectBody("include", Matchers.equalTo(true))
//                .build();
    }
    @ParameterizedTest                                                                         //параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"salmon"})
    public void AnalyzeRecipeSearchQuery_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                .get(SpoonEndpoints.RECIPES_QUERIES_ANALYZE.getEndpoint())//get("/recipes/queries/analyze")
//                .prettyPeek()
                .then()
//                .spec(responseSpecification)
                .statusCode(200);
//                .body("searchResults.results[0].name", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
}
