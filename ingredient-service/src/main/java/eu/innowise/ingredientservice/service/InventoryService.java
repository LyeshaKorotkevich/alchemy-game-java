package eu.innowise.ingredientservice.service;

import eu.innowise.ingredientservice.dto.response.SummaryIngredientResponse;

import java.util.List;

public interface InventoryService {

    List<SummaryIngredientResponse> getIngredientsInUserInventory(String userId);

    void deleteIngredientFromUserInventory(String userId, String ingredientId);
}
