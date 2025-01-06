package eu.innowise.ingredientservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IngredientRequest(

        @NotBlank(message = "Name should can't be blank")
        String name,

        @NotNull(message = "Chance of loss can't be null")
        @Min(value = 0, message = "Chance of loss must be at least 0")
        @Max(value = 100, message = "Chance of loss must not exceed 100")
        Short chanceOfLoss,

        @NotNull(message = "Price can't be null")
        @Min(value = 0, message = "Price must be at least 0")
        Integer price,

        List<UsedIngredientRequest> usedIngredients
) {
}
