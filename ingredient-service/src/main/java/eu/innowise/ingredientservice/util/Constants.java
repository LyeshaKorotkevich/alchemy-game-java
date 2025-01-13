package eu.innowise.ingredientservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String INGREDIENT_NOT_FOUND_BY_ID = "Ingredient with ingredientId %s not found";
    public static final String INGREDIENT_NOT_FOUND_BY_NAME = "Ingredient with name %s not found";
    public static final String CANNOT_CREATE_INGREDIENT = "Cannot create new ingredient from provided ingredients";
    public static final String INGREDIENT_ALREADY_EXISTS = "Ingredient with name %s already exists";

    public static final String INVENTORY_NOT_FOUND_BY_USER_ID = "Inventory for user %s was not found";
}
