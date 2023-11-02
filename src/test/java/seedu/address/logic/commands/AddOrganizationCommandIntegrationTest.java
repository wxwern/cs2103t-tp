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
import seedu.address.model.contact.Organization;
import seedu.address.testutil.OrganizationBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddOrganizationCommand}.
 */
public class AddOrganizationCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newOrganization_success() {
        Organization validOrganization = new OrganizationBuilder().withId("valid-organization").build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validOrganization);

        AddOrganizationCommand addCommand = new AddOrganizationCommand(
                validOrganization.getName(),
                validOrganization.getId(),
                validOrganization.getPhone().orElse(null),
                validOrganization.getEmail().orElse(null),
                validOrganization.getUrl().orElse(null),
                validOrganization.getAddress().orElse(null),
                validOrganization.getTags(),
                validOrganization.getStatus().orElse(null),
                validOrganization.getPosition().orElse(null),
                validOrganization.getRecruiterIds()
        );

        assertCommandSuccess(addCommand, model,
                String.format(AddOrganizationCommand.MESSAGE_SUCCESS, Messages.format(validOrganization)),
                expectedModel);
    }

    @Test
    public void execute_duplicateOrganization_throwsCommandException() {
        Organization organizationInList = (Organization) model.getAddressBook().getContactList().get(2);
        Organization duplicateOrganization = new OrganizationBuilder()
                .withId(organizationInList.getId().value).build();

        // Ensure that AddOrganizationCommand checks duplicate contact BY ID ONLY.
        AddOrganizationCommand addCommand = new AddOrganizationCommand(
                duplicateOrganization.getName(),
                duplicateOrganization.getId(),
                duplicateOrganization.getPhone().orElse(null),
                duplicateOrganization.getEmail().orElse(null),
                duplicateOrganization.getUrl().orElse(null),
                duplicateOrganization.getAddress().orElse(null),
                duplicateOrganization.getTags(),
                duplicateOrganization.getStatus().orElse(null),
                duplicateOrganization.getPosition().orElse(null),
                duplicateOrganization.getRecruiterIds()
        );
        assertCommandFailure(addCommand, model, AddOrganizationCommand.MESSAGE_DUPLICATE_CONTACT);
    }
}
