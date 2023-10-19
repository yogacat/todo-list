package pp.olena.todo.exception;

/**
 * Indicates that this status does not exist.
 */
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException(String message) {
        super(message);
    }
}
