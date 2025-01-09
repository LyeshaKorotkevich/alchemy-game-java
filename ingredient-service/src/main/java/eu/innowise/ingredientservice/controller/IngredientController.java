package eu.innowise.ingredientservice.controller;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{name}")
    public IngredientResponse getIngredientByName(@PathVariable("name") String name) {
        return ingredientService.getIngredientByName(name);
    }

    @PostMapping
    public IngredientResponse createIngredient(@RequestBody IngredientCreateRequest ingredientCreateRequest) {
        return ingredientService.createIngredient(ingredientCreateRequest);
    }

    @PostMapping("/mix")
    public IngredientResponse mixIngredient(@RequestBody List<UsedIngredientCreateRequest> usedIngredientCreateRequests) {
        return ingredientService.mixIngredients(usedIngredientCreateRequests);
    }
}
