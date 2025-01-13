package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;
import eu.innowise.ingredientservice.dto.response.SummaryIngredientResponse;
import eu.innowise.ingredientservice.model.node.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = UsedIngredientMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IngredientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredients", source = "usedIngredients")
    Ingredient toEntity(IngredientCreateRequest ingredientCreateRequest);

    @Mapping(target = "ingredientId", source = "id")
    @Mapping(target = "usedIngredients", source = "ingredients")
    DetailedIngredientResponse toDetailedResponse(Ingredient ingredient);

    @Mapping(target = "ingredientId", source = "id")
    SummaryIngredientResponse toSummeryResponse(Ingredient ingredient);
}
