package eu.innowise.userservice.model.idclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MixingHistoryIngredientId implements Serializable {

    private UUID mixingHistoryId;
    private String ingredientId;
}