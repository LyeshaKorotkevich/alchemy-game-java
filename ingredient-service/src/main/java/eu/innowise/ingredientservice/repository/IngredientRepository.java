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
        MATCH (i:Ingredient)-[r:USED_IN]->(target:Ingredient)
        WHERE ALL(relationship IN $relationships WHERE
            i.id = relationship.ingredientId AND toInteger(r.quantity) = toInteger(relationship.quantity)
        )
        RETURN target
    """)
    List<Ingredient> findMatchingIngredient(@Param("relationships") List<Map<String, Object>> relationships);

    Optional<Ingredient> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
}
