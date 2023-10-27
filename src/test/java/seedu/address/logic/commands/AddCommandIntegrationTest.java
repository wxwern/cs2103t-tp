package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newContact_success() {
        Contact validContact = new ContactBuilder().withId("valid-contact").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validContact);

        AddCommand addCommand = new AddCommand(
                validContact.getName(),
                validContact.getId(),
                validContact.getPhone().orElse(null),
                validContact.getEmail().orElse(null),
                validContact.getUrl().orElse(null),
                validContact.getAddress().orElse(null),
                validContact.getTags()
        );

        assertCommandSuccess(addCommand, model,
                String.format(AddCommand.MESSAGE_SUCCESS, Messages.format(validContact)),
                expectedModel);
    }

    @Test
    public void execute_duplicateContact_throwsCommandException() {
        Contact contactInList = model.getAddressBook().getContactList().get(0);
        AddCommand addCommand = new AddCommand(
                contactInList.getName(),
                contactInList.getId(),
                contactInList.getPhone().orElse(null),
                contactInList.getEmail().orElse(null),
                contactInList.getUrl().orElse(null),
                contactInList.getAddress().orElse(null),
                contactInList.getTags()
        );
        assertCommandFailure(addCommand, model, AddCommand.MESSAGE_DUPLICATE_CONTACT);
    }

}
