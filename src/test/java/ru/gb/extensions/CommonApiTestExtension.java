package ru.gb.extensions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
//для применения подхода (DRY не повторяться) пишем extension

public class CommonApiTestExtension  implements BeforeAllCallback {
//выносим все из MobileShopApiTestExtension в CommonApiTestExtension для исключения повторений

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
//      RestAssured.baseURI = mobileShopConfig.baseURI();                                     //выносим все из MobileShopApiTestExtension в CommonApiTestExtension для исключения повторений
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
//      RestAssured.requestSpecification = new RequestSpecBuilder()                           //использование requestSpecification для определения некоторых параметров request
//                   .setContentType(ContentType.JSON)                                        // что бы для всех request был ContentType JSON
//                   .build();
        }

    //выносим из MobileShopApiTestExtension
}
