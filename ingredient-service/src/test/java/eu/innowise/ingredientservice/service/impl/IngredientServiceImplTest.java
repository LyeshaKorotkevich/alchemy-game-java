package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.exception.IngredientNotFoundException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.mapper.UsedIngredientMapper;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import eu.innowise.ingredientservice.repository.IngredientRepository;
import eu.innowise.ingredientservice.testutil.builders.IngredientTestBuilder;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientMapper ingredientMapper;

    @Mock
    UsedIngredientMapper usedIngredientMapper;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Nested
    class CreateIngredient {

        @Test
        void shouldReturnIngredientWhenMatchingIngredientsFound() {
            // given
            UsedIngredientCreateRequest usedIngredientCreateRequest1 = new UsedIngredientCreateRequest("1", 1);
            Ingredient ingredient1 = IngredientTestBuilder.builder()
                    .withId("1")
                    .withName("water")
                    .build()
                    .getIngredient();

            UsedIngredientCreateRequest usedIngredientCreateRequest2 = new UsedIngredientCreateRequest("2", 1);
            Ingredient ingredient2 = IngredientTestBuilder.builder()
                    .withId("2")
                    .withName("herb")
                    .build()
                    .getIngredient();

            UsedInRelationship relationship1 = new UsedInRelationship("1", 1, ingredient1);
            UsedInRelationship relationship2 = new UsedInRelationship("2", 1, ingredient2);

            Ingredient ingredient3 = IngredientTestBuilder.builder()
                    .withIngredients(List.of(relationship1, relationship2))
                    .build()
                    .getIngredient();

            IngredientResponse expected = IngredientTestBuilder.builder()
                    .withIngredients(List.of(relationship1, relationship2))
                    .build()
                    .getResponse();

            List<UsedIngredientCreateRequest> usedIngredientCreateRequests = List.of(usedIngredientCreateRequest1, usedIngredientCreateRequest2);

            doReturn(relationship1)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientCreateRequest1);

            doReturn(relationship2)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientCreateRequest2);

            doReturn(List.of(ingredient3))
                    .when(ingredientRepository)
                    .findByIngredients(List.of(relationship1, relationship2));

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient3);

            // when
            IngredientResponse actual = ingredientService.mixIngredients(usedIngredientCreateRequests);

            // then
            assertEquals(expected, actual);
        }
    }


    @Nested
    class GetIngredientById {

        @Test
        void shouldReturnIngredientById() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();
            IngredientResponse expected = IngredientTestBuilder.builder().build().getResponse();

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient);

            doReturn(Optional.of(ingredient))
                    .when(ingredientRepository)
                    .findById(ingredient.getId());

            // when
            IngredientResponse actual = ingredientService.getIngredientById(ingredient.getId());

            // then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowExceptionWhenIngredientByIdNotFound() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getId());

            // when & then
            assertThrows(
                    IngredientNotFoundException.class,
                    () -> ingredientService.getIngredientByName(ingredient.getId())
            );
        }
    }

    @Nested
    class GetIngredientByName {

        @Test
        void shouldReturnIngredientByName() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();
            IngredientResponse expected = IngredientTestBuilder.builder().build().getResponse();

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient);

            doReturn(Optional.of(ingredient))
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getName());

            // when
            IngredientResponse actual = ingredientService.getIngredientByName(ingredient.getName());

            // then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowExceptionWhenIngredientByNameNotFound() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getName());

            // when & then
            assertThrows(
                    IngredientNotFoundException.class,
                    () -> ingredientService.getIngredientByName(ingredient.getName())
            );
        }
    }

    @Nested
    class MixIngredients {

        @Test
        void shouldReturnIngredientWhenMatchingIngredientsFound() {
            // given
            UsedIngredientCreateRequest usedIngredientCreateRequest1 = new UsedIngredientCreateRequest("1", 1);
            Ingredient ingredient1 = IngredientTestBuilder.builder()
                    .withId("1")
                    .withName("water")
                    .build()
                    .getIngredient();

            UsedIngredientCreateRequest usedIngredientCreateRequest2 = new UsedIngredientCreateRequest("2", 1);
            Ingredient ingredient2 = IngredientTestBuilder.builder()
                    .withId("2")
                    .withName("herb")
                    .build()
                    .getIngredient();

            Ingredient ingredient3 = IngredientTestBuilder.builder()
                    .withIngredients(List.of(
                            new UsedInRelationship("1", 1, ingredient1),
                            new UsedInRelationship("2", 1, ingredient2)
                    ))
                    .build()
                    .getIngredient();

            IngredientResponse expected = IngredientTestBuilder.builder()
                    .withIngredients(List.of(
                            new UsedInRelationship("1", 1, ingredient1),
                            new UsedInRelationship("2", 1, ingredient2)
                    ))
                    .build()
                    .getResponse();

            List<UsedIngredientCreateRequest> usedIngredientCreateRequests = List.of(usedIngredientCreateRequest1, usedIngredientCreateRequest2);

            doReturn(List.of(ingredient3))
                    .when(ingredientRepository)
                    .findByIngredients(anyList());

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient3);

            // when
            IngredientResponse actual = ingredientService.mixIngredients(usedIngredientCreateRequests);

            // then
            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowExceptionWhenNoMatchingIngredientsFound() {
            // given
            UsedIngredientCreateRequest usedIngredientCreateRequest = new UsedIngredientCreateRequest("1", 1);
            List<UsedIngredientCreateRequest> usedIngredientCreateRequests = List.of(usedIngredientCreateRequest);

            UsedInRelationship relationship = new UsedInRelationship("1", 1, null);

            doReturn(relationship)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientCreateRequest);

            doReturn(List.of())
                    .when(ingredientRepository)
                    .findByIngredients(List.of(relationship));

            // when & then
            assertThrows(IngredientNotFoundException.class, () -> ingredientService.mixIngredients(usedIngredientCreateRequests));
        }
    }

}