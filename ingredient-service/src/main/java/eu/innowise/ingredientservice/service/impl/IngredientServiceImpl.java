package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.exception.IngredientNotFoundException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.mapper.UsedIngredientMapper;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import eu.innowise.ingredientservice.repository.IngredientRepository;
import eu.innowise.ingredientservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;
    private final UsedIngredientMapper usedIngredientMapper;

    @Override
    public IngredientResponse createIngredient(IngredientCreateRequest ingredientCreateRequest) {
        Ingredient ingredientToCreate = ingredientMapper.toEntity(ingredientCreateRequest);
        Ingredient createdIngredient = ingredientRepository.save(ingredientToCreate);
        return ingredientMapper.toResponse(createdIngredient);
    }

    @Override
    public IngredientResponse getIngredientById(String id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient with id " + id + " not found"));
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    public IngredientResponse getIngredientByName(String name) {
        Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient with name " + name + " not found"));
        log.info(ingredient.toString());
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    public IngredientResponse mixIngredients(List<UsedIngredientCreateRequest> usedIngredientCreateRequests) {
        List<UsedInRelationship> relationships = usedIngredientCreateRequests.stream()
                .map(request -> {
                    //Ingredient ingredient = ingredientRepository.findById(request.id()).orElseThrow(() ->IngredientNotFoundException.of(Ingredient.class, "id"));
                    UsedInRelationship relationship = usedIngredientMapper.toUsedInRelationship(request);
                    //relationship.setIngredient(ingredient);
                    return relationship;
                })
                .toList();

        List<Ingredient> matchingIngredients = ingredientRepository.findByIngredients(relationships);
        log.info("Found ingredients: {}", matchingIngredients.size());

        if (!matchingIngredients.isEmpty()) {
            return ingredientMapper.toResponse(matchingIngredients.get(0));
        }

        handleChanceOfLoss(usedIngredientCreateRequests);
        throw new IngredientNotFoundException("Can not create new ingredient from provided ingredients");
    }

    private void handleChanceOfLoss(List<UsedIngredientCreateRequest> usedIngredientCreateRequests) {
        Random random = new Random();

        List<String> usedIngredientIds = usedIngredientCreateRequests.stream()
                .map(UsedIngredientCreateRequest::id)
                .toList();

        List<Ingredient> usedIngredients = ingredientRepository.findAllById(usedIngredientIds);

        usedIngredients.forEach(ingredient -> {
            if (random.nextInt(100) < ingredient.getChanceOfLoss()) {
                // TODO delete ingredient from user's inventory
            }
        });
    }
}
