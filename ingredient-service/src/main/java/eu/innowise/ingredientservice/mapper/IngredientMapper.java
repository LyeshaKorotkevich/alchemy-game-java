package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.model.node.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {

    Ingredient toEntity(IngredientRequest ingredientRequest);

    IngredientResponse toResponse(Ingredient ingredient);
}
