package ru.gb.test.spoon;
//в rest-assured есть достойная документация по проверкам : github/rest-assured/rest-assured/wiki/Usage

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ru.gb.dto.spoon.AddItemToShoppingListRequest;
import ru.gb.dto.spoon.CreateUserRequest;
import ru.gb.dto.spoon.CreateUserResponse;
import ru.gb.extensions.SpoonApiTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
//подготовка данных для теста
@SpoonApiTest                                                                           //ставим аннотацию на проброс данных пользователя и url
public class AddToShoppingListTest {
    private static CreateUserResponse createUserResponse;
    private static RequestSpecification hashParam;
//    private static String userName;                                                   //делаем статического юзера, что бы использовать в тестах
//    private static String hash;
    private int id;                                                                     //получаем id нестатический, тк для каждого теста должна быть своя переменная

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();                                                      //генератор фейковых данных из библиотечки репозитория для создания нового юзера (для использования в дальнейших тестах)
//      System.out.println(faker.chuckNorris().fact());
        createUserResponse = given()//JsonPath jsonPath меняем на createUserResponse
//                .log()                                                                // в случае необходимости логирования для обнаружения проблем
//                .all()
//для сериализацииCreateUserRequest теста AddToShoppingListTest используется объект определенного класса (с определенными полями getter setter конструктор и т.д.)
//в процессе сериализации происходит закладка любого объекта любого класса (когда мы вставляем объект любого класса то он будет автоматически сериализоваться (превращение объекта в определенный формат файла (в нашем случае в json))
                .body(CreateUserRequest.builder()                                       //создаем запрос юзера
                                .username(faker.funnyName().name())                     //добавляем библиотечки jackson-databind, jackson-core, jackson-annotations в <dependency> для распознавания java этого кода
                                .firstName(faker.name().firstName())                    //добавляем данные из конструкции ниже
                                .lastName(faker.name().lastName())
                                .email(faker.internet().emailAddress())
                                .build())
//                        "{\n" +                                                        //подставляем тело запроса (используем строку (для сериализации используется объект определенного класса(с определенными полями getter setter конструктор и т.д.))
//                        "    \"username\": \"" + faker.funnyName() + "\", \n" +        //" + + " - генерация данных //рандомизация полного имени
//                        "    \"firstName\": \"" + faker.name().firstName() + "\",\n" +
//                        "    \"lastName\": \"" + faker.name().lastName() + "\",\n" +
//                        "    \"email\": \"" + faker.internet().emailAddress()+ "\"\n" +
//                        "}")
                .post("/users/connect")                                               //ожидаем создание юзера
//                .prettyPeek()                                                          //для отображения в ответе полей
                .then()
                .statusCode(200)                                                       //201 необязательно (можно просто 200)
                .extract()                                                               //забрать полученные данные (логирование)
                .as(CreateUserResponse.class);                                           //делаем десериализацию (для исключения повтора queryParam("hash", hash)     )
//                .body()                                                                //берем тело
//                .jsonPath();                                                           //взять нужные данные из переменной jsonPath:
        hashParam = new RequestSpecBuilder()                                             //избавляемся от лишнего кода указывая параметры отдельно
                .addQueryParam("hash", createUserResponse.getHash())        //указываем hashParam для дальнейшего заполнения формы одинаковыми параметрами
                .build();
//        userName = jsonPath.getString("username");                                     //userName
//        hash = jsonPath.getString("hash");                                             //hash
    }
//проверка, что нет ни каких данных в корзине (если она не пустая то изначально невозможно узнать какие условия)
    @BeforeEach
    void setUp() {
        given()
                .spec(hashParam)                                                         //уже после десириализации достаточно написать .spec(hashParam)
//                .queryParam("hash", hash)                                              //используем квери параметры
                .get("/mealplanner/{username}/shopping-list", createUserResponse.getUsername())      //забираем из постмана mealplanner/your-users-name77/shopping-list?hash=d59eb6a65c286eb6   .. для обозначения параметра в {username} дописываем userName
                .then()                                                                   // добавляем проверки
                .statusCode(200)                                                       //статус код 200 (если негативный 400)
                .body("aisles", Matchers.hasSize(0));                                  //проверяем что в теле есть арей, и что у арея размер ноль (как в теле запроса)
    }

    public static Stream<AddItemToShoppingListRequest> shoppingListRequests() {             //выносим параметры  {"1 kg cucumbers,Cucumber", "2 kg tomatos,Tomato"}) возвращаем не аргументы а нужный класс AddltemToShopingListRequest
    return Stream.of(AddItemToShoppingListRequest.builder()                         //формируем нужные параметры
                  .item("1 kg cucumbers")
                  .aisle("Cucumber")
                  .parse(true)
            .build(),
           AddItemToShoppingListRequest.builder()                                          //формируем нужные параметры
                    .item("2 kg tomatos")
                    .aisle("Tomato")
                    .parse(true)
                    .build());
    }
//пишем тест (сначала кладем в корзину, затем проверяем что там что-то есть)
    @ParameterizedTest                                                                    //параметризуем тест (для этого воспользуемся   @CsvSource
//@CsvSource(value = {"1 kg cucumbers,Cucumber", "2 kg tomatos,Tomato"})                  //добавляем значение параметров - String item, String aisle
    @MethodSource("shoppingListRequests")
//    void addToShoppingListTest(String item, String aisle) {                             //добавляем входные параметры из тела запроса - String item, String aisle
   void addToShoppingListTest(AddItemToShoppingListRequest addItemToShoppingListRequest) {
       given()
                .log()
                .all()
                .spec(hashParam)                                                          //уже после десириализации достаточно написать .spec(hashParam)
                .body(addItemToShoppingListRequest) //(AddItemToShoppingListRequest

//                        "{\n" +                                                           //указываем тело запроса из постмана с добавлением параметров
//                        "    \"item\": \"" + item + "\",\n" +
//                        "    \"aisle\": \"" + aisle + "\",\n" +
//                        "    \"parse\": true\n" +
//                        "}")
                .post("/mealplanner/{username}/shopping-list/items", createUserResponse.getUsername())  //указываем тело запроса из постмана
                .then()
                .statusCode(200);                                                      //тело запроса не проверяем (важно что бы корзина была не пустая

        id = given()                                                                     //добавляем проверку, что корзина не пуста
                .spec(hashParam)
                .get("/mealplanner/{username}/shopping-list", createUserResponse.getUsername())
                .then()
                .statusCode(200)
                .body("aisles", Matchers.hasSize(1))                                   //проверка, что размер равен 1 товар
                .body("aisles.aisle", Matchers.hasItems(addItemToShoppingListRequest.getAisle()))  //проверка поля тела запроса: aisle
                .body("aisles.items", Matchers.hasSize(1))
                .extract()                                                                //получаем id для дальнейшего удаления
                .jsonPath()
                .getInt("aisles.items[0].id[0]");                                    //получить id из body. items[0].id[0] - указываем где он находится
    }
//удаление из корзины (можно сделать перед тестом, но обычно так делают)
    @AfterEach
    void tearDown() {
        given()
                .spec(hashParam)                                                          //уже после десириализации достаточно написать .spec(hashParam)
                .delete("/mealplanner/{username}/shopping-list/items/{id}", createUserResponse.getUsername(), id)
                .then()
                .statusCode(200);
    }
}
