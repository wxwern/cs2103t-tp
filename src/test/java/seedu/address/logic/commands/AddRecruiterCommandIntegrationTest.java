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
                String.format(AddRecruiterCommand.MESSAGE_SUCCESS,
                        validRecruiter.getType(), Messages.format(validRecruiter)),
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
        Recruiter recruiterInList = (Recruiter) model.getAddressBook().getContactList().get(5);
        Recruiter duplicateRecruiter = new RecruiterBuilder().withId(recruiterInList.getId().value).build();

        // Ensure that AddRecruiterCommand checks duplicate contact BY ID ONLY.
        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                duplicateRecruiter.getName(),
                duplicateRecruiter.getId(),
                duplicateRecruiter.getPhone().orElse(null),
                duplicateRecruiter.getEmail().orElse(null),
                duplicateRecruiter.getUrl().orElse(null),
                duplicateRecruiter.getAddress().orElse(null),
                duplicateRecruiter.getTags(),
                duplicateRecruiter.getOrganizationId().orElse(null)
        );
        assertCommandFailure(addCommand, model, AddRecruiterCommand.MESSAGE_DUPLICATE_CONTACT);
    }
}
