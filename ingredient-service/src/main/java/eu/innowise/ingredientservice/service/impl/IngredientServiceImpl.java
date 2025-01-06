package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.exception.NotFoundException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import eu.innowise.ingredientservice.repository.IngredientRepository;
import eu.innowise.ingredientservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    public IngredientResponse createIngredient(IngredientRequest ingredientRequest) {
        Ingredient ingredientToCreate = ingredientMapper.toEntity(ingredientRequest);
        Ingredient createdIngredient = ingredientRepository.save(ingredientToCreate);
        return ingredientMapper.toResponse(createdIngredient);
    }

    @Override
    public IngredientResponse getIngredientById(String id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of(Ingredient.class, id));
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    public IngredientResponse getIngredientByName(String name) {
        Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> NotFoundException.of(Ingredient.class, name));
        log.info(ingredient.toString());
        return ingredientMapper.toResponse(ingredient);
    }

    @Override
    public Optional<IngredientResponse> mixIngredients(List<UsedIngredientRequest> usedIngredientRequests) {

        List<Ingredient> allIngredients = ingredientRepository.findAll();

        // TODO change with cypher query
        Map<String, Integer> requestedMap = usedIngredientRequests.stream()
                .collect(
                        Collectors.toMap(UsedIngredientRequest::id, UsedIngredientRequest::quantity)
                );

        for (Ingredient ingredient : allIngredients) {

            Map<String, Integer> ingredientMap = ingredient.getIngredients().stream()
                    .collect(
                            Collectors.toMap(ingr -> ingr.getIngredient().getId(), UsedInRelationship::getQuantity)
                    );

            if (ingredientMap.equals(requestedMap)) {
                IngredientResponse ingredientResponse = ingredientMapper.toResponse(ingredient);
                return Optional.of(ingredientResponse);
            }
        }

        handleChanceOfLoss(usedIngredientRequests);
        return Optional.empty();
    }

    private void handleChanceOfLoss(List<UsedIngredientRequest> usedIngredientRequests) {
        Random random = new Random();

        List<String> usedIngredientIds = usedIngredientRequests.stream()
                .map(UsedIngredientRequest::id)
                .toList();

        List<Ingredient> usedIngredients = ingredientRepository.findAllById(usedIngredientIds);

        usedIngredients.forEach(ingredient -> {
            if (random.nextInt(100) < ingredient.getChanceOfLoss()) {
                ingredientRepository.deleteById(ingredient.getId());
            }
        });
    }
}
