package eu.innowise.alchemy.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Inventory {

    @Id
    private String userId;
}
