package eu.innowise.ingredientservice.service;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;

import java.util.List;

public interface IngredientService {

    IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest);

    IngredientResponse getIngredientById(String id);

    IngredientResponse getIngredientByName(String name);

    IngredientResponse mixIngredients(List<UsedIngredientCreateRequest> usedIngredientCreateRequests);
}
