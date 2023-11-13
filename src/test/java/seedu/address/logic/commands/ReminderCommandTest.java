package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.jobapplication.JobApplication;

class ReminderCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ReminderCommand r = new ReminderCommand(true);
        ReminderCommand r2 = new ReminderCommand(true);
        ReminderCommand r3 = new ReminderCommand(false);

        assertEquals(r, r);
        assertEquals(r, r2);
        assertNotEquals(r, r3);
    }

    @Test
    public void execute_remindEarliest_applicationsSorted() {
        String expectedMessage = ReminderCommand.MESSAGE_REMINDED_EARLIEST;
        ReminderCommand command = new ReminderCommand(true);
        expectedModel.updateSortedApplicationList(JobApplication.DEADLINE_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        ArrayList<JobApplication> arrayList1 = new ArrayList<>(model.getDisplayedApplicationList());
        ArrayList<JobApplication> arrayList2 = new ArrayList<>(expectedModel.getDisplayedApplicationList());
        assertEquals(arrayList1, arrayList2);
    }

    @Test
    public void execute_sortTitle_applicationsSorted() {
        String expectedMessage = ReminderCommand.MESSAGE_REMINDED_LATEST;
        ReminderCommand command = new ReminderCommand(false);
        expectedModel.updateSortedApplicationList(JobApplication.DEADLINE_COMPARATOR.reversed());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        ArrayList<JobApplication> arrayList1 = new ArrayList<>(model.getDisplayedApplicationList());
        ArrayList<JobApplication> arrayList2 = new ArrayList<>(expectedModel.getDisplayedApplicationList());
        assertEquals(arrayList1, arrayList2);
    }
}
