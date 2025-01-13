package eu.innowise.ingredientservice.exception.handler;

import eu.innowise.ingredientservice.exception.EntityAlreadyExistsException;
import eu.innowise.ingredientservice.exception.EntityNotFoundException;
import eu.innowise.ingredientservice.exception.IngredientLossException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<String> handleEntityAlreadyExistsException(EntityAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    @ExceptionHandler(IngredientLossException.class)
    public ResponseEntity<Map<String, Object>> handleIngredientLossException(IngredientLossException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("lostIngredients", ex.getLostIngredients());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }
}
