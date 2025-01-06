package eu.innowise.ingredientservice.repository;

import eu.innowise.ingredientservice.model.node.Ingredient;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IngredientRepository extends Neo4jRepository<Ingredient, String> {

    @Query("""
        MATCH (i:Ingredient)-[r:USED_IN]->(sub:Ingredient)
        WHERE all(key IN keys($requestedMap) WHERE key IN collect(sub.id) AND $requestedMap[key] = r.quantity)
        RETURN i
        """)
    List<Ingredient> findByIngredients(@Param("requestedMap") Map<String, Integer> ingredientsMap);

//    @Query("""
//        MATCH (ingredient:Ingredient)
//        WHERE toLower(ingredient.name) = toLower($name)
//        RETURN ingredient
//       """)
    Optional<Ingredient> findByNameIgnoreCase(String name);
}
