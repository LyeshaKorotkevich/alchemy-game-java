package eu.innowise.ingredientservice.repository;

import eu.innowise.ingredientservice.model.node.Ingredient;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface IngredientRepository extends Neo4jRepository<Ingredient, String> {
}
