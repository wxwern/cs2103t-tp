package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Type;
import seedu.address.model.jobapplication.JobApplication;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = contact -> true;
    Predicate<Contact> PREDICATE_SHOW_ONLY_ORGANIZATIONS = contact -> contact.getType() == Type.ORGANIZATION;
    Predicate<Contact> PREDICATE_SHOW_ONLY_RECRUITERS = contact -> contact.getType() == Type.RECRUITER;
    Predicate<Contact> PREDICATE_SHOW_NOT_APPLIED_ORGANIZATIONS =
            contact -> contact.getType() == Type.ORGANIZATION
                    && ((Organization) contact).getJobApplications().length == 0;

    Comparator<Contact> COMPARATOR_ADDRESS = Comparator.comparing(contact ->
                    contact.getAddress().map(address -> address.value).orElse(null),
            Comparator.nullsLast(Comparator.naturalOrder()));
    Comparator<Contact> COMPARATOR_EMAIL = Comparator.comparing(contact ->
                    contact.getEmail().map(email -> email.value).orElse(null),
            Comparator.nullsLast(Comparator.naturalOrder()));
    Comparator<Contact> COMPARATOR_ID = Comparator.comparing(contact ->
            contact.getId().value);
    Comparator<Contact> COMPARATOR_NAME = Comparator.comparing(contact ->
                    contact.getName().fullName);
    Comparator<Contact> COMPARATOR_PHONE = Comparator.comparing(contact ->
                    contact.getPhone().map(phone -> phone.value).orElse(null),
            Comparator.nullsLast(Comparator.naturalOrder()));
    Comparator<Contact> COMPARATOR_URL = Comparator.comparing(contact ->
                    contact.getUrl().map(url -> url.value).orElse(null),
            Comparator.nullsLast(Comparator.naturalOrder()));

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    boolean hasContact(Contact contact);

    /**
     * Deletes the given contact.
     * The contact must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Adds the given contact.
     * {@code contact} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Replaces the given contact {@code target} with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another existing one in the address book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Adds the given application.
     */
    void addApplication(JobApplication application);

    /**
     * Gives a contact which matches the given id.
     * Gives null if no such contact is found.
     * Given id must not be null.
     */
    Contact getContactById(Id id);

    /**
     * Guarantees a contact given an id or index.
     *
     * @throws IllegalValueException if both are given or not given, or if model cannot access the contact.
     */
    Contact getContactByIdXorIndex(Id id, Index index) throws IllegalValueException;

    /**
     * Replaces the old {@code JobApplication} with the new {@code JobApplication}
     */
    void replaceApplication(Index index, JobApplication newApplication) throws IllegalValueException;

    /**
     * Removes the application from the list.
     */
    void deleteApplication(JobApplication application) throws IllegalValueException;


    /** Returns an unmodifiable view of the displayed contact list */
    ObservableList<Contact> getDisplayedContactList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    void updateSortedContactList(Comparator<Contact> comparator);

    /** Returns an unmodifiable view of the filtered application list */
    ObservableList<JobApplication> getDisplayedApplicationList();

    void updateSortedApplicationList(Comparator<JobApplication> comparator);
}
