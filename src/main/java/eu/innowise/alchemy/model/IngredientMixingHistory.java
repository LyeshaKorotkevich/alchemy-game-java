package eu.innowise.alchemy.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ingredient_mixing_history")
public class IngredientMixingHistory {

    @Column(name = "is_lost")
    private boolean isLost;
}
