package eu.innowise.userservice.model.entity;

import eu.innowise.userservice.model.idclass.MixingHistoryIngredientId;
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
@IdClass(MixingHistoryIngredientId.class)
@Table(name = "mixing_history_ingredient")
public class MixingHistoryIngredient {

    @Id
    @Column(name = "mixing_history_id")
    private UUID mixingHistoryId;

    @Id
    @Column(name = "ingredient_id")
    private String ingredientId;

    @Column(name = "is_lost", nullable = false)
    private Boolean isLost;

    @ManyToOne
    @JoinColumn(name = "mixing_history_id", nullable = false)
    private MixingHistory mixingHistory;
}
