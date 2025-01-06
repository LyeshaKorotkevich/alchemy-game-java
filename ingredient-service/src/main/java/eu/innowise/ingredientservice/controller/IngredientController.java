package eu.innowise.ingredientservice.controller;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientRequest;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{id}")
    public IngredientResponse getIngredientById(@PathVariable("id") String id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public IngredientResponse createIngredient(@RequestBody IngredientRequest ingredientRequest) {
        return ingredientService.createIngredient(ingredientRequest);
    }

    @PostMapping("/mix")
    public IngredientResponse mixIngredient(@RequestBody List<UsedIngredientRequest> usedIngredientRequests) {
        Optional<IngredientResponse> ingredientResponse = ingredientService.mixIngredients(usedIngredientRequests);
        if (ingredientResponse.isPresent()) {
            return ingredientResponse.get();
        }
        return null; // TODO
    }
}
