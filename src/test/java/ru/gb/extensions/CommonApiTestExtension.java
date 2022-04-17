package ru.gb.extensions;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.mapper.ObjectMapperType;
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
        RestAssured.config = RestAssured.config()                                             //настройка restAssured для работы с библиотекой jackson
                .objectMapperConfig(new ObjectMapperConfig(ObjectMapperType.JACKSON_2));      //для прикручивания библиотечки jackson.version к RestAssured указываем как будем производить сериализацию теста AddToShoppingListTest (указываем т.к. есть другие библиотечки) (objectMapperConfig - сопоставление полей объекти и json)(ObjectMapperType.JACKSON_2 - вторая версия)
//      RestAssured.requestSpecification = new RequestSpecBuilder()                           //использование requestSpecification для определения некоторых параметров request
//                   .setContentType(ContentType.JSON)                                        // что бы для всех request был ContentType JSON
//                   .build();
        }

    //выносим из MobileShopApiTestExtension
}
