package ru.gb.test.shop_mobail;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.gb.dto.shop_mobail.UserDTO;
import ru.gb.extensions.MobileShopApiTest;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//тест для примера работы через докер со свагером (магазином мобилок )
@MobileShopApiTest
public class RegisterUserTest {
    private static Faker faker = new Faker();                                             //для написания на русском LocalDate.from("ru")
    private UserDTO userDTO;
    String address = faker.address().fullAddress();                                       //готовим фейковые данные
    String email = faker.internet().emailAddress();
    String password = faker.internet().password();
    String phone = faker.phoneNumber().phoneNumber();
    String username = faker.name().fullName();

    @BeforeEach                                                                          //инициализируем userDTO
    void setUp() {
         userDTO = UserDTO.builder()                                                     //указываем объединенные параметры
            .address(address)
            .password(password)
            .username(username)
            .email(email)
            .phone(phone)
            .build();
    }

    @Test
    void registerUserTest() {
        UserDTO actualUserDTO = given()
//                        .body(UserDTO.builder()                                                     //указываем объединенные параметры
//                        .address(address)
//                        .password(password)
//                        .username(username)
//                        .email(email)
//                        .phone(phone)
//                        .build())
//                        "{\n" +
//                        "\"address\":\""+address+"\",n" +
//                        "\"email\":\""+email+"\",n" +
//                        "\"password\":\""+password+"\",n" +
//                        "\"phone\":\""+phone+"\",n" +
//                        "\"username\":\""+username+"\",n" +
//                "}")
                .body(userDTO)
                .post("/auth/register")
                .then()
                .statusCode(201)                                                           //проверки
                .extract()                                                                    //необязательно проверять все поля - можно применить эту конструкцию
                .as(UserDTO.class);                                                           //генерим нужный класс в папке dto и получаем UserDTO и выносим в отдельную переменную через var
        assertThat(actualUserDTO)                                                             //проверяем с помощю ассертджей для проверки объектов
                .usingRecursiveComparison()                                                   //для исключения полей из проверки (в случае ненадобности)
                .ignoringFields("password")                                   // не проверять поле password
                .ignoringExpectedNullFields()                                                 //все поля в expekted Result Noll проверяться не будут
                .isEqualTo(userDTO);
//        .body("address", equalTo(address))                                                  //вместо матчерса можно указать статический методequalTo
//                .body("email", equalTo(address))
//                .body("phone", equalTo(address))
//                .body("username", equalTo(address));                                        //пароль проверить не можем

    }
}
