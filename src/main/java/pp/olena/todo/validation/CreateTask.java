package pp.olena.todo.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Validates data that came in the rest interface before creating the task.
 */
@Target({ElementType.PARAMETER, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CreateTaskValidator.class)
public @interface CreateTask {
    String message() default "Task cannot be created, data is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
