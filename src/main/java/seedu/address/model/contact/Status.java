package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Organisation's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {
    public static final String MESSAGE_CONSTRAINTS =
            "Status should only contain alphanumeric characters and spaces";

    public static final String VALIDATION_REGEX = "[\\p{Alnum} ]*";

    public final String applicationStatus;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    @Deprecated
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        applicationStatus = status;
    }

    /**
     * Constructs an empty {@code Status}.
     */
    public Status() {
        applicationStatus = "";

    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
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
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return applicationStatus.equals(otherStatus.applicationStatus);
    }

    @Override
    public int hashCode() {
        return applicationStatus.hashCode();
    }
}
