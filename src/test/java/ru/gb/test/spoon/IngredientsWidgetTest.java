package ru.gb.test.spoon;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.endpoints.SpoonEndpoints;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class IngredientsWidgetTest {

    @BeforeAll
    static void beforeAll() {

    }

    @ParameterizedTest//параметризуем тест (подходит больше для  тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"spoonacular-ingredient-vis-list"})
    public void IngredientsWidget_Test(String queryParameter) {
        given()
                .queryParam("query", queryParameter)
                .post(SpoonEndpoints.RECIPES_VIZUALIZE_INGREDIENTS.getEndpoint())//("/recipes/visualizeIngredients")
//                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
