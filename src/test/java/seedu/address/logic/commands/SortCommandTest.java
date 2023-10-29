package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.contact.Contact;

class SortCommandTest {

    // TODO: tech debt - lousy tests
    @Test
    public void constructor_null_throwsNullPtrException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    public void constructor_validComparator_doesNotThrow() {
        assertDoesNotThrow(() -> new SortCommand((a, b) -> a.hashCode() - b.hashCode()));
    }

    @Test
    public void execute_validComparator_doesNotThrow() {
        Model model = new ModelManager();
        assertDoesNotThrow(() -> new SortCommand((a, b) -> a.getType().compareTo(b.getType())).execute(model));
    }

    @Test
    public void equals() {
        Comparator<Contact> a = (c, b) -> c.getType().compareTo(b.getType());
        Comparator<Contact> d = (c1, c2) -> (-c1.getType().compareTo(c2.getType()));

        SortCommand s = new SortCommand(a);
        assertEquals(s, s);
        assertEquals(s, new SortCommand(a));
        assertNotEquals(s, new SortCommand(d));
    }
}
