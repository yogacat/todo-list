package pp.olena.todo.dto;

import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

/**
 * Filter with a potential of making more complex calls. When all the fields are null all the tasks will be returned.
 * Otherwise, filtering will happen by all specified fields (AND).
 */
public record TaskFilter(@Pattern(regexp = "^(not done|done|past due)$") String status, LocalDateTime dueTo) {

}
