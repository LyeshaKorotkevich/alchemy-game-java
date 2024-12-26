package eu.innowise.alchemy.model.node;

import eu.innowise.alchemy.model.node.relationship.UsedInRelationship;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.ArrayList;
import java.util.List;

@Node
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(UUIDStringGenerator.class)
    private String id;

    private String name;

    private double chanceOfLoss;

    private int price;

    @Relationship(type = "USED_IN", direction = Relationship.Direction.INCOMING)
    private List<UsedInRelationship> ingredients = new ArrayList<>();
}
