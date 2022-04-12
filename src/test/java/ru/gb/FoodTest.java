package ru.gb;

import io.qameta.allure.junit5.AllureJunit5;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.gb.extensions.SpoonApiTest;
import ru.gb.extensions.SpoonApiTestExtension;

import static io.restassured.RestAssured.given;
// работа с RestAssured (типа Postman)

//@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class})                                 //пишем наследование на junit5 для красивого отображения beforeAll. SpoonApiTesy - наследование спунакуляра сайта // на самом деле эта конфигурация не удобна - поскольку наследования может быт много и с ui  и с  allure...
@SpoonApiTest                                                                                    //заменяем //@ExtendWith({AllureJunit5.class, SpoonApiTestExtension.class}) для наследования от аннотации
public class FoodTest {

//    String baseUrl = "https://api.spoonacular.com";                                            //добавляем url
//    String token = "86ad362742694dbc8fd0bb0efb949

//    eb2";                                         // добавим токен для регистрации

    @BeforeAll                                                                                   //протаскиваем логи и url во все тесты
    static void beforeAll() {
//        RestAssured.baseURI = "https://api.spoonacular.com";
//       RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                        //включается логирование когда валидация падает
//       RestAssured.filters(new AllureRestAssured());                                            //добавляем спецификацию allure для развернутого отчета
//       RestAssured.requestSpecification = new RequestSpecBuilder()                              //авторизация на каждый тест
//               .setContentType(ContentType.JSON)                                                //указываем нужные нам параметры
//               .addQueryParam("apiKey", "86ad362742694dbc8fd0bb0efb949eb2" )
//               .build();
    }

    @Test
    public void foodSearchTest()
    {
        String queryParameter = "Pizza";
        given()                                                                                  //в rest-assured все тесты начинаются с given() - какие - либо данные, условия
//              .when()                                                                          //писать не обязательно
//              .log()                                                                           //просмотр логов
//              .all()                                                                           //логируем все
//              .queryParam("apiKey", token )
                .queryParam("query", queryParameter )
                .queryParam("offset", 0 )
                .queryParam("number", 10 )
//              .queryParams (Map.of("query", queryParameter,                                    //вариант перечисления параметров
//                      "offset", 0
//                      "number", 10
//              ))
                .get("/food/search")
//              .get(baseUrl + "/food/search?query="+ queryParameter +"&offset=0&number=10")     //так обычно никто не пишет потому что queryParameter может быть много
//              .prettyPeek()                                                                    //логируем ответ get
                .then();
                .statusCode(200)                                                                 //проверка статус кода
                .body("query", Matchers.equalTo(queryParameter));                                // проверка тела запроса (Matchers - сравнить)



    }

}
