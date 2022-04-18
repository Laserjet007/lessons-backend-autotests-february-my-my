package ru.gb.test.spoon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class PriceBreakdownWidgetTest {

    @BeforeAll
    static void beforeAll() {

    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {""})
    public void PriceBreakdownWidget_Test(String queryParameter) {
//        String queryParameter = "en";
        given()
                .queryParam("query", queryParameter)
                .post("/recipes/visualizePriceEstimator")
//                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
