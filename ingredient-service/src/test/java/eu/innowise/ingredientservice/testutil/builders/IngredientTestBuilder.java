package eu.innowise.ingredientservice.testutil.builders;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.dto.response.UsedIngredientResponse;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class IngredientTestBuilder {

    @Builder.Default
    private String id = "3";

    @Builder.Default
    private String name = "Potion";

    @Builder.Default
    private Short chanceOfLoss = 10;

    @Builder.Default
    private Integer price = 2;

    @Builder.Default
    private List<UsedInRelationship> ingredients = List.of();

    public Ingredient getIngredient() {
        return Ingredient.builder()
                .id(id)
                .name(name)
                .chanceOfLoss(chanceOfLoss)
                .price(price)
                .ingredients(ingredients)
                .build();
    }

    public IngredientRequest getRequest() {
        return new IngredientRequest(name, chanceOfLoss, price, ingredients.stream()
                .map(usedInRelationship -> new UsedIngredientRequest(usedInRelationship.getId(), usedInRelationship.getQuantity()))
                .toList());
    }

    public IngredientResponse getResponse() {
        return new IngredientResponse(id, name, chanceOfLoss, price, ingredients.stream()
                .map(usedIngredients -> new UsedIngredientResponse(usedIngredients.getId(), usedIngredients.getIngredient().getName(), usedIngredients.getQuantity()))
                .toList());
    }
}
