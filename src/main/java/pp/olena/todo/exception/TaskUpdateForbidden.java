package pp.olena.todo.exception;

public class TaskUpdateForbidden extends RuntimeException {

    public TaskUpdateForbidden(String message) {
        super(message);
    }
}
