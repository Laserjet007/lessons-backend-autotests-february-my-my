package ru.gb.test.shop;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.MobileShopApiTest;

import static io.restassured.RestAssured.given;

@MobileShopApiTest
public class CatalogTest {



    @Test
    void  catalogTest(){
        given()                                                                                    //регистрация не нужна поэтому сразу переходим к гивен
                .get("/catalog")                                                                // тела не будет пишем гет
                .then()
                .statusCode(200)
                .body("info.name", Matchers.hasItems("Apple iPhone 8 Plus", "Apple iPhone X")); //получать список телефонов лучше получать из базы через  @Beefoeach


    }

}
