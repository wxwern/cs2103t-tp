package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline((String) null));
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        String invalidDeadline1 = "";
        String invalidDeadline2 = "None";
        String invalidDeadline3 = "1-222-456";
        String invalidDeadline4 = "11-22-2022"; // invalid month
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline1));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline2));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline3));
        assertThrows(IllegalArgumentException.class, () -> new Deadline(invalidDeadline4));
    }

    @Test
    public void isValidDeadline() {
        String invalidDeadline1 = "";
        String invalidDeadline2 = "None-None-2022";
        String invalidDeadline3 = "1-222-456";
        String invalidDeadline4 = "11-22-2022";
        String invalidDeadline5 = "29-02-2022";
        String invalidDeadline6 = "31-04-2022";
        String validDeadline1 = "23-02-2022";
        String validDeadline2 = "29-02-2020";
        String validDeadline3 = "30-4-2022";

        // null deadline
        assertThrows(NullPointerException.class, () -> Deadline.isValidDeadline(null));

        // invalid deadline
        assertFalse(Deadline.isValidDeadline(invalidDeadline1));
        assertFalse(Deadline.isValidDeadline(invalidDeadline2));
        assertFalse(Deadline.isValidDeadline(invalidDeadline3));
        assertFalse(Deadline.isValidDeadline(invalidDeadline4));
        assertFalse(Deadline.isValidDeadline(invalidDeadline5));
        assertFalse(Deadline.isValidDeadline(invalidDeadline6));

        // valid deadline
        assertTrue(Deadline.isValidDeadline(validDeadline1));
    }

    @Test
    public void compareTo() {
        Deadline d1 = new Deadline("22-11-2022");
        Deadline d2 = new Deadline("22-11-2022");
        Deadline d3 = new Deadline("11-12-2022");

        assertEquals(d1.compareTo(d2), 0);
        assertTrue(d1.compareTo(d3) < 0);
        assertTrue(d3.compareTo(d1) > 0);
    }

    @Test
    public void equals() {
        Deadline d1 = new Deadline("22-11-2022");
        Deadline d2 = new Deadline("22-11-2022");
        Deadline d3 = new Deadline("23-11-2022");

        // self -> true
        assertEquals(d1, d1);

        // same deadline -> true
        assertEquals(d1, d2);

        // different deadline -> false
        assertNotEquals(d1, d3);

        // not a deadline -> false
        assertNotEquals(d1, null);
    }
}
