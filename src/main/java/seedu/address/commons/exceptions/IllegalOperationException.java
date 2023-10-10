package seedu.address.commons.exceptions;

/**
 * Exception thrown when attempting to make illegal.
 */
public class IllegalOperationException extends Exception {
    /**
     * @param message that informs the user that it has attempted an illegal operation.
     */
    public IllegalOperationException(String message) {
        super(message);
    }

    /**
     * @param message that informs the user that it has attempted an illegal operation.
     * @param cause of the main exception.
     */
    public IllegalOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
