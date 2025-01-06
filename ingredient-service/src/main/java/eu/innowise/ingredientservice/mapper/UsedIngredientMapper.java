package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.response.UsedIngredientResponse;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsedIngredientMapper {

    @Mapping(target = "name", source = "ingredient.name")
    UsedIngredientResponse toResponse(UsedInRelationship usedInRelationship);
}
