package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;
import eu.innowise.ingredientservice.exception.EntityAlreadyExistsException;
import eu.innowise.ingredientservice.exception.EntityNotFoundException;
import eu.innowise.ingredientservice.exception.IngredientLossException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import eu.innowise.ingredientservice.repository.IngredientRepository;
import eu.innowise.ingredientservice.service.IngredientService;
import eu.innowise.ingredientservice.service.InventoryService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static eu.innowise.ingredientservice.util.Constants.INGREDIENT_ALREADY_EXISTS;
import static eu.innowise.ingredientservice.util.Constants.INGREDIENT_NOT_FOUND_BY_ID;
import static eu.innowise.ingredientservice.util.Constants.INGREDIENT_NOT_FOUND_BY_NAME;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private static final int PERCENTAGE_SCALE = 100;

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final InventoryService inventoryService;

    private final Random random = new Random();

    @Override
    @Transactional
    public DetailedIngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest) {

        boolean exists = ingredientRepository.existsByNameIgnoreCase(ingredientCreateRequest.name());
        if (exists) {
            throw new EntityAlreadyExistsException(String.format(INGREDIENT_ALREADY_EXISTS, ingredientCreateRequest.name()));
        }

        Ingredient ingredientToCreate = ingredientMapper.toEntity(ingredientCreateRequest);
        for (UsedInRelationship usedInRelationship : ingredientToCreate.getIngredients()) {
            Ingredient targetIngredient = ingredientRepository.findById(usedInRelationship.getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format(INGREDIENT_NOT_FOUND_BY_ID, usedInRelationship.getId())
                    ));
            usedInRelationship.setIngredient(targetIngredient);
        }

        Ingredient createdIngredient = ingredientRepository.save(ingredientToCreate);

        return ingredientMapper.toDetailedResponse(createdIngredient);
    }


    @Override
    @Transactional(readOnly = true)
    public DetailedIngredientResponse getIngredientById(@NotNull String id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(INGREDIENT_NOT_FOUND_BY_ID, id)));
        return ingredientMapper.toDetailedResponse(ingredient);
    }

    @Override
    @Transactional(readOnly = true)
    public DetailedIngredientResponse getIngredientByName(@NotNull String name) {
        Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException(String.format(INGREDIENT_NOT_FOUND_BY_NAME, name)));
        return ingredientMapper.toDetailedResponse(ingredient);
    }

    @Override
    @Transactional
    public DetailedIngredientResponse mixIngredients(String userId, List<UsedIngredientCreateRequest> usedIngredientCreateRequests) {
        List<Map<String, Object>> relationshipParams = usedIngredientCreateRequests.stream()
                .map(req -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ingredientId", req.ingredientId());
                    map.put("quantity", req.quantity());
                    return map;
                })
                .toList();

        List<Ingredient> matchingIngredient = ingredientRepository.findMatchingIngredient(relationshipParams);

        if (matchingIngredient.size() == 1) {
            return ingredientMapper.toDetailedResponse(matchingIngredient.get(0));
        }

        List<String> lostIngredients = handleChanceOfLoss(userId, usedIngredientCreateRequests);

        if (!lostIngredients.isEmpty()) {
            throw new IngredientLossException("Some ingredients were lost during the process.", lostIngredients);
        }

        throw new EntityNotFoundException("No unique matching ingredient found for the given request.");
    }

    private List<String> handleChanceOfLoss(String userId, List<UsedIngredientCreateRequest> usedIngredientCreateRequests) {
        List<String> usedIngredientIds = usedIngredientCreateRequests.stream()
                .map(UsedIngredientCreateRequest::ingredientId)
                .toList();

        List<Ingredient> usedIngredients = ingredientRepository.findAllById(usedIngredientIds);

        List<String> lostIngredients = new ArrayList<>();

        usedIngredients.forEach(ingredient -> {
            if (random.nextInt(PERCENTAGE_SCALE) < ingredient.getChanceOfLoss()) {
                lostIngredients.add(ingredient.getName());
                inventoryService.deleteIngredientFromUserInventory(userId, ingredient.getId());
            }
        });

        return lostIngredients;
    }
}