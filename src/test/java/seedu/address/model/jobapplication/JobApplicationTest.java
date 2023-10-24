package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Id;
import seedu.address.model.contact.Status;


public class JobApplicationTest {
    private Id id1 = new Id();
    private Id id2 = new Id();
    private JobTitle validTitle = new JobTitle("SWE");
    private JobDescription validJobDescription = new JobDescription("Pay: $700/h");
    private Status validStatus = new Status("pending");
    private Deadline validDeadline = new Deadline();

    @Test
    public void constructor_nullExceptJobDescription_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobApplication(null, validTitle, validJobDescription,
                validDeadline, validStatus));
        assertThrows(NullPointerException.class, () -> new JobApplication(id1, null, validJobDescription,
                validDeadline, validStatus));
        assertThrows(NullPointerException.class, () -> new JobApplication(id1, validTitle, validJobDescription,
                null, validStatus));
        assertThrows(NullPointerException.class, () -> new JobApplication(id1, validTitle, validJobDescription,
                validDeadline, null));
    }

    @Test
    public void constructor_nullDescription_doesNotThrowNullPointerException() {
        assertDoesNotThrow(() -> new JobApplication(id1, validTitle, null,
                validDeadline, validStatus));
    }

    @Test
    public void equals() {
        assert !id1.equals(id2);

        JobApplication ja1 = new JobApplication(id1, validTitle, validJobDescription, validDeadline, validStatus);
        JobApplication ja2 = new JobApplication(id1, validTitle, validJobDescription, validDeadline, validStatus);
        JobApplication ja3 = new JobApplication(id2, validTitle, validJobDescription, validDeadline, validStatus);
        JobApplication ja4 = new JobApplication(id1, new JobTitle("SRE"), validJobDescription, validDeadline,
                validStatus);
        JobApplication ja5 = new JobApplication(id1, validTitle, new JobDescription("Intern"), validDeadline,
                validStatus);
        JobApplication ja6 = new JobApplication(id1, validTitle, validJobDescription, validDeadline, new Status(
                "rejected"));

        // self -> true
        assertEquals(ja1, ja1);

        // same contents -> true
        assertEquals(ja1, ja2);

        // different content -> false
        assertNotEquals(ja1, ja3); // different id
        assertNotEquals(ja1, ja4); // different title
        assertNotEquals(ja1, ja5); // different description
        assertNotEquals(ja1, ja6); // different status
    }

    @Test
    public void getters_nonNull_getsCorrectItems() {
        JobApplication ja1 = new JobApplication(id1, validTitle, validJobDescription, validDeadline, validStatus);

        assertEquals(ja1.getStatus(), validStatus);
        assertEquals(ja1.getDeadline(), validDeadline);
        assertEquals(ja1.getJobTitle(), validTitle);
        assertEquals(ja1.getJobDescription().orElse(null), validJobDescription);
        assertEquals(ja1.getOrganizationId(), id1);
    }
}
