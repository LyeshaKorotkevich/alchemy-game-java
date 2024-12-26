package eu.innowise.alchemy.repository;

import eu.innowise.alchemy.model.node.Ingredient;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface IngredientRepository extends Neo4jRepository<Ingredient, String> {
}
