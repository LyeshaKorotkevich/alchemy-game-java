package eu.innowise.ingredientservice.model.relationship;

import eu.innowise.ingredientservice.model.node.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RelationshipProperties
public class UsedInRelationship {

    @RelationshipId
    private String id;

    private int quantity;

    @TargetNode
    private Ingredient ingredient;
}

