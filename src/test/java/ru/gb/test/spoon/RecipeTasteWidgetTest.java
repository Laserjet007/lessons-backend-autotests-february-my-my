package ru.gb.test.spoon;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;
import java.util.Map;
import static io.restassured.RestAssured.given;

@SpoonApiTest
public class RecipeTasteWidgetTest {
    @Test
    public void RecipeTasteWidget() {
        String queryParameter = "75,192,192";
        given()
                .when()
                .queryParams(Map.of("apiKey", "token",
                        "language", "en",
                        "normalize", "false",
                        "minCalories", "50",
                        "rgb", queryParameter
                ))
                .then()
                .statusCode(200)
                .body("rgb", Matchers.equalTo(queryParameter));
    }
}
