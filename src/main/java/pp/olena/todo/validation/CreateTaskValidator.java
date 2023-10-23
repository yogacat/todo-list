package pp.olena.todo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pp.olena.todo.dto.CreateTask;
import java.time.LocalDateTime;

/**
 * Validates {@link CreateTask}, checks that description is present and due date is in the future if present.
 *
 * <p>Will not do: collecting and returning specific exceptions for each issue.</p>
 */
public class CreateTaskValidator implements ConstraintValidator<ValidCreateTask, CreateTask> {

    @Override
    public boolean isValid(CreateTask createTask, ConstraintValidatorContext context) {
        if (createTask == null) {
            return false;
        }

        if (createTask.description() == null || createTask.description().isEmpty()) {
            return false;
        }

        if (createTask.dueTo() == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        return createTask.dueTo().isAfter(now);
    }
}
