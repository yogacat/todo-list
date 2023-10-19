package pp.olena.todo.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates data that came in the rest interface before updating the task.
 */
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = UpdateTaskValidator.class)
public @interface ValidUpdateTask {

    String message() default "Task cannot be updated, either description or status must be present. Status can be done or not done.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
