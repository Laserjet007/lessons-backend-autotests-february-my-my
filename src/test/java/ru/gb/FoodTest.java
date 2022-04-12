package ru.gb;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
// работа с RestAssured (типа Postman)
public class FoodTest {

//    String baseUrl = "https://api.spoonacular.com";                                            //добавляем url
    String token = "86ad362742694dbc8fd0bb0efb949eb2";                                           // добавим токен для регистрации

    @BeforeAll                                                                                   //протаскиваем логи и url во все тесты
    static void beforeAll() {
        RestAssured.baseURI = "https://api.spoonacular.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                        //включается логирование когда валидация падает
    }

    @Test
    public void foodSearchTest()
    {
        String queryParameter = "Pizza";
        given()                                                                                  //в rest-assured все тесты начинаются с given() - какие - либо данные, условия
//              .when()                                                                          //писать не обязательно
//              .log()                                                                         //просмотр логов
//              .all()                                                                         //логируем все
                .queryParam("apiKey", token )
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
//              .statusCode(200)                                                                 //проверка статус кода
//              .body("query", Matchers.equalTo(queryParameter));                                // проверка тела запроса (Matchers - сравнить)



    }

}
