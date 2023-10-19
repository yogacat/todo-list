package pp.olena.todo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CreateTaskValidator.class)
public @interface ValidCreateTask {

    String message() default "Task cannot be created, description must be present and due date must be in the future if present.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
