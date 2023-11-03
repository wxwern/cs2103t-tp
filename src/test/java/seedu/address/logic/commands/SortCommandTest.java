package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Contact;
import seedu.address.model.jobapplication.JobApplication;

class SortCommandTest {

    // TODO: tech debt - lousy tests
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
}
