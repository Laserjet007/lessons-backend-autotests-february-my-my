package ru.gb.dto.spoon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                                                                 // создаем через новый POJO jackson (возможно долго генерить будет)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToShoppingListRequest {

	@JsonProperty("item")
	private String item;

	@JsonProperty("parse")
	private boolean parse;

	@JsonProperty("aisle")
	private String aisle;
}