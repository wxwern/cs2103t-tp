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
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Recruiter;
import seedu.address.testutil.OrganizationBuilder;
import seedu.address.testutil.RecruiterBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddRecruiterCommand}.
 */
public class AddRecruiterCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newRecruiter_success() {
        Organization linkedOrganization = new OrganizationBuilder().withId("valid-organization").build();
        Recruiter validRecruiter = new RecruiterBuilder()
                .withId("valid-recruiter").withOrganization(linkedOrganization).build();

        model.addContact(linkedOrganization);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validRecruiter);

        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                validRecruiter.getName(),
                validRecruiter.getId(),
                validRecruiter.getPhone().orElse(null),
                validRecruiter.getEmail().orElse(null),
                validRecruiter.getUrl().orElse(null),
                validRecruiter.getAddress().orElse(null),
                validRecruiter.getTags(),
                validRecruiter.getOrganizationId().orElse(null)
        );

        assertCommandSuccess(addCommand, model,
                String.format(AddRecruiterCommand.MESSAGE_SUCCESS, Messages.format(validRecruiter)),
                expectedModel);
    }

    @Test
    public void execute_newRecruiterLinkToMissingOrganization_failure() {
        Organization linkedOrganization = new OrganizationBuilder().withId("valid-organization").build();
        Recruiter validRecruiter = new RecruiterBuilder()
                .withId("valid-recruiter").withOrganization(linkedOrganization).build();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addContact(validRecruiter);

        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                validRecruiter.getName(),
                validRecruiter.getId(),
                validRecruiter.getPhone().orElse(null),
                validRecruiter.getEmail().orElse(null),
                validRecruiter.getUrl().orElse(null),
                validRecruiter.getAddress().orElse(null),
                validRecruiter.getTags(),
                validRecruiter.getOrganizationId().orElse(null)
        );

        assertCommandFailure(addCommand, model,
                String.format(AddRecruiterCommand.MESSAGE_INVALID_ORGANIZATION));
    }

    @Test
    public void execute_duplicateRecruiter_throwsCommandException() {
        Contact contactInList = model.getAddressBook().getContactList().get(5);
        Recruiter recruiterInList = (Recruiter) contactInList;
        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                recruiterInList.getName(),
                recruiterInList.getId(),
                recruiterInList.getPhone().orElse(null),
                recruiterInList.getEmail().orElse(null),
                recruiterInList.getUrl().orElse(null),
                recruiterInList.getAddress().orElse(null),
                recruiterInList.getTags(),
                recruiterInList.getOrganizationId().orElse(null)
        );
        assertCommandFailure(addCommand, model, AddRecruiterCommand.MESSAGE_DUPLICATE_CONTACT);
    }
}
