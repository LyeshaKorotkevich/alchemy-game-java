package eu.innowise.ingredientservice.repository;

import eu.innowise.ingredientservice.model.node.Inventory;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface InventoryRepository extends Neo4jRepository<Inventory, String> {

    Optional<Inventory> findByUserId(String userId);
}
