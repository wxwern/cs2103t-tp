package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Organisation's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPosition(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String applicationStatus;

    /**
     * Constructs a {@code Position}.
     *
     * @param status A valid position.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidPosition(status), MESSAGE_CONSTRAINTS);
        applicationStatus = status;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidPosition(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return applicationStatus;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Position)) {
            return false;
        }

        Position otherPosition = (Position) other;
        return applicationStatus.equals(otherPosition.jobPosition);
    }

    @Override
    public int hashCode() {
        return applicationStatus.hashCode();
    }
}
