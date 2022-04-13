package ru.gb.test.shop;

import com.github.javafaker.Faker;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.MobileShopApiTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
//еще один тест с получение юзера для мобайлшопа
@MobileShopApiTest
public class UserTest {
    private static Faker faker = new Faker();                                             //для написания на русском LocalDate.from("ru")
    String address = faker.address().fullAddress();                                       //готовим фейковые данные
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String phone = faker.phoneNumber().phoneNumber();
    String username = faker.name().fullName();
    String tokin;
    @BeforeEach
    void  setUp(){
        tokin = given()
                .body("{\n" +
                        "\"address\":\"" + address + "\",n" +
                        "\"email\":\"" + email + "\",n" +
                        "\"password\":\"" + password + "\",n" +
                        "\"phone\":\"" + phone + "\",n" +
                        "\"username\":\"" + username + "\",n" +
                        "}")
                .post("/auth/register")
                .then()
                .statusCode(201)                                                              //проверки
                .body("address", equalTo(address))                                            //вместо матчерса можно указать статический методequalTo
                .body("email", equalTo(address))
                .body("phone", equalTo(address))
                .body("username", equalTo(address))                                          //пароль проверить не можем
                .extract()           //для успешного прохождения теста нужна авторизация через берер токен
                .body()
                .jsonPath()
                .getString("token");                                                      //пишем getString чтобы не приводить типы
    }

    @Test                                                                                       //создаем тестовые данные для создания нового юзера
    void registerUserTest() {


        given()
                .header(new Header("Authorization", "Bearer" + tokin))
                .get("/user")                                                                 //метод получения юзера
                .then()
                .statusCode(201)                                                              //проверки
                .body("address", equalTo(address))                                            //вместо матчерса можно указать статический методequalTo
                .body("email", equalTo(address))
                .body("phone", equalTo(address))
                .body("username", equalTo(address));                                          //пароль проверить не можем

    }
}
