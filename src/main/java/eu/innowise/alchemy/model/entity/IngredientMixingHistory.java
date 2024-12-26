package eu.innowise.alchemy.model.entity;

import eu.innowise.alchemy.model.idclass.IngredientMixingHistoryId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(IngredientMixingHistoryId.class)
@Table(name = "ingredient_mixing_history")
public class IngredientMixingHistory {

    @Id
    @Column(name = "mixing_history_id")
    private UUID mixingHistoryId;

    @Id
    @Column(name = "ingredient_id")
    private String ingredientId;

    @Column(name = "is_lost")
    private boolean isLost;

    @ManyToOne
    @JoinColumn(name = "mixing_history_id")
    private MixingHistory mixingHistory;
}
