package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.response.InventoryResponse;
import eu.innowise.ingredientservice.model.node.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    @Mapping(target = "userId", ignore = true)
    InventoryResponse toResponse(Inventory inventory);
}
