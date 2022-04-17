package ru.gb.dto.spoon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//для сериализации теста AddToShoppingListTest используется объект определенного класса (с определенными полями getter setter конструктор и т.д.)

@Data                                                                      // используем библиотечку Lombok
@Builder                                                                   // используем библиотечку Lombok
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
@JsonProperty() //для преобразования данных в json существует аннотация (она преобразовывает текст названия в приемлимый для java язык)
    private String username;                                               //(private String для соблюдения инкапсуляции)
    private String firstName;
    private String lastName;
    private String email;
}
