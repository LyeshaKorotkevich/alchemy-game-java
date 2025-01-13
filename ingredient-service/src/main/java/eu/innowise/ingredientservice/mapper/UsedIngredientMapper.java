package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.UsedIngredientResponse;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UsedIngredientMapper {

    @Mapping(target = "ingredientId", source = "ingredient.id")
    @Mapping(target = "name", source = "ingredient.name")
    UsedIngredientResponse toResponse(UsedInRelationship usedInRelationship);

    @Mapping(target = "id", source = "ingredientId")
    @Mapping(target = "ingredient", ignore = true)
    UsedInRelationship toUsedInRelationship(UsedIngredientCreateRequest usedIngredientCreateRequest);
}