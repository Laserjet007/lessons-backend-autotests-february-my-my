package ru.gb.test.shop;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.MobileShopApiTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

//тест для примера работы через докер со свагером (магазином мобилок )
@MobileShopApiTest
public class RegisterUserTest {
    private static Faker faker = new Faker();                                             //для написания на русском LocalDate.from("ru")
    String address = faker.address().fullAddress();                                       //готовим фейковые данные
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String phone = faker.phoneNumber().phoneNumber();
    String username = faker.name().fullName();
    @Test
    void registerUserTest() {


        given()
                .body("{\n" +
                        "\"address\":\""+address+"\",n" +
                        "\"email\":\""+email+"\",n" +
                        "\"password\":\""+password+"\",n" +
                        "\"phone\":\""+phone+"\",n" +
                        "\"username\":\""+username+"\",n" +
                "}")
                .post("/auth/register")
                .then()
                .statusCode(201)                                                              //проверки
                .body("address", equalTo(address))                                            //вместо матчерса можно указать статический методequalTo
                .body("email", equalTo(address))
                .body("phone", equalTo(address))
                .body("username", equalTo(address));                                          //пароль проверить не можем

    }
}
