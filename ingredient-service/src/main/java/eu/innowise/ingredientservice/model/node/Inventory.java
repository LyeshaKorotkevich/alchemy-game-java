package eu.innowise.ingredientservice.model.node;

import eu.innowise.ingredientservice.model.relationship.HasRelationship;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
public class Inventory {

    @Id
    private String userId;

    @Relationship(type = "HAS", direction = Relationship.Direction.OUTGOING)
    private List<HasRelationship> ingredients = new ArrayList<>();
}
