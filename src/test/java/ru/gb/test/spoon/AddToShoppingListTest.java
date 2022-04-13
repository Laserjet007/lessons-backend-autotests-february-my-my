package ru.gb.test.spoon;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage
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
//подготовка данных для теста
@SpoonApiTest                                                                             //ставим аннотацию на проброс данных пользователя и url
public class AddToShoppingListTest {
    private static String userName;                                                       //делаем статического юзера, что бы использовать в тестах
    private static String hash;
    private int id;                                                                       //получаем id нестатический, тк для каждого теста должна быть своя переменная

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();                                                        //генератор фейковых данных из библиотечки репозитория для создания нового юзера (для использования в дальнейших тестах)
        System.out.println(faker.chuckNorris().fact());
        JsonPath jsonPath = given()
                //todo на 4-ом занятии заменим с помощью сериализации
                .body("{\n" +                                                          //подставляем тело запроса
                        "    \"username\": \"" + faker.funnyName() + "\", \n" +           //" + + " - генерация данных //рандомизация полного имени
                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
                        "    \"email\": \"" + faker.internet().emailAddress()+ "\"\n" +
                        "}")
                .post("/users/connect")                                                //ожидаем создание юзера
                .then()
                .statusCode(200)                                                        //201 необязательно (можно просто 200)
                .extract()                                                                //забрать полученные данные
                .body()                                                                   //берем тело
                .jsonPath();                                                              //взять нужные данные из переменной jsonPath:
        userName = jsonPath.getString("username");                                   //userName
        hash = jsonPath.getString("hash");                                           //hash
    }
//проверка, что нет ни каких данных в корзине (если она не пустая то изначально невозможно узнать какие условия)
    @BeforeEach
    void setUp() {
        given()
                .queryParam("hash", hash)                                              //используем квери параметры
                .get("/mealplanner/{username}/shopping-list", userName)                //забираем из постмана mealplanner/your-users-name77/shopping-list?hash=d59eb6a65c286eb6   .. для обозначения параметра в {username} дописываем userName
                .then()                                                                   // добавляем проверки
                .statusCode(200)                                                       //статус код 200 (если негативный 400)
                .body("aisles", Matchers.hasSize(0));                                  //проверяем что в теле есть арей, и что у арея размер ноль (как в теле запроса)
    }
//пишем тест (сначала кладем в корзину, затем проверяем что там что-то есть)
    @ParameterizedTest                                                                    //параметризуем тест (для этого воспользуемся   @CsvSource
    @CsvSource(value = {"1 kg cucumbers,Cucumber", "2 kg tomatos,Tomato"})                //добавляем значение параметров - String item, String aisle
    void addToShoppingListTest(String item, String aisle) {                               //добавляем входные параметры из тела запроса - String item, String aisle
        given()
                .queryParam("hash", hash)                                              //указываем хеш
                .body("{\n" +                                                          //указываем тело запроса из постмана с добавлением параметров
                        "    \"item\": \"" + item + "\",\n" +
                        "    \"aisle\": \"" + aisle + "\",\n" +
                        "    \"parse\": true\n" +
                        "}")
                .post("/mealplanner/{username}/shopping-list/items", userName)         //указываем тело запроса из постмана
                .then()
                .statusCode(200);                                                      //тело запроса не проверяем (важно что бы корзина была не пустая

        id = given()                                                                     //добавляем проверку, что корзина не пуста
                .queryParam("hash", hash)
                .get("/mealplanner/{username}/shopping-list", userName)
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))                                  //проверка, что размер равен 1 товар
                .body("aisles.aisle", Matchers.hasItems(aisle))                       //проверка поля тела запроса: aisle
                .body("aisles.items", Matchers.hasSize(1))
                .extract()                                                              //получаем id для дальнейшего удаления
                .jsonPath()
                .getInt("aisles.items[0].id[0]");                                  //получить id из body. items[0].id[0] - указываем где он находится
    }
//удаление из корзины (можно сделать перед тестом, но обычно так делают)
    @AfterEach
    void tearDown() {
        given()
                .queryParam("hash", hash)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", userName, id)
                .then()
                .statusCode(200);
    }
}
