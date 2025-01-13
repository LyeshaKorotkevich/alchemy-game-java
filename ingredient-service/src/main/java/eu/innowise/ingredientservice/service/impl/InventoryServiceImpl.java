package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.response.SummaryIngredientResponse;
import eu.innowise.ingredientservice.exception.EntityNotFoundException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.model.node.Inventory;
import eu.innowise.ingredientservice.model.relationship.HasRelationship;
import eu.innowise.ingredientservice.repository.InventoryRepository;
import eu.innowise.ingredientservice.service.InventoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static eu.innowise.ingredientservice.util.Constants.INVENTORY_NOT_FOUND_BY_USER_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SummaryIngredientResponse> getIngredientsInUserInventory(@NotNull String userId) {
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(INVENTORY_NOT_FOUND_BY_USER_ID, userId)));
        return inventory.getIngredients()
                .stream()
                .map(HasRelationship::getIngredient)
                .map(ingredientMapper::toSummeryResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteIngredientFromUserInventory(String userId, String ingredientId) {
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(INVENTORY_NOT_FOUND_BY_USER_ID, userId)));

        boolean ingredientRemoved = inventory.getIngredients().removeIf(
                relationship -> relationship.getIngredient().getId().equals(ingredientId)
        );

        if (ingredientRemoved) {
            log.info("Ingredient with ID '{}' removed from user '{}' inventory.", ingredientId, userId);
            inventoryRepository.save(inventory);
        } else {
            log.error("Ingredient with ID '{}' not found in user '{}' inventory.", ingredientId, userId);
        }
    }
}
