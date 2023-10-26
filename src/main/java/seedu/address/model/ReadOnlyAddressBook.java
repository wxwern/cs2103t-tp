package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the contacts list.
     * This list will not contain any duplicate contacts.
     */
    ObservableList<Contact> getContactList();

    /**
     * Gives a contact which id matches the given id.
     * Gives null if a contact with such id does not exist.
     * Given id must not be null.
     */
    default Contact getContactById(Id id) {
        requireNonNull(id);
        for (Contact c: getContactList()) {
            if (id.equals(c.getId())) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    default boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return getContactList().contains(contact);
    }

}
