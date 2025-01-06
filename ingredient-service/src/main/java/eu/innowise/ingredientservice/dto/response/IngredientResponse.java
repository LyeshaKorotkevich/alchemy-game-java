package eu.innowise.ingredientservice.dto.response;

import java.util.List;

public record IngredientResponse(

        String id,
        String name,
        Short chanceOfLoss,
        Integer price,
        List<UsedIngredientResponse> usedIngredients
) {
}
