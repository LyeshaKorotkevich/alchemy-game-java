package eu.innowise.ingredientservice.controller;

import eu.innowise.ingredientservice.dto.response.SummaryIngredientResponse;
import eu.innowise.ingredientservice.service.InventoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{userId}")
    public List<SummaryIngredientResponse> getIngredientsInUserInventory(@PathVariable("userId") @NotNull String userId) {
        return inventoryService.getIngredientsInUserInventory(userId);
    }
}
