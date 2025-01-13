package eu.innowise.ingredientservice.service;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;

import java.util.List;

public interface IngredientService {

    DetailedIngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest);

    DetailedIngredientResponse getIngredientById(String id);

    DetailedIngredientResponse getIngredientByName(String name);

    DetailedIngredientResponse mixIngredients(String userId, List<UsedIngredientCreateRequest> usedIngredientCreateRequests);
}
