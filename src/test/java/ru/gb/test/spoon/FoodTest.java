package ru.gb.test.spoon;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
//тест на поиск определенных продуктов по кучке

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;
// работа с RestAssured (отталкиваясь от Postman)

//@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class})                                 //пишем наследование на junit5 для красивого отображения beforeAll. SpoonApiTesy - наследование спунакуляра сайта // на самом деле эта конфигурация не удобна - поскольку наследования может быт много и с ui  и с  allure...
@SpoonApiTest                                                                                    //заменяем //@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class}) для наследования от аннотации
public class FoodTest {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;                                  // проверка ответа

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("offset", 0)
                .addQueryParam("number", 10)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectBody("limit", Matchers.equalTo(10))                          // проверка ответа
                .expectBody("offset", Matchers.equalTo(0))
                .build();
    }

//    String baseUrl = "https://api.spoonacular.com";                                             //добавляем url
//    String token = "86ad362742694dbc8fd0bb0efb949eb2";                                          // добавим токен для регистрации

//    @BeforeAll                                                                                  //протаскиваем логи и url во все тесты
//    static void beforeAll() {
//        RestAssured.baseURI = "https://api.spoonacular.com";
//       RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                        //включается логирование когда валидация падает
//       RestAssured.filters(new AllureRestAssured());                                            //добавляем спецификацию allure для развернутого отчета
//       RestAssured.requestSpecification = new RequestSpecBuilder()                              //авторизация на каждый тест
//               .setContentType(ContentType.JSON)                                                //указываем нужные нам параметры
//               .addQueryParam("apiKey", "86ad362742694dbc8fd0bb0efb949eb2" )
//               .build();
//    }

    @ParameterizedTest                                                                           //параметризуем тест (подходит больше для api тестов. потому что их нужно проверить с различными параметрами, а по пирамиде тестирования их должно быть больше (и благодаря что они более стабильнее мы можем проверить больше функционала проверить без риска хрупкости тестов)
    @ValueSource(strings = {"pizza", "Sushi"})
    public void foodSearchRecipesContainsQueryTest(String queryParameter) {                                          //входной параметр - String queryParameter
//        String queryParameter = "Pizza";
        given()                                                                                  //в rest-assured все тесты начинаются с given() - какие - либо данные, условия
                .queryParam("query", queryParameter)
                .spec(requestSpecification)
//                .queryParams(Map.of("query", queryParameter,
//                        "offset", 0,
//                        "number", 10))

//              .when()                                                                          //писать не обязательно
//              .log()                                                                           //просмотр логов
//              .all()                                                                           //логируем все
//              .queryParam("apiKey", token )
//              .queryParam("query", queryParameter )
//              .queryParam("offset", 0 )
//              .queryParam("number", 10 )
//              .queryParams (Map.of("query", queryParameter,                                    //вариант перечисления параметров
//                      "offset", 0
//                      "number", 10
//              ))
                .get("/food/search")
//              .get(baseUrl + "/food/search?query="+ queryParameter +"&offset=0&number=10")     //так обычно никто не пишет потому что queryParameter может быть много
                .prettyPeek()                                                                    //временно поставим логирование для просмотра, response что бы в дальнейшем с этим работать (находить в ответе нужный элемент для сравнения)
                .then()
                .spec(responseSpecification)                                                     // проверка ответа
                .statusCode(200)                                                              //проверка статус кода
//                .body("query", Matchers.equalTo(queryParameter))                               // проверка тела запроса (Matchers - сравнить)
                .body("query", Matchers.containsStringIgnoringCase(queryParameter))           // проверка тела запроса (containsStringIgnoringCase - игнорирование большой буквы)
                .body("searchResults.results[0].name", Matchers.everyItem(Matchers.containsStringIgnoringCase(queryParameter))); //проверяем что в ответе (после флага prettyPeek) идет name - Recipes. [0] - ищем первый результат Arrey содержит во всех ответах queryParameter"pizza" (Matchers.everyItem(Matchers.containsString(queryParameter))

    }
}
