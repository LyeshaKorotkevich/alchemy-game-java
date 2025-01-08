package eu.innowise.ingredientservice.repository;

import eu.innowise.ingredientservice.model.node.Ingredient;
import eu.innowise.ingredientservice.model.relationship.UsedInRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends Neo4jRepository<Ingredient, String> {

    List<Ingredient> findByIngredients(List<UsedInRelationship> ingredients);

    Optional<Ingredient> findByNameIgnoreCase(String name);
}
