package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.response.UserInventoryResponse;
import eu.innowise.ingredientservice.model.node.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface InventoryMapper {

    //UserInventoryResponse toResponse(Inventory inventory);
}
