package pp.olena.todo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pp.olena.todo.dto.Status;
import pp.olena.todo.dto.UpdateTask;

/**
 * Makes sure that any of the fields, that can be updated, is present. Fields that can be updated: description, status.
 * Does not validate if the task is past due date, only validates that any of the mandatory fields is present.
 */
public class UpdateTaskValidator implements ConstraintValidator<ValidUpdateTask, UpdateTask> {

    @Override
    public boolean isValid(UpdateTask task, ConstraintValidatorContext context) {
        if (task == null) {
            return false;
        }
        return !isBlank(task.description()) || isValidStatus(task.status());
    }

    private boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    private boolean isValidStatus(String statusValue) {
        if (isBlank(statusValue)) {
            return false;
        }
        Status status = Status.fromString(statusValue);
        return Status.NOT_DONE.equals(status) || Status.DONE.equals(status);
    }
}
