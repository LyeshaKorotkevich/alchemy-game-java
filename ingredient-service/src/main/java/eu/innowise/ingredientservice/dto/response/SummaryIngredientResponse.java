package eu.innowise.ingredientservice.dto.response;

public record SummaryIngredientResponse(

        String ingredientId,
        String name,
        Integer price
) {
}
