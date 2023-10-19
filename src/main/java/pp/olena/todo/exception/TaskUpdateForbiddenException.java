package pp.olena.todo.exception;

public class TaskUpdateForbiddenException extends RuntimeException {

    public TaskUpdateForbiddenException(String message) {
        super(message);
    }
}
