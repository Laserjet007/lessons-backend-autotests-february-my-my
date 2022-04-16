package ru.gb.test.spoon;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class RecipeTasteWidgetTest {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("language", "en")
                .addQueryParam("normalize", "false")
                .addQueryParam("rgb", "75,192,192")
                .build();
    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {""})
    public void RecipeTasteWidget_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .spec(requestSpecification)                                                    //добавляем спецификацию вместо параметров
                .post("/recipes/visualizeTaste")
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
