package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class JobTitleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new JobTitle(null));
    }

    @Test
    public void constructor_invalidTitle_throwsIllegalArgumentException() {
        String invalidTitle = "";
        assertThrows(IllegalArgumentException.class, () -> new JobTitle(invalidTitle));
    }

    @Test
    public void isValidTitle() {
        // null title
        assertThrows(NullPointerException.class, () -> JobTitle.isValidJobTitle(null));

        // invalid title
        assertFalse(JobTitle.isValidJobTitle(""));
        assertFalse(JobTitle.isValidJobTitle(" "));

        // valid title
        assertTrue(JobTitle.isValidJobTitle("Software Engineer"));
        assertTrue((JobTitle.isValidJobTitle("Lvl 7 Engineer")));
    }

    @Test
    public void equals() {
        JobTitle jt1 = new JobTitle("Software Engineer");
        JobTitle jt2 = new JobTitle("Software Engineer");
        JobTitle jt3 = new JobTitle("Level 7 Engineer");

        // self -> true
        assertEquals(jt1, jt1);

        // same title -> true
        assertEquals(jt1, jt2);

        // different title -> false
        assertNotEquals(jt1, jt3);

        // not a title -> false
        assertNotEquals(jt1, null);
    }
}
