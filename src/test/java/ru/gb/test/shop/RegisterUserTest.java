package ru.gb.test.shop;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

//тест для примера работы через докер со свагером (магазином мобилок )
public class RegisterUserTest {
    @Test
    void registerUserTest() {
        given()
                .body("{\n" +
                        "\"address\":\"russia\",n" +
                        "\"email\":\"adgrdsg@vas.ru\",n" +
                        "\"password\":\"vasya\",n" +
                        "\"phone\":\"8999999999\",n" +
                        "\"username\":\"vasya\",n" +
                "}")
                .post("/auth/register")
    }
}
