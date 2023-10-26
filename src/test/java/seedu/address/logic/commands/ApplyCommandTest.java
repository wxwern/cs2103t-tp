package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.ALICE;
import static seedu.address.testutil.TypicalContacts.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.contact.Id;
import seedu.address.model.jobapplication.JobTitle;


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
        model.addContact(ALICE);
        assertThrows(
                CommandException.class, () -> new ApplyCommand(
                        null, Index.fromOneBased(1), new JobTitle("SWE"),
                        null, null, null,
                        null)
                        .execute(model)
        );
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
        assertDoesNotThrow(() -> new ApplyCommand(
                        null, Index.fromOneBased(1), new JobTitle("SWE"),
                        null, null, null,
                        null)
                        .execute(model)
        );
    }
}
