package eu.innowise.ingredientservice.dto.response;

public record UsedIngredientResponse(

        String ingredientId,
        String name,
        int quantity
) {
}
