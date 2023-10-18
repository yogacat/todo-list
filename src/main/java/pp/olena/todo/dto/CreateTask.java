package pp.olena.todo.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import pp.olena.todo.validation.FutureDateTime;
import java.time.LocalDateTime;

/**
 * Data object that contains mandatory parameters for the validation when the task is being created.
 */
public record CreateTask(@NotBlank String description, @Nullable @FutureDateTime LocalDateTime dueTo) {

}
