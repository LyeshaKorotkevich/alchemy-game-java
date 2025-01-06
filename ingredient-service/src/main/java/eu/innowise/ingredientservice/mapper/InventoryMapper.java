package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.response.InventoryResponse;
import eu.innowise.ingredientservice.model.node.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryMapper {

    InventoryResponse toResponse(Inventory inventory);
}
