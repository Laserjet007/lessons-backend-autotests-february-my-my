package ru.gb.extensions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.gb.config.SpoonConfig.spoonConfig;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
//настройка Extension

public class SpoonApiTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        RestAssured.baseURI = spoonConfig.baseURI();                                             //прокидываем наследование
//убираем часть кода для получения его подефолту от CommonApiTestExtension
//      RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                        //включается логирование когда валидация падает
//      RestAssured.filters(new AllureRestAssured());                                            //добавляем спецификацию allure для развернутого отчета
        RestAssured.requestSpecification = new RequestSpecBuilder()                              //авторизация на каждый тест
                .setContentType(ContentType.JSON)                                                //указываем нужные нам параметры
//              .log(LogDetail.ALL)                                                              //временно поставим логирование для просмотра, response что бы в дальнейшем с этим работать
                .addQueryParam("apiKey", spoonConfig.apiKey() )                     //сюда прокидываем из спунконфига апи кей для авторизации (что бы на каждом тесте не прописывать каждый раз авторизацию)
                .build();
//        RestAssured.responseSpecification = new ResponseSpecBuilder()
//                .expectStatusCode(200)                                           //можем проверить, что все ответы идут с кодом 200 в случае если они 200
//                .build();
    }
}
