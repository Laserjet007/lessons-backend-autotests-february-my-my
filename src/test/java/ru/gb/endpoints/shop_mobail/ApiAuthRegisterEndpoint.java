package ru.gb.endpoints.shop_mobail;

import ru.gb.dto.shop_mobail.UserDTO;

import static io.restassured.RestAssured.given;
//для избежания повтора кода в UserTest
@Endpoint("Auth/register")                                      //достаточно указать с помощью данной аннотации какой будет endpoint и дальше с ним работать
public class ApiAuthRegisterEndpoint extends BaseEndpoint{
    public UserDTO registerUser(UserDTO userDTO) {              //данный endpoint может регистрировать user
        return given()                                          //прокидываем донные из registerUserTest
                .body(userDTO)
                .post(endpoint)                                 //указываем endpoint что бы его можно было переиспользовать
                .then()
                .statusCode(201)
                .extract()
                .as(UserDTO.class);

    }
}
