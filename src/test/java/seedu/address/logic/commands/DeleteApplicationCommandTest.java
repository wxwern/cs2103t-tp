package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.jobapplication.JobApplication;

class DeleteApplicationCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndex_deletesApplication() {
        assert model.getDisplayedContactList().size() > 0;

        JobApplication applicationToDelete = model.getDisplayedApplicationList().get(0);

        DeleteApplicationCommand command = new DeleteApplicationCommand(Index.fromZeroBased(0));

        String expectedMessage = String.format(DeleteApplicationCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                applicationToDelete);

        ModelManager expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertDoesNotThrow(() -> expectedModel.deleteApplication(applicationToDelete));

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_deletesApplication() {

        DeleteApplicationCommand command = new DeleteApplicationCommand(Index.fromZeroBased(20));

        String expectedMessage = Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() {

        Index sharedIndex = Index.fromOneBased(1);
        DeleteApplicationCommand commandOne = new DeleteApplicationCommand(sharedIndex);
        DeleteApplicationCommand commandTwo = new DeleteApplicationCommand(sharedIndex);
        DeleteApplicationCommand commandThree = new DeleteApplicationCommand(Index.fromOneBased(2));

        // equals itself
        assertEquals(commandOne, commandOne);

        // equals same index
        assertEquals(commandOne, commandTwo);

        // does not equal different index
        assertNotEquals(commandOne, commandThree);

        // does not equal null
        assertNotEquals(commandOne, null);
    }

}
