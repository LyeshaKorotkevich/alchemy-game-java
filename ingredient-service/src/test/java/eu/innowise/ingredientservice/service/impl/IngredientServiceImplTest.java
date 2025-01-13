package eu.innowise.ingredientservice.service.impl;

import eu.innowise.ingredientservice.dto.request.IngredientCreateRequest;
import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.dto.response.DetailedIngredientResponse;
import eu.innowise.ingredientservice.exception.EntityNotFoundException;
import eu.innowise.ingredientservice.mapper.IngredientMapper;
import eu.innowise.ingredientservice.mapper.UsedIngredientMapper;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import eu.innowise.ingredientservice.repository.IngredientRepository;
import eu.innowise.ingredientservice.testutil.IngredientTestData;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    IngredientMapper ingredientMapper;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Nested
    class CreateIngredient {

        @Test
        void shouldSuccessfullyCreateIngredient() {
            // given
            IngredientCreateRequest ingredientCreateRequest = IngredientTestData.builder().build().getRequest();
            Ingredient ingredient = IngredientTestData.builder().build().getIngredient();

            when(ingredientRepository.existsByNameIgnoreCase(ingredientCreateRequest.name()))
                    .thenReturn(false);

            when(ingredientMapper.toEntity(ingredientCreateRequest))
                    .thenReturn(ingredient);

            when(ingredientRepository.save(ingredient))
                    .thenReturn(ingredient);

            DetailedIngredientResponse response = IngredientTestData.builder().build().getResponse();
            when(ingredientMapper.toDetailedResponse(ingredient))
                    .thenReturn(response);

            // when
            DetailedIngredientResponse actual = ingredientService.createIngredient(ingredientCreateRequest);

            // then
            assertNotNull(actual);
            assertEquals(ingredientCreateRequest.name(), actual.name());

            verify(ingredientRepository).existsByNameIgnoreCase(ingredientCreateRequest.name());
            verify(ingredientRepository).save(ingredient);
            verify(ingredientMapper).toDetailedResponse(ingredient);
        }
    }


    @Nested
    class GetIngredientById {

        @Test
        void shouldReturnIngredientById() {
            // given
            Ingredient ingredient = IngredientTestData.builder().build().getIngredient();
            DetailedIngredientResponse expected = IngredientTestData.builder().build().getResponse();

            doReturn(expected)
                    .when(ingredientMapper)
                    .toDetailedResponse(ingredient);

            doReturn(Optional.of(ingredient))
                    .when(ingredientRepository)
                    .findById(ingredient.getId());

            // when
            DetailedIngredientResponse actual = ingredientService.getIngredientById(ingredient.getId());

            // then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowExceptionWhenIngredientByIdNotFound() {
            // given
            Ingredient ingredient = IngredientTestData.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getId());

            // when & then
            assertThrows(
                    EntityNotFoundException.class,
                    () -> ingredientService.getIngredientByName(ingredient.getId())
            );
        }
    }

    @Nested
    class GetIngredientByName {

        @Test
        void shouldReturnIngredientByName() {
            // given
            Ingredient ingredient = IngredientTestData.builder().build().getIngredient();
            DetailedIngredientResponse expected = IngredientTestData.builder().build().getResponse();

            doReturn(expected)
                    .when(ingredientMapper)
                    .toDetailedResponse(ingredient);

            doReturn(Optional.of(ingredient))
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getName());

            // when
            DetailedIngredientResponse actual = ingredientService.getIngredientByName(ingredient.getName());

            // then
            assertNotNull(actual);
            assertEquals(expected, actual);
        }

        @Test
        void shouldThrowExceptionWhenIngredientByNameNotFound() {
            // given
            Ingredient ingredient = IngredientTestData.builder().build().getIngredient();

            doReturn(Optional.empty())
                    .when(ingredientRepository)
                    .findByNameIgnoreCase(ingredient.getName());

            // when & then
            assertThrows(
                    EntityNotFoundException.class,
                    () -> ingredientService.getIngredientByName(ingredient.getName())
            );
        }
    }

//    @Nested
//    class MixIngredients {
//
//        @Test
//        void shouldReturnIngredientWhenMatchingIngredientsFound() {
//            // given
//            UsedIngredientCreateRequest usedIngredientCreateRequest1 = new UsedIngredientCreateRequest("1", 1);
//            Ingredient ingredient1 = IngredientTestData.builder()
//                    .withId("1")
//                    .withName("water")
//                    .build()
//                    .getIngredient();
//
//            UsedIngredientCreateRequest usedIngredientCreateRequest2 = new UsedIngredientCreateRequest("2", 1);
//            Ingredient ingredient2 = IngredientTestData.builder()
//                    .withId("2")
//                    .withName("herb")
//                    .build()
//                    .getIngredient();
//
//            Ingredient ingredient3 = IngredientTestData.builder()
//                    .withIngredients(List.of(
//                            new UsedInRelationship("1", 1, ingredient1),
//                            new UsedInRelationship("2", 1, ingredient2)
//                    ))
//                    .build()
//                    .getIngredient();
//
//            DetailedIngredientResponse expected = IngredientTestData.builder()
//                    .withIngredients(List.of(
//                            new UsedInRelationship("1", 1, ingredient1),
//                            new UsedInRelationship("2", 1, ingredient2)
//                    ))
//                    .build()
//                    .getResponse();
//
//            List<UsedIngredientCreateRequest> usedIngredientCreateRequests = List.of(usedIngredientCreateRequest1, usedIngredientCreateRequest2);
//
//            doReturn(List.of(ingredient3))
//                    .when(ingredientRepository)
//                    .findByIngredients(anyList());
//
//            doReturn(expected)
//                    .when(ingredientMapper)
//                    .toDetailedResponse(ingredient3);
//
//            // when
//            DetailedIngredientResponse actual = ingredientService.mixIngredients(usedIngredientCreateRequests);
//
//            // then
//            assertNotNull(actual);
//            assertEquals(expected, actual);
//        }
//
//        @Test
//        void shouldThrowExceptionWhenNoMatchingIngredientsFound() {
//            // given
//            UsedIngredientCreateRequest usedIngredientCreateRequest = new UsedIngredientCreateRequest("1", 1);
//            List<UsedIngredientCreateRequest> usedIngredientCreateRequests = List.of(usedIngredientCreateRequest);
//
//            UsedInRelationship relationship = new UsedInRelationship("1", 1, null);
//
//            doReturn(relationship)
//                    .when(usedIngredientMapper)
//                    .toUsedInRelationship(usedIngredientCreateRequest);
//
//            doReturn(List.of())
//                    .when(ingredientRepository)
//                    .findByIngredients(List.of(relationship));
//
//            // when & then
//            assertThrows(
//                    EntityNotFoundException.class,
//                    () -> ingredientService.mixIngredients(usedIngredientCreateRequests)
//            );
//        }
//    }

}