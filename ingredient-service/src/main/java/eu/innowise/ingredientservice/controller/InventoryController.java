package eu.innowise.ingredientservice.controller;

import eu.innowise.ingredientservice.dto.response.InventoryResponse;
import eu.innowise.ingredientservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{user-id}")
    public InventoryResponse getInventory(@PathVariable("user-id") String userId) {
        return inventoryService.getInventoryByUserId(userId);
    }
}
