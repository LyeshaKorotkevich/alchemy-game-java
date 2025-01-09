package eu.innowise.ingredientservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UsedIngredientCreateRequest(

        @NotBlank(message = "Ingredient ID can't be null")
        String id,

        @Positive(message = "Quantity must be greater than 0")
        int quantity
) {
}
