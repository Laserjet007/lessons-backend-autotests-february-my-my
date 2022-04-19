package ru.gb.test.spoon;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.endpoints.spoon.SpoonEndpoints;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class ClassifyCuisineTest {
    private static ResponseSpecification responseSpecification;

    @BeforeAll
    static void beforeAll() {
        responseSpecification = new ResponseSpecBuilder()
                .expectBody("cuisine", Matchers.equalTo("Mediterranean"))
                .build();
    }

    @ParameterizedTest                                                                         //параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"Mediterranean"})
    public void ClassifyCuisine_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .post(SpoonEndpoints.RECIPES_CUISINE.getEndpoint())                           //("/recipes/cuisine")
//                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .statusCode(200);
    }
}
