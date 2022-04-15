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
public class GetRecipeInformationBulkTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("ids", 715538,716429)
                .addQueryParam("includeNutrition", false)
                .build();
    }

    @ParameterizedTest//параметризуем тест (подходит больше для api тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"Pink When"})
    public void GetRecipeInformationBulk_Test(String queryParameter) {
//        String queryParameter = "715538,716429";
        given()
                .queryParam("query", queryParameter)
                .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                .get("/recipes/informationBulk")
                .prettyPeek()
                .then()
                .statusCode(200)
//                .body("id", Matchers.equalTo(queryParameter))
//                .body("title", Matchers.equalTo(queryParameter))// проверка тела запроса (Matchers - сравнить)
//                .body("query", Matchers.containsStringIgnoringCase(queryParameter))          // проверка тела запроса (containsStringIgnoringCase - игнорирование большой буквы)
                .body("searchResults.results.sourceName", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
}
