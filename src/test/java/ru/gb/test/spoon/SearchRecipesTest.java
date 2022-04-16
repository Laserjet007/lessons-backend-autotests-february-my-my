package ru.gb.test.spoon;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class SearchRecipesTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("maxSodium", 100)
                .addQueryParam("minSugar", 0)
                .addQueryParam("maxSugar", 100)
                .addQueryParam("minZinc", 0)
                .addQueryParam("mixZinc", 100)
                .addQueryParam("offset", 394)
                .addQueryParam("number", 10)
                .addQueryParam("limitLicense", true)
                .build();
    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"394"})
        public void SearchRecipes_Test(String queryParameter) {
            given()
                   .queryParam("query", queryParameter)
                    .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                    .get("/recipes/complexSearch")
                    .prettyPeek()
                    .then()
                    .statusCode(200)
//                    .body("offset", Matchers.equalTo(queryParameter));
//                    .body("query", Matchers.containsStringIgnoringCase(queryParameter))           // проверка тела запроса (containsStringIgnoringCase - игнорирование большой буквы)
                    .body("searchResults.results[0].results", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
    }
