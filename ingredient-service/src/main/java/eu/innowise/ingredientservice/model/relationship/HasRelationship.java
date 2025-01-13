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
public class HasRelationship {

    @RelationshipId
    private Long id;

    private Integer quantity;

    @TargetNode
    private Ingredient ingredient;
}
