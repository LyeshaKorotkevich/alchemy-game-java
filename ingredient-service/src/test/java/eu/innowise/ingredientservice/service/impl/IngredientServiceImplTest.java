package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.IngredientRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientRequest;
import eu.innowise.ingredientservice.dto.response.IngredientResponse;
import eu.innowise.ingredientservice.exception.NotFoundException;
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
            UsedIngredientRequest usedIngredientRequest1 = new UsedIngredientRequest("1", 1);
            Ingredient ingredient1 = IngredientTestBuilder.builder()
                    .withId("1")
                    .withName("water")
                    .build()
                    .getIngredient();

            UsedIngredientRequest usedIngredientRequest2 = new UsedIngredientRequest("2", 1);
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

            List<UsedIngredientRequest> usedIngredientRequests = List.of(usedIngredientRequest1, usedIngredientRequest2);

            doReturn(relationship1)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientRequest1);

            doReturn(relationship2)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientRequest2);

            doReturn(List.of(ingredient3))
                    .when(ingredientRepository)
                    .findByIngredients(List.of(relationship1, relationship2));

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient3);

            // when
            Optional<IngredientResponse> actual = ingredientService.mixIngredients(usedIngredientRequests);

            // then
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
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
        void shouldThrowException_whenIngredientByIdNotFound() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getId());

            // when & then
            assertThrows(
                    NotFoundException.class,
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
        void shouldThrowException_whenIngredientByNameNotFound() {
            // given
            Ingredient ingredient = IngredientTestBuilder.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getName());

            // when & then
            assertThrows(
                    NotFoundException.class,
                    () -> ingredientService.getIngredientByName(ingredient.getName())
            );
        }
    }

    @Nested
    class MixIngredients {

        @Test
        void shouldReturnIngredientWhenMatchingIngredientsFound() {
            // given
            UsedIngredientRequest usedIngredientRequest1 = new UsedIngredientRequest("1", 1);
            Ingredient ingredient1 = IngredientTestBuilder.builder()
                    .withId("1")
                    .withName("water")
                    .build()
                    .getIngredient();

            UsedIngredientRequest usedIngredientRequest2 = new UsedIngredientRequest("2", 1);
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

            List<UsedIngredientRequest> usedIngredientRequests = List.of(usedIngredientRequest1, usedIngredientRequest2);

            doReturn(List.of(ingredient3))
                    .when(ingredientRepository)
                    .findByIngredients(anyList());

            doReturn(expected)
                    .when(ingredientMapper)
                    .toResponse(ingredient3);

            // when
            Optional<IngredientResponse> actual = ingredientService.mixIngredients(usedIngredientRequests);

            // then
            assertTrue(actual.isPresent());
            assertEquals(expected, actual.get());
        }

        @Test
        void shouldReturnEmptyWhenNoMatchingIngredientsFound() {
            // given
            UsedIngredientRequest usedIngredientRequest = new UsedIngredientRequest("1", 1);
            List<UsedIngredientRequest> usedIngredientRequests = List.of(usedIngredientRequest);

            UsedInRelationship relationship = new UsedInRelationship("1", 1, null);

            doReturn(relationship)
                    .when(usedIngredientMapper)
                    .toUsedInRelationship(usedIngredientRequest);

            doReturn(List.of())
                    .when(ingredientRepository)
                    .findByIngredients(List.of(relationship));

            // when
            Optional<IngredientResponse> actual = ingredientService.mixIngredients(usedIngredientRequests);

            // then
            assertFalse(actual.isPresent());
        }
    }

}