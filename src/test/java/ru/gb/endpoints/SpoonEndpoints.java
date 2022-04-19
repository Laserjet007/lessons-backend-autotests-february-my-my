package ru.gb.endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SpoonEndpoints {
    FOOD_SEARCH("/food/search"),
    USER_CONNECT("/users/connect"),
    MEALPLANNER_USERNAME_SHOPPING_LIST("/mealplanner/{username}/shopping-list"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS("/mealplanner/{username}/shopping-list/items"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ID("/mealplanner/{username}/shopping-list/items/{id}"),
    RECIPES_QUERIES_ANALYZE("/recipes/queries/analyze"),
    RECIPES_CUISINE("/recipes/cuisine"),
    RECIPES_INFORMATION_BULK("/recipes/informationBulk"),
    RECIPES_VIZUALIZE_INGREDIENTS("/recipes/visualizeIngredients"),
    RECIPES_PARSE_INGREDIENTS("/recipes/parseIngredients"),
    RECIPES_VIZUALIZE_PRICE_ESTIMATOR("/recipes/visualizePriceEstimator"),
    RECIPES_VIZUALIZE_TESTE("/recipes/visualizeTaste"),
    RECIPES_FIND_BY_NUTRIENTS("/recipes/findByNutrients"),
    RECIPES_FIND_BY_INGREDIENTS("/recipes/findByIngredients"),
    RECIPES_COMPLEX_SEARCH("/recipes/complexSearch");

    @Getter
    final String endpoint;
}
