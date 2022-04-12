package ru.gb;

import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest                                                                                //ставим аннотацию на проброс данных пользователя и url
public class AddToShoppingListTest {
    private static String userName;                                                          //делаем статического юзера, что бы использовать в тестах
    private static String hash;
    private int id;

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();                                                           //генератор фейковых данных из библиотечки репозитория для создания нового юзера (для использования в дальнейших тестах)
        System.out.println(faker.chuckNorris().fact());
        JsonPath jsonPath = given()
                //todo на 4-ом занятии заменим с помощью сериализации
                .body("{\n" +                                                             //подставляем тело запроса
                        "    \"username\": \"" + faker.funnyName() + "\", \n" +              //" + + " - генерация данных //рандомизация полного имени
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress()+ "\"\n" +
                        "}")
                .post("/users/connect")                                                  //ожидаем создание юзера
                .then()
                .statusCode(200)                                                         //201 необязательно (можно просто 200)
                .extract()                                                                 //забрать полученные данные
                .body()                                                                    //берем тело
                .jsonPath();                                                               //взять нужные данные из переменной jsonPath:
        userName = jsonPath.getString("username");                                                      //userName
        hash = jsonPath.getString("hash");                                                               //hash
    }

    @BeforeEach
    void setUp() {
        given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(0));
    }

    @ParameterizedTest
    @CsvSource(value = {"1 kg cucumbers,Cucumber", "2 kg tomatos,Tomato"})
    void addToShoppingListTest(String item, String aisle) {
        given()
                .queryParam("hash", hash)
                .body("{\n" +
                        "    \"item\": \"" + item + "\",\n" +
                        "    \"aisle\": \"" + aisle + "\",\n" +
                        "    \"parse\": true\n" +
                        "}")
                .post("/mealplanner/{username}/shopping-list/items", userName)
                .then()
                .statusCode(200);

        id = given()
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))
                .body("aisles.aisle", Matchers.hasItems(aisle))
                .body("aisles.items", Matchers.hasSize(1))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }

    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", hash)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", userName, id)
                .then()
                .statusCode(200);
    }
}
