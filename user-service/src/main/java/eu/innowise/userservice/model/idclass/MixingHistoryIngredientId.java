package eu.innowise.userservice.model.idclass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MixingHistoryIngredientId implements Serializable {

    private UUID mixingHistoryId;
    private String ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixingHistoryIngredientId that = (MixingHistoryIngredientId) o;
        return Objects.equals(mixingHistoryId, that.mixingHistoryId) &&
                Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mixingHistoryId, ingredientId);
    }
}