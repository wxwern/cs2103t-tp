package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.jobapplication.JobApplication;

class SortCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model unsortedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        Comparator<Contact> a = (c, b) -> c.getType().compareTo(b.getType());
        Comparator<Contact> d = (c1, c2) -> (-c1.getType().compareTo(c2.getType()));

        Comparator<JobApplication> j = Comparator.comparing(application ->
                application.getJobTitle().title, String.CASE_INSENSITIVE_ORDER);
        Comparator<JobApplication> j2 = Comparator.comparing(application ->
                application.getDeadline().deadline);

        SortCommand s = new SortCommand(a, j, false);
        SortCommand s2 = new SortCommand(a, j, true);

        assertEquals(s, s);
        assertEquals(s, new SortCommand(a, j, false));
        //it does not matter what the comparators are if the SortCommand is a reset
        assertEquals(s2, new SortCommand(d, j2, true));
        assertNotEquals(s, new SortCommand(a, j2, false));
        assertNotEquals(s, new SortCommand(d, j, false));
        assertNotEquals(s, new SortCommand(a, j, true));
    }

    @Test
    public void execute_sortName_contactsSorted() {
        String expectedMessage = SortCommand.MESSAGE_SORTED_CONTACTS;
        SortCommand command = new SortCommand(Model.COMPARATOR_NAME, null, false);
        expectedModel.updateSortedContactList(Model.COMPARATOR_NAME);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        ArrayList<Contact> arrayList1 = new ArrayList<>(model.getDisplayedContactList());
        ArrayList<Contact> arrayList2 = new ArrayList<>(expectedModel.getDisplayedContactList());
        assertEquals(arrayList1, arrayList2);
    }

    @Test
    public void execute_sortTitle_applicationsSorted() {
        String expectedMessage = SortCommand.MESSAGE_SORTED_APPLICATIONS;
        SortCommand command = new SortCommand(null, JobApplication.JOB_TITLE_COMPARATOR,
                false);
        expectedModel.updateSortedApplicationList(JobApplication.JOB_TITLE_COMPARATOR);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        ArrayList<JobApplication> arrayList1 = new ArrayList<>(model.getDisplayedApplicationList());
        ArrayList<JobApplication> arrayList2 = new ArrayList<>(expectedModel.getDisplayedApplicationList());
        assertEquals(arrayList1, arrayList2);
    }

    @Test
    public void execute_sortNone_applicationsUnsorted() {
        String expectedMessage = SortCommand.MESSAGE_RESET_SORTING;
        SortCommand command = new SortCommand(null, null, true);
        assertCommandSuccess(command, model, expectedMessage, unsortedModel);
        ArrayList<Contact> arrayListContact1 = new ArrayList<>(model.getDisplayedContactList());
        ArrayList<Contact> arrayListContact2 = new ArrayList<>(unsortedModel.getDisplayedContactList());
        ArrayList<JobApplication> arrayListApp1 = new ArrayList<>(model.getDisplayedApplicationList());
        ArrayList<JobApplication> arrayListApp2 = new ArrayList<>(unsortedModel.getDisplayedApplicationList());
        assertEquals(arrayListApp1, arrayListApp2);
        assertEquals(arrayListContact1, arrayListContact2);
    }
}
