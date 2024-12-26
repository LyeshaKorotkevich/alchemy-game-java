package eu.innowise.alchemy.model.idclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class IngredientMixingHistoryId implements Serializable {

    private UUID mixingHistoryId;
    private String ingredientId;
}