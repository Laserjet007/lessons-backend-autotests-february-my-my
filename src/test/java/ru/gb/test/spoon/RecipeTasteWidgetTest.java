package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import java.util.Map;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class RecipeTasteWidgetTest {

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"pizza", "Sushi"})
    public void RecipeTasteWidget_Test(String queryParameter) {
//        String queryParameter = "75,192,192";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "language", "en",
                        "normalize", "false",
                        "minCalories", "50",
                        "rgb", queryParameter))
                .post("/recipes/visualizeTaste")
                .prettyPeek()
                .then()
                .statusCode(200)
                .body("rgb", Matchers.equalTo(queryParameter));
    }
}
