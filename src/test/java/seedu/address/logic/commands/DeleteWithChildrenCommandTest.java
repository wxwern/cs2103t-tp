package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_LINKED_ORGANIZATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_LINKED_RECRUITER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_UNLINKED_ORGANIZATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_UNLINKED_RECRUITER;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteWithChildrenCommand}.
 */
public class DeleteWithChildrenCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_unlinkedOrganizationSuccess() {
        Contact contactToDelete = model.getDisplayedContactList()
                .get(INDEX_UNLINKED_ORGANIZATION.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_UNLINKED_ORGANIZATION);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_unlinkedRecruiterSuccess() {
        Contact contactToDelete = model.getDisplayedContactList()
                .get(INDEX_UNLINKED_RECRUITER.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_UNLINKED_RECRUITER);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_linkedOrganizationSuccess() {
        Contact contactToDelete = model.getDisplayedContactList()
                .get(INDEX_LINKED_ORGANIZATION.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_LINKED_ORGANIZATION);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        List<Contact> childrenContacts = contactToDelete.getChildren(expectedModel);
        for (Contact child : childrenContacts) {
            expectedModel.deleteContact(child);
        }

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_linkedRecruiterSuccess() {
        Contact contactToDelete = model.getDisplayedContactList()
                .get(INDEX_LINKED_RECRUITER.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_LINKED_RECRUITER);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        Contact parent = contactToDelete.getParent().orElse(null);
        assert parent != null;
        List<Contact> childrenContacts = parent.getChildren(expectedModel);

        assertFalse(childrenContacts.contains(contactToDelete));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_unlinkedOrganizationSuccess() {
        showContactAtIndex(model, INDEX_UNLINKED_ORGANIZATION);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_unlinkedRecruiterSuccess() {
        showContactAtIndex(model, INDEX_UNLINKED_RECRUITER);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_linkedOrganizationSuccess() {
        showContactAtIndex(model, INDEX_LINKED_ORGANIZATION);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);
        List<Contact> childrenContacts = contactToDelete.getChildren(expectedModel);
        for (Contact child : childrenContacts) {
            expectedModel.deleteContact(child);
        }

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_linkedRecruiterSuccess() {
        showContactAtIndex(model, INDEX_LINKED_RECRUITER);

        Contact contactToDelete = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);

        String expectedMessage = String.format(DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Messages.formatChildren(contactToDelete.getChildren(model)));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);
        showNoContact(expectedModel);
        Contact parent = contactToDelete.getParent().orElse(null);
        assert parent != null;
        List<Contact> childrenContacts = parent.getChildren(expectedModel);

        assertFalse(childrenContacts.contains(contactToDelete));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteWithChildrenCommand deleteFirstCommand = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);
        DeleteWithChildrenCommand deleteSecondCommand = new DeleteWithChildrenCommand(INDEX_SECOND_CONTACT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteWithChildrenCommand deleteFirstCommandCopy = new DeleteWithChildrenCommand(INDEX_FIRST_CONTACT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteWithChildrenCommand deleteCommand = new DeleteWithChildrenCommand(targetIndex);
        String expected = DeleteWithChildrenCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getDisplayedContactList().isEmpty());
    }
}
