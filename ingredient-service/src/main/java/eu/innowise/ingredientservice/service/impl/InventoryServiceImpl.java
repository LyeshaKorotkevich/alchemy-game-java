package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.response.InventoryResponse;
import eu.innowise.ingredientservice.exception.NotFoundException;
import eu.innowise.ingredientservice.mapper.InventoryMapper;
import eu.innowise.ingredientservice.model.node.Inventory;
import eu.innowise.ingredientservice.repository.InventoryRepository;
import eu.innowise.ingredientservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public InventoryResponse getInventoryByUserId(String userId) {
        Inventory inventory = inventoryRepository.findByUserId(userId)
                .orElseThrow(() -> NotFoundException.of(Inventory.class, userId));
        return inventoryMapper.toResponse(inventory);
    }
}
