package eu.innowise.ingredientservice.controller;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;
import eu.innowise.ingredientservice.service.IngredientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("/{name}")
    public DetailedIngredientResponse getIngredientByName(@PathVariable("name") @NotNull String name) {
        return ingredientService.getIngredientByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DetailedIngredientResponse createIngredient(@RequestBody @Valid IngredientCreateRequest ingredientCreateRequest) {
        return ingredientService.createIngredient(ingredientCreateRequest);
    }

    @PostMapping("/{userId}/mix")
    public DetailedIngredientResponse mixIngredients(
            @PathVariable("userId") String userId,
            @RequestBody @Valid List<UsedIngredientCreateRequest> usedIngredientCreateRequests
    ) {
        return ingredientService.mixIngredients(userId, usedIngredientCreateRequests);
    }
}
