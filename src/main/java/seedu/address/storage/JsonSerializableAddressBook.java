package seedu.address.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Contact;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_CONTACT = "Contacts list contains duplicate contact(s).";

    private final List<JsonAdaptedContact> contacts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given contacts.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("contacts") List<JsonAdaptedContact> contacts) {
        this.contacts.addAll(contacts);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        contacts.addAll(source.getContactList().stream()
                .map(JsonAdaptedContact::new)
                .sorted()
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        // Record the original order.
        Map<String, Integer> orderMap;
        try {
            orderMap = IntStream.range(0, contacts.size())
                    .boxed()
                    .collect(Collectors.toMap(i -> contacts.get(i).getId(), Function.identity()));
        } catch (IllegalStateException s) {
            // Having two duplicate keys in the order map will trigger an IllegalStateException.
            // In this case, duplicate keys mean duplicate ids which implies duplicate contacts.
            throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
        }

        Comparator<Contact> originalOrderComparator = Comparator.comparingInt(c -> orderMap.get(c.getId().value));

        // Create all contacts.
        List<JsonAdaptedContact> sortedJsonContacts = new ArrayList<>(contacts);
        Collections.sort(sortedJsonContacts);
        List<Contact> newContacts = new ArrayList<>();

        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedContact jsonAdaptedContact : sortedJsonContacts) {
            Contact contact = jsonAdaptedContact.toModelType(addressBook);
            // Defensive check.
            if (addressBook.getContactById(contact.getId()) != null) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CONTACT);
            }
            addressBook.addContact(contact);
            newContacts.add(contact);
        }

        // Add them into the book in the original order.
        newContacts.sort(originalOrderComparator);
        addressBook.setContacts(newContacts);

        return addressBook;
    }

}
