package eu.innowise.ingredientservice.dto.response;

import java.util.List;

public record InventoryResponse(

        String userId,
        List<IngredientResponse> ingredients
) {
}
