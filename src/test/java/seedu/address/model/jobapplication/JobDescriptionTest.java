package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobDescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobDescription(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArugmentException() {
        String invalidDescription = "";
        assertThrows(IllegalArgumentException.class, () -> new JobDescription(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> JobDescription.isValidJobDescription(null));

        // invalid description
        assertFalse(JobDescription.isValidJobDescription(""));
        assertFalse(JobDescription.isValidJobDescription(" "));

        // valid description
        assertTrue(JobDescription.isValidJobDescription("Pay: $100")); // Special characters
        assertTrue(JobDescription.isValidJobDescription("To contact John for position"));
        assertTrue(JobDescription.isValidJobDescription("First apply, then wait.")); // More special char
    }

    @Test
    public void equals() {
        JobDescription jd1 = new JobDescription("Pay: $100");
        JobDescription jd2 = new JobDescription("Pay: $100");
        JobDescription jd3 = new JobDescription("Contact John");

        // self -> true
        assertEquals(jd1, jd1);

        // same description -> true
        assertEquals(jd1, jd2);

        // different description -> false
        assertNotEquals(jd1, jd3);
    }
}
