package eu.innowise.ingredientservice.dto.response;

import java.util.List;

public record UserInventoryResponse(

        String userId,
        List<SummaryIngredientResponse> ingredients
) {
}
