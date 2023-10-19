package pp.olena.todo.dto;

import lombok.Getter;
import pp.olena.todo.exception.StatusNotFoundException;

/**
 * Status of the task: not done, done, past due.
 */
public enum Status {
    NOT_DONE("not done"), DONE("done"), PAST_DUE("past due");
    @Getter
    private final String value;

    Status(String value) {
        this.value = value;
    }

    public static Status fromString(String value) {
        for (Status status : Status.values()) {
            if (status.value != null && status.value.equals(value)) {
                return status;
            }
        }

        throw new StatusNotFoundException("Status with the value " + value + " does not exist.");
    }
}
