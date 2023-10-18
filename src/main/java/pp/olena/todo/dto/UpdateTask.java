package pp.olena.todo.dto;

/**
 * Data object that contains mandatory parameters for the validation when the task is being updated.
 */
public record UpdateTask(String description, String status) {

}
