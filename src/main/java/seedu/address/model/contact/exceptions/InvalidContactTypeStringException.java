package seedu.address.model.contact.exceptions;

/**
 * Signals that the given string representation of a {@code ContactType} enum is invalid, i.e., there are no
 * enum values with the given string representation.
 */
public class InvalidContactTypeStringException extends RuntimeException {

    /**
     * Constructs an exception for invalid contact type string representations.
     *
     * @param incorrectRepresentation The incorrect string representation used.
     */
    public InvalidContactTypeStringException(String incorrectRepresentation) {
        super(String.format("'%s' is not a valid contact type", incorrectRepresentation));
    }
}
