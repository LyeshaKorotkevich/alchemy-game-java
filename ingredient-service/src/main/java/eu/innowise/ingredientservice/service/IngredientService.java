package eu.innowise.ingredientservice.service;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;

import java.util.List;
import java.util.Optional;

public interface IngredientService {

    IngredientResponse createIngredient(IngredientRequest ingredientRequest);

    IngredientResponse getIngredientById(String id);

    IngredientResponse getIngredientByName(String name);

    Optional<IngredientResponse> mixIngredients(List<UsedIngredientRequest> usedIngredientRequests);
}
