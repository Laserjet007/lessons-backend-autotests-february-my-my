package ru.gb.dto.spoon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                                                                    // создаем через новый POJO jackson (возможно долго генерить будет)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponse{
	private String spoonacularPassword;
	private String hash;
	private String status;
	private String username;
}