package eu.innowise.ingredientservice.dto.response;

import java.util.List;

public record DetailedIngredientResponse(

        String ingredientId,
        String name,
        Short chanceOfLoss,
        Integer price,
        List<UsedIngredientResponse> usedIngredients
) {
}
