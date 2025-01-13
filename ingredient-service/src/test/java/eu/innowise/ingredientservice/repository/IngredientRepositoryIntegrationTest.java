package eu.innowise.ingredientservice.repository;

import eu.innowise.ingredientservice.dto.request.UsedIngredientCreateRequest;
import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataNeo4jTest
class IngredientRepositoryIntegrationTest {

    static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>(DockerImageName.parse("neo4j:4.4"))
            .withAdminPassword(null);;

    @Autowired
    IngredientRepository ingredientRepository;

    @BeforeAll
    static void setUp() {
        neo4jContainer.start();
        System.setProperty("spring.neo4j.uri", neo4jContainer.getBoltUrl());
    }

    @BeforeEach
    void populateDatabase() {
        Ingredient ingredient1 = new Ingredient("1", "Water", (short)10, 1, null);
        ingredientRepository.save(ingredient1);
        Ingredient ingredient2 = new Ingredient("2", "Herb", (short)10, 1, null);
        ingredientRepository.save(ingredient2);
        ingredientRepository.save(new Ingredient("3", "Potion", (short)15, 2, List.of(
                new UsedInRelationship("1", 1, ingredient1),
                new UsedInRelationship("2", 1, ingredient2)
        )));
    }


    @Test
    void canEstablishConnection() {
        assertTrue(neo4jContainer.isCreated());
        assertTrue(neo4jContainer.isRunning());
    }

    @Test
    void testFindByNameIgnoreCase() {
        // given
        Ingredient expected = new Ingredient("2", "hErb", (short)10, 1, null);

        // when
        Optional<Ingredient> actual = ingredientRepository.findByNameIgnoreCase("herb");

        // then
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testFindMatchingIngredient() {
        // given
//        Ingredient ingredient1 = new Ingredient("1", "water", (short)10, 1, null);
//        Ingredient ingredient2 = new Ingredient("2", "hErb", (short)10, 1, null);

        // when
        List<Map<String, Object>> relationships = List.of(
                //Map.of("ingredientId", "1", "quantity", 1),
                Map.of("ingredientId", "2", "quantity", 1)
        );

        List<Ingredient> actual = ingredientRepository.findMatchingIngredient(relationships);

        // then
        assertFalse(actual.isEmpty());
    }
}