package eu.innowise.ingredientservice.testutil;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;
import eu.innowise.ingredientservice.dto.response.UsedIngredientResponse;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder(setterPrefix = "with")
public class IngredientTestData {

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

    public IngredientCreateRequest getRequest() {
        return new IngredientCreateRequest(name, chanceOfLoss, price, ingredients.stream()
                .map(usedInRelationship -> new UsedIngredientCreateRequest(usedInRelationship.getId(), usedInRelationship.getQuantity()))
                .toList());
    }

    public DetailedIngredientResponse getResponse() {
        return new DetailedIngredientResponse(id, name, chanceOfLoss, price, ingredients.stream()
                .map(usedIngredients -> new UsedIngredientResponse(usedIngredients.getId(), usedIngredients.getIngredient().getName(), usedIngredients.getQuantity()))
                .toList());
    }
}
