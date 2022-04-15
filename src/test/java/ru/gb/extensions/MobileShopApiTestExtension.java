package ru.gb.extensions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static ru.gb.config.MobileShopConfig.mobileShopConfig;

public class MobileShopApiTestExtension implements BeforeAllCallback {
    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        RestAssured.baseURI = mobileShopConfig.baseURI();
//убираем часть кода для получения его подефолту от CommonApiTestExtension
//        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();                 //выносим все в CommonApiTestExtension для исключения повторений
//        RestAssured.filters(new AllureRestAssured());                                     //вынеся они будут по дефолту
        RestAssured.requestSpecification = new RequestSpecBuilder()                         //использование requestSpecification для определения некоторых параметров request
               .setContentType(ContentType.JSON)                                            // что бы для всех request был ContentType JSON
               .build();
    }
}
