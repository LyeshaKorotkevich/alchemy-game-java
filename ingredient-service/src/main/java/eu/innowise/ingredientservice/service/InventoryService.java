package eu.innowise.ingredientservice.service;

import eu.innowise.ingredientservice.dto.response.InventoryResponse;

public interface InventoryService {

    InventoryResponse getInventoryByUserId(String userId);
}
