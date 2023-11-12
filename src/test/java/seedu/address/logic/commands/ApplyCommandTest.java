package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.NUS;
import static seedu.address.testutil.TypicalContacts.RYAN;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Id;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.testutil.TypicalContacts;


public class ApplyCommandTest {

    @Test
    public void execute_nullIdAndIndex_throwsException() {
        Model model = new ModelManager();
        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        null, null, null,
                        null, null, null,
                        null)
                        .execute(model)
        );
    }

    @Test
    public void execute_bothIndexAndIdNotNull_throwsException() {
        Model model = new ModelManager();
        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        new Id(), Index.fromOneBased(1), null,
                        null, null, null,
                        null)
                        .execute(model)
        );
    }

    @Test
    public void execute_indexOutOfBounds_throwsException() {
        Model model = new ModelManager();
        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        null, Index.fromOneBased(1), new JobTitle("SWE"),
                        null, null, null,
                        null)
                        .execute(model)
        );
    }

    @Test
    public void execute_noSuchId_throwsException() {
        Model model = new ModelManager();

        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        new Id(), null, new JobTitle("SWE"),
                        null, null, null, null)
                        .execute(model)
        );
    }

    @Test
    public void execute_applyToNonOrg_throwsException() {
        Model model = new ModelManager();
        model.addContact(RYAN);

        String expectedMessage = String.format(ApplyCommand.MESSAGE_ATTEMPT_TO_ADD_TO_NON_ORG, RYAN);

        ApplyCommand command = new ApplyCommand(
                null, Index.fromOneBased(1), new JobTitle("SWE"),
                null, null, null, null);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_noTitle_throwsException() {
        Model model = new ModelManager();
        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        new Id(), null, null,
                        null, null, null,
                        null)
                        .execute(model)
        );
    }

    @Test
    public void execute_validApplication_doesNotThrowException() {
        Model model = new ModelManager();
        model.addContact(NUS);

        JobTitle uniqueJobTitle = new JobTitle("This is a unique job title");

        JobApplication expectedApplication = new JobApplication(NUS, uniqueJobTitle,
                null, null, null);

        ApplyCommand command = new ApplyCommand(null, Index.fromOneBased(1), uniqueJobTitle,
                null, null, null, null);

        Model expectedModel = new ModelManager();
        expectedModel.addContact(NUS);
        expectedModel.addApplication(expectedApplication);

        String expectedMessage = String.format(ApplyCommand.MESSAGE_APPLY_SUCCESS, expectedApplication, NUS);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        assertDoesNotThrow(() -> new ApplyCommand(
                        null, Index.fromOneBased(1), new JobTitle("This is a unique position"),
                        null, null, null,
                        null)
                        .execute(model)
        );
    }

    @Test
    public void execute_applicationWithSameName_throwException() {
        JobTitle title = NUS.getJobApplications()[0].getJobTitle();
        Model model = new ModelManager();
        model.addContact(NUS);

        ApplyCommand command = new ApplyCommand(
                null, Index.fromOneBased(1), title,
                null, null, null, null);

        assertCommandFailure(command, model, ApplyCommand.MESSAGE_DUPLICATE_APPLICATION);
    }
}
