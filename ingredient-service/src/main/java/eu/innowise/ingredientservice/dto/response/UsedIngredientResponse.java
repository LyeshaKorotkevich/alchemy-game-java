package eu.innowise.ingredientservice.dto.response;

public record UsedIngredientResponse(

        String id,
        String name,
        int quantity
) {
}
