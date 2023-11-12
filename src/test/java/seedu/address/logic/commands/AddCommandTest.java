package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.testutil.TypicalContacts.NUS;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.jobapplication.JobApplication;

public class AddCommandTest {

    /**
     * A default model stub that have all of the methods failing.
     */
    protected class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addApplication(JobApplication application) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Contact getContactById(Id id) {
            // Ensure that the organization returned here matches the DEFAULT_ORGANIZATION
            // used in RecruiterBuilder.
            return NUS;
        }

        @Override
        public Contact getContactByIdXorIndex(Id id, Index index) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void replaceApplication(JobApplication oldApplication,
                                       JobApplication newApplication) throws IllegalValueException {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void deleteApplication(JobApplication application) throws IllegalValueException {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getDisplayedContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<JobApplication> getDisplayedApplicationList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedApplicationList(Comparator<JobApplication> comparator) {
            throw new AssertionError("This method should not be called.");

        }
        @Override
        public void updateSortedContactList(Comparator<Contact> comparator) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single contact.
     */
    protected class ModelStubWithContact extends ModelStub {
        private final Contact contact;

        ModelStubWithContact(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSameContact(contact);
        }
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    protected class ModelStubAcceptingContactAdded extends ModelStub {
        final ArrayList<Contact> contactsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return contactsAdded.stream().anyMatch(contact::isSameContact);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            contactsAdded.add(contact);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
