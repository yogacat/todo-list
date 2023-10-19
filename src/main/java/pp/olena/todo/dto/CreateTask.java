package pp.olena.todo.dto;

import java.time.LocalDateTime;

/**
 * Data object that contains mandatory parameters for the validation when the task is being created.
 */
public record CreateTask(String description, LocalDateTime dueTo) {

}
