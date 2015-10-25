/**
 * Exception throwable when an operation is performed on a component
 * with invalid state.
 * 
 * @author benhughes
 *
 */
public class InvalidStateException extends RuntimeException {

    public InvalidStateException() {
    }

    public InvalidStateException(String message) {
        super(message);
    }

}
