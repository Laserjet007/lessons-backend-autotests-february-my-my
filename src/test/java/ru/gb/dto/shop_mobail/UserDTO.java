package ru.gb.dto.shop_mobail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//можно соединить RegisterUserResponse и RegisterUserRequest для уменьшения кода (но в основном так делать нельзя из-за неудобства поиска ошибок и возможных изменений данных кода)
@Data                                           // создаем через новый POJO jackson (возможно долго генерить будет)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {                         //вставляем все поля из RegisterUserResponse так как там содержаться поля RegisterUserRequest
    @JsonProperty("password")
    private String password;

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("orders")
    private List<Object> orders;

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("token")
    private String token;

    @JsonProperty("username")
    private String username;
}
