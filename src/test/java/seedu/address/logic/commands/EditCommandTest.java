package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.testutil.TypicalContacts.NUS;
import static seedu.address.testutil.TypicalContacts.RICHARD;
import static seedu.address.testutil.TypicalContacts.RYAN;
import static seedu.address.testutil.TypicalContacts.SMU;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_LINKED_ORGANIZATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_LINKED_RECRUITER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_UNLINKED_ORGANIZATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_UNLINKED_RECRUITER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand.EditContactDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Type;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.EditContactDescriptorBuilder;
import seedu.address.testutil.OrganizationBuilder;
import seedu.address.testutil.RecruiterBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_editCommandByTargetId_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactInFilteredList = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact editedContact = new ContactBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(contactInFilteredList.getId(),
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(0), editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_unlinkedOrganizationSuccess() {
        Organization editedContact = new OrganizationBuilder(SMU)
                .withId(VALID_ID_AMY).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditCommand(INDEX_UNLINKED_ORGANIZATION, descriptor);

        String expectedMessage = String
                .format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_UNLINKED_ORGANIZATION.getZeroBased()), editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_unlinkedRecruiterSuccess() {
        Recruiter editedContact = new RecruiterBuilder(RICHARD)
                .withId(VALID_ID_AMY).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditCommand editCommand = new EditCommand(INDEX_UNLINKED_RECRUITER, descriptor);

        String expectedMessage = String
                .format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_UNLINKED_RECRUITER.getZeroBased()), editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateLinkedOrganization_failure() {
        Organization firstOrganization = (Organization) model.getDisplayedContactList()
                .get(INDEX_LINKED_ORGANIZATION.getZeroBased());
        Organization secondOrganization = (Organization) model.getDisplayedContactList()
                .get(INDEX_UNLINKED_ORGANIZATION.getZeroBased());

        Organization editedOrganization = new OrganizationBuilder(firstOrganization)
                .withId(secondOrganization.getId().value).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedOrganization).build();
        EditCommand editCommand = new EditCommand(INDEX_LINKED_ORGANIZATION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_DUPLICATE_CONTACT,
                Messages.format(editedOrganization));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandFailure(editCommand, model, expectedMessage);
        assertEquals(model, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_linkedOrganizationSuccess() {
        Organization originalOrganization = NUS;
        Organization editedOrganization = new OrganizationBuilder(originalOrganization)
                .withId(VALID_ID_AMY).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedOrganization).build();
        EditCommand editCommand = new EditCommand(INDEX_LINKED_ORGANIZATION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedOrganization));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_LINKED_ORGANIZATION.getZeroBased()), editedOrganization);
        for (Contact child : originalOrganization.getChildren(expectedModel)) {
            expectedModel.setContact(child,
                    new RecruiterBuilder((Recruiter) child).withOrganization(editedOrganization).build());
        }

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_linkedRecruiterSuccess() {
        Recruiter editedRecruiter = new RecruiterBuilder(RYAN)
                .withId(VALID_ID_AMY).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedRecruiter).build();
        EditCommand editCommand = new EditCommand(INDEX_LINKED_RECRUITER, descriptor);

        String expectedMessage = String
                .format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedRecruiter));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_LINKED_RECRUITER.getZeroBased()), editedRecruiter);

        Contact parentContact = editedRecruiter.getParent().orElse(null);
        assert parentContact != null && parentContact.getType() == Type.ORGANIZATION;

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(parentContact.getChildren(model).contains(editedRecruiter));
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_linkedOrganizationSuccess() {
        Organization originalOrganization = NUS;
        Organization editedOrganization = new OrganizationBuilder(originalOrganization)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(INDEX_LINKED_ORGANIZATION, descriptor);

        String expectedMessage = String
                .format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedOrganization));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_LINKED_ORGANIZATION.getZeroBased()), editedOrganization);
        for (Contact child : originalOrganization.getChildren(expectedModel)) {
            expectedModel.setContact(child,
                    new RecruiterBuilder((Recruiter) child).withOrganization(editedOrganization).build());
        }

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_linkedRecruiterSuccess() {
        Recruiter editedRecruiter = new RecruiterBuilder(RYAN)
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(INDEX_LINKED_RECRUITER, descriptor);

        String expectedMessage = String
                .format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedRecruiter));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList()
                .get(INDEX_LINKED_RECRUITER.getZeroBased()), editedRecruiter);

        Contact parentContact = editedRecruiter.getParent().orElse(null);
        assert parentContact != null && parentContact.getType() == Type.ORGANIZATION;

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(parentContact.getChildren(model).contains(editedRecruiter));
    }

    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_linkedOrganizationSuccess() {
        EditCommand editCommand = new EditCommand(INDEX_LINKED_ORGANIZATION, new EditContactDescriptor());
        Contact editedContact = model.getDisplayedContactList().get(INDEX_LINKED_ORGANIZATION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldsSpecifiedUnfilteredList_linkedRecruiterSuccess() {
        EditCommand editCommand = new EditCommand(INDEX_LINKED_RECRUITER, new EditContactDescriptor());
        Contact editedRecruiter = model.getDisplayedContactList().get(INDEX_LINKED_RECRUITER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedRecruiter));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        Contact parentContact = editedRecruiter.getParent().orElse(null);
        assert parentContact != null && parentContact.getType() == Type.ORGANIZATION;

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        assertTrue(parentContact.getChildren(model).contains(editedRecruiter));
    }

    @Test
    public void execute_filteredList_linkedOrganizationSuccess() {
        showContactAtIndex(model, INDEX_LINKED_ORGANIZATION);

        Contact contactInFilteredList = model.getDisplayedContactList()
                .get(INDEX_FIRST_CONTACT.getZeroBased());
        Organization editedOrganization = new OrganizationBuilder((Organization) contactInFilteredList)
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedOrganization));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased()),
                editedOrganization);
        for (Contact child : contactInFilteredList.getChildren(expectedModel)) {
            expectedModel.setContact(child,
                    new RecruiterBuilder((Recruiter) child).withOrganization(editedOrganization).build());
        }

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_linkedRecruiterSuccess() {
        showContactAtIndex(model, INDEX_LINKED_RECRUITER);

        Contact contactInFilteredList = model.getDisplayedContactList()
                .get(INDEX_FIRST_CONTACT.getZeroBased());
        Recruiter editedContact = new RecruiterBuilder((Recruiter) contactInFilteredList)
                .withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder(editedContact).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased()),
                editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        Contact contactInFilteredList = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact editedContact = new ContactBuilder(contactInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CONTACT_SUCCESS,
                Messages.format(editedContact));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setContact(model.getDisplayedContactList().get(0), editedContact);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateContactUnfilteredList_failure() {
        Contact firstContact = model.getDisplayedContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(firstContact).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_CONTACT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        // edit contact in filtered list into a duplicate in address book
        Contact contactInList = model.getAddressBook().getContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_CONTACT,
                new EditContactDescriptorBuilder(contactInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }

    @Test
    public void execute_duplicateContactFilteredListWithId_failure() {
        // edit contact in filtered list into a duplicate in address book
        Contact contactToEdit = model.getAddressBook().getContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        Contact contactInList = model.getAddressBook().getContactList().get(INDEX_SECOND_CONTACT.getZeroBased());
        EditCommand editCommand = new EditCommand(contactToEdit.getId(),
                new EditContactDescriptorBuilder(contactInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CONTACT);
    }
    @Test
    public void execute_invalidContactIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getDisplayedContactList().size() + 1);
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);
        Index outOfBoundIndex = INDEX_SECOND_CONTACT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getContactList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditContactDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_CONTACT, DESC_AMY);

        // same values -> returns true
        EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_CONTACT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_CONTACT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_CONTACT, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();
        EditCommand editCommand = new EditCommand(index, editContactDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{index=" + index + ", editContactDescriptor="
                + editContactDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }



}
