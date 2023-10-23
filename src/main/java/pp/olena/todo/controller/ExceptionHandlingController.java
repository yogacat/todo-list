package pp.olena.todo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pp.olena.todo.dto.ErrorMessage;
import pp.olena.todo.exception.NoChangesException;
import pp.olena.todo.exception.StatusNotFoundException;
import pp.olena.todo.exception.TaskNotFoundException;
import pp.olena.todo.exception.TaskUpdateForbiddenException;

/**
 * Converts exceptions from the application into response entity.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(StatusNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleWrongStatus(StatusNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleWrongTaskId(TaskNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(TaskUpdateForbiddenException.class)
    public ResponseEntity<ErrorMessage> handleForbiddenUpdate(TaskUpdateForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(ex.getMessage()));
    }

    @ExceptionHandler(NoChangesException.class)
    public ResponseEntity<ErrorMessage> handleNoChanges(NoChangesException ex) {
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ErrorMessage(ex.getMessage()));
    }
}
