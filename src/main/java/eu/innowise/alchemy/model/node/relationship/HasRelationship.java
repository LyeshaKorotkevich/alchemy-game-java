package eu.innowise.alchemy.model.node.relationship;

import eu.innowise.alchemy.model.node.Ingredient;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

@Getter
@Setter
@RelationshipProperties
public class HasRelationship {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private int quantity;

    @TargetNode
    private Ingredient ingredient;
}
