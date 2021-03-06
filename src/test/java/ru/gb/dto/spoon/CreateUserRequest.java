package ru.gb.dto.spoon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                                                                    // создаем через новый POJO jackson (возможно долго генерить будет)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserRequest {

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("email")
	private String email;

	@JsonProperty("username")
	private String username;
}