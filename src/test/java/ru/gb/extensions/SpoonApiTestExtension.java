package ru.gb.extensions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.gb.config.SpoonConfig.spoonConfig;

//настройка Extension

public class SpoonApiTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        RestAssured.baseURI = spoonConfig.baseURI();                                             //прокидываем наследование
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                        //включается логирование когда валидация падает
        RestAssured.filters(new AllureRestAssured());                                            //добавляем спецификацию allure для развернутого отчета
        RestAssured.requestSpecification = new RequestSpecBuilder()                              //авторизация на каждый тест
                .setContentType(ContentType.JSON)                                                //указываем нужные нам параметры
                .addQueryParam("apiKey", spoonConfig.apiKey() )                     //сюда прокидываем из спунконфига апикей
                .build();
    }
}
