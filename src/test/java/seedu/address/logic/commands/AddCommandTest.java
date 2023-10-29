package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.NUS;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.testutil.ContactBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullCompulsoryFields_throwsNullPointerException() {
        // null name field
        assertThrows(NullPointerException.class, () -> new AddCommand(
                null,
                ALICE.getId(), ALICE.getPhone().orElse(null),
                ALICE.getEmail().orElse(null), ALICE.getUrl().orElse(null),
                ALICE.getAddress().orElse(null), ALICE.getTags()));

        // null id field
        assertThrows(NullPointerException.class, () -> new AddCommand(
                ALICE.getName(),
                null, ALICE.getPhone().orElse(null),
                ALICE.getEmail().orElse(null), ALICE.getUrl().orElse(null),
                ALICE.getAddress().orElse(null), ALICE.getTags()));

        // null tags fields
        assertThrows(NullPointerException.class, () -> new AddCommand(
                ALICE.getName(),
                ALICE.getId(), ALICE.getPhone().orElse(null),
                ALICE.getEmail().orElse(null), ALICE.getUrl().orElse(null),
                ALICE.getAddress().orElse(null), null));
    }

    @Test
    public void execute_contactAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Contact validContact = new ContactBuilder().build();

        AddCommand addCommand = new AddCommand(
                validContact.getName(),
                validContact.getId(),
                validContact.getPhone().orElse(null),
                validContact.getEmail().orElse(null),
                validContact.getUrl().orElse(null),
                validContact.getAddress().orElse(null),
                validContact.getTags()
        );

        CommandResult commandResult = addCommand.execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validContact)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), modelStub.contactsAdded);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact validContact = new ContactBuilder().build();
        AddCommand addCommand = new AddCommand(
                validContact.getName(),
                validContact.getId(),
                validContact.getPhone().orElse(null),
                validContact.getEmail().orElse(null),
                validContact.getUrl().orElse(null),
                validContact.getAddress().orElse(null),
                validContact.getTags()
        );
        ModelStub modelStub = new ModelStubWithContact(validContact);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CONTACT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new ContactBuilder().withName("Alice").build();
        Contact bob = new ContactBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(
                alice.getName(),
                alice.getId(),
                alice.getPhone().orElse(null),
                alice.getEmail().orElse(null),
                alice.getUrl().orElse(null),
                alice.getAddress().orElse(null),
                alice.getTags()
        );
        AddCommand addBobCommand = new AddCommand(
                bob.getName(),
                bob.getId(),
                bob.getPhone().orElse(null),
                bob.getEmail().orElse(null),
                bob.getUrl().orElse(null),
                bob.getAddress().orElse(null),
                bob.getTags()
        );

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(
                alice.getName(),
                alice.getId(),
                alice.getPhone().orElse(null),
                alice.getEmail().orElse(null),
                alice.getUrl().orElse(null),
                alice.getAddress().orElse(null),
                alice.getTags()
        );
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contact -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(
                ALICE.getName(),
                ALICE.getId(),
                ALICE.getPhone().orElse(null),
                ALICE.getEmail().orElse(null),
                ALICE.getUrl().orElse(null),
                ALICE.getAddress().orElse(null),
                ALICE.getTags()
        );
        String expected = AddCommand.class.getCanonicalName()
                + "{name=" + ALICE.getName()
                + ", id=" + ALICE.getId()
                + ", phone=" + ALICE.getPhone().orElse(null)
                + ", email=" + ALICE.getEmail().orElse(null)
                + ", url=" + ALICE.getUrl().orElse(null)
                + ", address=" + ALICE.getAddress().orElse(null)
                + ", tags=" + ALICE.getTags() + "}";
        assertEquals(expected, addCommand.toString());
    }

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
            return NUS;
        }

        @Override
        public Contact getContactByIdXorIndex(Id id, Index index) {
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
