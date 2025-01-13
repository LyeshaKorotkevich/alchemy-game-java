package eu.innowise.ingredientservice.mapper;

import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.UsedIngredientResponse;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UsedIngredientMapperTest {

    UsedIngredientMapper usedIngredientMapper = Mappers.getMapper(UsedIngredientMapper.class);

    @Test
    void testToResponse() {
        // given
        Ingredient ingredient = new Ingredient();
        ingredient.setId("1234");
        ingredient.setName("Sugar");

        UsedInRelationship relationship = new UsedInRelationship();
        relationship.setIngredient(ingredient);

        // when
        UsedIngredientResponse response = usedIngredientMapper.toResponse(relationship);

        // then
        assertNotNull(response);
        assertEquals("1234", response.ingredientId());
        assertEquals("Sugar", response.name());
    }

    @Test
    void testToUsedInRelationship() {
        // given
        UsedIngredientCreateRequest request = new UsedIngredientCreateRequest("5678", 2);

        // when
        UsedInRelationship relationship = usedIngredientMapper.toUsedInRelationship(request);

        // then
        assertNotNull(relationship);
        assertNull(relationship.getId());
        assertNotNull(relationship.getIngredient());
        assertEquals(2, relationship.getQuantity());
    }
}