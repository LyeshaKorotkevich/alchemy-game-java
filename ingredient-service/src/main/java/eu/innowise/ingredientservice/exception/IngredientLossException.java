package eu.innowise.ingredientservice.exception;

import lombok.Getter;

import java.util.List;

public class IngredientLossException extends RuntimeException {

    @Getter
    private final List<String> lostIngredients;

    public IngredientLossException(String message, List<String> lostIngredients) {
        super(message);
        this.lostIngredients = lostIngredients;
    }
}
