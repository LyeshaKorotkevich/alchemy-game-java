package eu.innowise.ingredientservice.model.relationship;

import eu.innowise.ingredientservice.model.node.Ingredient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@RelationshipProperties
public class UsedInRelationship {

    @RelationshipId
    private String id;

    private int quantity;

    @TargetNode
    private Ingredient ingredient;
}

