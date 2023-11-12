package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalContacts.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.testutil.TypicalContacts;

class EditApplicationCommandTest {

    private Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

    private JobTitle uniqueTitle = new JobTitle("Unique title");
    @Test
    public void execute_validIndex_editSuccessfully() {

        EditApplicationCommand.EditApplicationDescriptor editApplicationDescriptor =
                new EditApplicationCommand.EditApplicationDescriptor();

        Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

        editApplicationDescriptor.setJobTitle(uniqueTitle);

        EditApplicationCommand command = new EditApplicationCommand(Index.fromOneBased(1), editApplicationDescriptor);

        assertDoesNotThrow(() -> command.execute(model));

        JobApplication firstApplication = model.getDisplayedApplicationList().get(0);

        assertEquals(uniqueTitle, firstApplication.getJobTitle());
    }

    @Test
    public void execute_invalidIndex_throwsException() {

        EditApplicationCommand.EditApplicationDescriptor editApplicationDescriptor =
                new EditApplicationCommand.EditApplicationDescriptor();

        Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

        editApplicationDescriptor.setJobTitle(uniqueTitle);

        EditApplicationCommand command = new EditApplicationCommand(Index.fromOneBased(20), editApplicationDescriptor);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_emptyFields_throwsException() {

        EditApplicationCommand.EditApplicationDescriptor editApplicationDescriptor =
                new EditApplicationCommand.EditApplicationDescriptor();

        Model model = new ModelManager(TypicalContacts.getTypicalAddressBook(), new UserPrefs());

        EditApplicationCommand command = new EditApplicationCommand(Index.fromOneBased(20), editApplicationDescriptor);

        assertCommandFailure(command, model, EditApplicationCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void equals() {
        EditApplicationCommand.EditApplicationDescriptor editApplicationDescriptorOne =
                new EditApplicationCommand.EditApplicationDescriptor();

        EditApplicationCommand.EditApplicationDescriptor editApplicationDescriptorTwo =
                new EditApplicationCommand.EditApplicationDescriptor();

        editApplicationDescriptorOne.setJobTitle(uniqueTitle);

        Index one = Index.fromOneBased(1);

        Index two = Index.fromOneBased(2);

        EditApplicationCommand commandOne = new EditApplicationCommand(one, editApplicationDescriptorOne);
        EditApplicationCommand commandTwo = new EditApplicationCommand(one, editApplicationDescriptorOne);

        // different index
        EditApplicationCommand commandThree = new EditApplicationCommand(two, editApplicationDescriptorOne);

        // different descriptor
        EditApplicationCommand commandFour = new EditApplicationCommand(one, editApplicationDescriptorTwo);

        // itself
        assertEquals(commandOne, commandOne);

        // same index and descriptor
        assertEquals(commandOne, commandTwo);

        // different index
        assertNotEquals(commandOne, commandThree);

        // different descriptor
        assertNotEquals(commandOne, commandFour);

        // null
        assertNotEquals(commandOne, null);
    }
}
