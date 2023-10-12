package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's ID in the address book.
 * Guarantees: immutable, is valid as declared in {@link #isValidId(String)}
 */
public class ContactId {

    public static final String MESSAGE_CONSTRAINTS =
            "Contact ID should not be blank";

    // TODO: I need help with this part
    public static final String VALIDATION_REGEX = "^[a-zA-Z0-9\\-_]+$";

    public final String contactId;

    /**
     * Constructs a {@code ContactId}.
     *
     * @param contactId A valid contact id.
     */
    public ContactId(String contactId) {
        requireNonNull(contactId);
        checkArgument(isValidId(contactId), MESSAGE_CONSTRAINTS);
        this.contactId = contactId;
    }

    /**
     * Returns true if a given string is a valid contact ID.
     */
    public static boolean isValidId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return contactId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof ContactId)) {
            return false;
        }

        ContactId otherId = (ContactId) other;
        return contactId.equals(otherId.contactId);
    }

    @Override
    public int hashCode() {
        return contactId.hashCode();
    }
}
