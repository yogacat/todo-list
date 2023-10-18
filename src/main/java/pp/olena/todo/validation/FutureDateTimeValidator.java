package pp.olena.todo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * Validator for the annotation {@link FutureDateTime}, makes sure that due date is not in the past if it is present. No
 * date is a valid date.
 */
public class FutureDateTimeValidator implements ConstraintValidator<FutureDateTime, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        if (localDateTime == null) {
            return true;
        }
        LocalDateTime now = LocalDateTime.now();
        return localDateTime.isAfter(now);
    }
}
