/**
 * Exception for Locations that are out of constraint boundaries.
 * 
 * @author benhughes
 *
 */
public class LocationOutOfBoundsException extends RuntimeException {

    /**
     * Constructs a LocationOutOfBoundsException with no message.
     */
    public LocationOutOfBoundsException() {
        super();
    }



    /**
     * Constructs a LocationOutOfBoundsException with the detail message.
     * 
     * @param message The exception message.
     */
    public LocationOutOfBoundsException( String message ) {
        super( message );
    }
}
