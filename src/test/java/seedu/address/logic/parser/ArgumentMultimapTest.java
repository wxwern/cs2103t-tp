package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ArgumentMultimapTest {

    private static final Flag FLAG_A = new Flag("aaa");
    private static final Flag FLAG_B = new Flag("bbb");
    private static final Flag FLAG_C = new Flag("ccc");
    private static final Flag FLAG_D = new Flag("ddd");



    @Test
    public void hasFlag_existingFlagsSet_trueReturned() {
        // Will return true if value is set
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "1 value");
        assertTrue(multimap.hasFlag(FLAG_A));

        // Adding more inputs don't impact the result
        multimap.put(FLAG_A, "2 value");
        multimap.put(FLAG_A, "3 value");
        assertTrue(multimap.hasFlag(FLAG_A));

        // Some test cases with more flags
        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "blah");
        multimap.put(FLAG_B, " zzzzz ");
        multimap.put(FLAG_B, " test");
        multimap.put(FLAG_C, "contact ");

        assertTrue(multimap.hasFlag(FLAG_A));
        assertTrue(multimap.hasFlag(FLAG_B));
        assertTrue(multimap.hasFlag(FLAG_C));
    }
    @Test
    public void hasFlag_missingFlag_falseReturned() {
        // Empty map trivially doesn't have the value
        ArgumentMultimap multimap = new ArgumentMultimap();
        assertFalse(multimap.hasFlag(FLAG_A));

        // Nonempty map but target value not present
        multimap = new ArgumentMultimap();
        multimap.put(FLAG_B, "bbbbb");
        assertFalse(multimap.hasFlag(FLAG_A));

        // Subsequent additions without adding target value will not change result
        multimap.put(FLAG_C, "ccccc");
        assertFalse(multimap.hasFlag(FLAG_A));
    }

    @Test
    public void hasAllOfFlags_completeMatch_trueReturned() {
        // Populated map
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "aaa");
        multimap.put(FLAG_B, "bbb1");
        multimap.put(FLAG_B, "bbb2");
        multimap.put(FLAG_C, "ccc");

        assertTrue(multimap.hasAllOfFlags()); // Trivially, any map will contain every element in an empty list [].

        assertTrue(multimap.hasAllOfFlags(FLAG_A));
        assertTrue(multimap.hasAllOfFlags(FLAG_B));
        assertTrue(multimap.hasAllOfFlags(FLAG_C));

        assertTrue(multimap.hasAllOfFlags(FLAG_A, FLAG_B));
        assertTrue(multimap.hasAllOfFlags(FLAG_B, FLAG_C));
        assertTrue(multimap.hasAllOfFlags(FLAG_C, FLAG_A));

        assertTrue(multimap.hasAllOfFlags(FLAG_A, FLAG_B, FLAG_C));

        // Empty map
        multimap = new ArgumentMultimap();
        assertTrue(multimap.hasAllOfFlags()); // Similar to above, it has all elements in [].
    }

    @Test
    public void hasAllOfFlags_partialOrNoMatch_falseReturned() {
        // Populated map
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "A");
        multimap.put(FLAG_B, "B");
        multimap.put(FLAG_B, "B");

        assertFalse(multimap.hasAllOfFlags(FLAG_C));

        assertFalse(multimap.hasAllOfFlags(FLAG_A, FLAG_C));
        assertFalse(multimap.hasAllOfFlags(FLAG_B, FLAG_C));

        assertFalse(multimap.hasAllOfFlags(FLAG_A, FLAG_B, FLAG_C));

        // Empty map
        multimap = new ArgumentMultimap();
        assertFalse(multimap.hasAllOfFlags(FLAG_A));
        assertFalse(multimap.hasAllOfFlags(FLAG_A, FLAG_B));
        assertFalse(multimap.hasAllOfFlags(FLAG_A, FLAG_B, FLAG_C));
    }

    @Test
    public void hasAnyOfFlags_partialOrCompleteMatch_trueReturned() {
        // Populated map
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "123");
        multimap.put(FLAG_B, "456");
        multimap.put(FLAG_B, "789");

        assertTrue(multimap.hasAnyOfFlags(FLAG_A));
        assertTrue(multimap.hasAnyOfFlags(FLAG_B));

        assertTrue(multimap.hasAnyOfFlags(FLAG_A, FLAG_B));
        assertTrue(multimap.hasAnyOfFlags(FLAG_A, FLAG_C));
        assertTrue(multimap.hasAnyOfFlags(FLAG_B, FLAG_C));

        assertTrue(multimap.hasAnyOfFlags(FLAG_A, FLAG_B, FLAG_C));

        // Empty map
        // - An empty map will never have a partial or complete match.
    }

    @Test
    public void hasAnyOfFlags_noMatch_falseReturned() {
        // Populated map
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "000");

        assertFalse(multimap.hasAnyOfFlags()); // Trivially, any map will not contain any elements from empty list [].
        assertFalse(multimap.hasAnyOfFlags(FLAG_C));
        assertFalse(multimap.hasAnyOfFlags(FLAG_B, FLAG_C));

        // Empty map
        multimap = new ArgumentMultimap();
        assertFalse(multimap.hasAnyOfFlags()); // Similar to above, it will not have any elements in [].
        assertFalse(multimap.hasAnyOfFlags(FLAG_A));
        assertFalse(multimap.hasAnyOfFlags(FLAG_B));
        assertFalse(multimap.hasAnyOfFlags(FLAG_A, FLAG_C));
        assertFalse(multimap.hasAnyOfFlags(FLAG_C, FLAG_B, FLAG_A));
    }

    @Test
    public void hasNonEmptyValue_nonEmpty_returnsTrue() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "AAA");
        multimap.put(FLAG_B, "");
        multimap.put(FLAG_C, "CC1");
        multimap.put(FLAG_C, "CC2");
        multimap.put(FLAG_C, null);

        assertTrue(multimap.hasNonEmptyValue(FLAG_A));
        assertTrue(multimap.hasNonEmptyValue(FLAG_C));
    }
    @Test
    public void hasNonEmptyValue_noFlagOrEmpty_returnsFalse() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "AAA");
        multimap.put(FLAG_B, "");

        assertFalse(multimap.hasNonEmptyValue(FLAG_B));
        assertFalse(multimap.hasNonEmptyValue(FLAG_C));

        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, null);
        multimap.put(FLAG_A, "");

        assertFalse(multimap.hasNonEmptyValue(FLAG_A));
        assertFalse(multimap.hasNonEmptyValue(FLAG_D));
    }


    @Test
    public void getValue_notPresent_emptyOptionalReturned() {
        // Empty map trivially doesn't have the value
        ArgumentMultimap multimap = new ArgumentMultimap();
        assertTrue(multimap.getValue(FLAG_A).isEmpty());

        // Nonempty map but target value not present
        multimap = new ArgumentMultimap();
        multimap.put(FLAG_B, "some data");
        assertTrue(multimap.getValue(FLAG_A).isEmpty());

        // Subsequent additions without adding target value will not change result
        multimap.put(FLAG_C, "some more data");
        assertTrue(multimap.getValue(FLAG_A).isEmpty());
    }

    @Test
    public void getValue_existingValuesSet_lastValueReturned() {
        // Will return a value if that is set
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a value");
        assertTrue(multimap.getValue(FLAG_A).isPresent());
        assertEquals("a value", multimap.getValue(FLAG_A).get());

        // More than one inputs cause it to return the last one.
        multimap.put(FLAG_A, "2nd value");
        multimap.put(FLAG_A, "3rd value");
        assertEquals("3rd value", multimap.getValue(FLAG_A).get());

        // Some test cases with more flags and whitespace checks
        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "\t testing A  ");
        multimap.put(FLAG_B, "testing B 123 \n");
        multimap.put(FLAG_B, "testing B 456\r\n  ");
        multimap.put(FLAG_C, " CC  ");

        assertTrue(multimap.getValue(FLAG_A).isPresent());
        assertTrue(multimap.getValue(FLAG_B).isPresent());
        assertTrue(multimap.getValue(FLAG_C).isPresent());

        assertEquals("testing A", multimap.getValue(FLAG_A).get());
        assertEquals("testing B 456", multimap.getValue(FLAG_B).get());
        assertEquals("CC", multimap.getValue(FLAG_C).get());

        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "A1 value");
        multimap.put(FLAG_B, "B1 value");
        multimap.put(FLAG_A, " A2 value");
        multimap.put(FLAG_B, "B2 value ");
        multimap.put(FLAG_B, "  "); // Empty value.
        assertTrue(multimap.getValue(FLAG_A).isPresent());
        assertTrue(multimap.getValue(FLAG_B).isPresent());
        assertEquals("A2 value", multimap.getValue(FLAG_A).get());
        assertEquals("", multimap.getValue(FLAG_B).get());

        multimap.put(FLAG_A, null); // Empty value.
        assertEquals("", multimap.getValue(FLAG_A).get());
    }

    @Test
    public void getAllValues_existingValuesSet_allValuesReturned() {
        // Will return a value if that is set
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a value");
        assertTrue(multimap.getValue(FLAG_A).isPresent());
        assertEquals(List.of("a value"), multimap.getAllValues(FLAG_A));

        // More than one inputs cause it to return the last one.
        multimap.put(FLAG_A, "2nd value");
        multimap.put(FLAG_A, "3rd value");
        assertEquals(List.of("a value", "2nd value", "3rd value"), multimap.getAllValues(FLAG_A));

        // Some test cases with more flags and whitespace checks
        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "\t testing A  ");
        multimap.put(FLAG_B, "testing B 123 \n");
        multimap.put(FLAG_B, "testing B 456\r\n  ");
        multimap.put(FLAG_C, " CC  ");

        assertEquals(List.of("testing A"), multimap.getAllValues(FLAG_A));
        assertEquals(List.of("testing B 123", "testing B 456"), multimap.getAllValues(FLAG_B));
        assertEquals(List.of("CC"), multimap.getAllValues(FLAG_C));

        multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "A1 value");
        multimap.put(FLAG_B, "B1 value");
        multimap.put(FLAG_A, " A2 value");
        multimap.put(FLAG_B, "B2 value ");
        multimap.put(FLAG_B, "  "); // Empty value.
        assertTrue(multimap.getValue(FLAG_A).isPresent());
        assertTrue(multimap.getValue(FLAG_B).isPresent());
        assertEquals(List.of("A1 value", "A2 value"), multimap.getAllValues(FLAG_A));
        assertEquals(List.of("B1 value", "B2 value", ""), multimap.getAllValues(FLAG_B));
    }

    @Test
    public void getPreamble_noPreambleSet_returnsEmptyString() {
        // Empty map has no preamble.
        ArgumentMultimap multimap = new ArgumentMultimap();

        assertNotNull(multimap.getPreamble());
        assertTrue(multimap.getPreamble().isEmpty());
        assertEquals("", multimap.getPreamble());

        // Setting a preamble, then removing it, will remove the preamble.
        multimap.putPreamble("testing");
        multimap.putPreamble(null);

        assertNotNull(multimap.getPreamble());
        assertTrue(multimap.getPreamble().isEmpty());
        assertEquals("", multimap.getPreamble());

        // Similarly for setting to an empty string. Whitespace is trimmed.
        multimap.putPreamble("  ");

        assertNotNull(multimap.getPreamble());
        assertTrue(multimap.getPreamble().isEmpty());
        assertEquals("", multimap.getPreamble());
    }
    @Test
    public void getPreamble_preambleSet_returnsPreamble() {
        // Setting a preamble will give you that one.
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.putPreamble("preamble val");
        assertEquals("preamble val", multimap.getPreamble());

        // Setting a new preamble will replace the old one.
        multimap.putPreamble("preamble val 2.0");
        assertEquals("preamble val 2.0", multimap.getPreamble());

        // Removing the reamble, then setting it again, will have the new one.
        multimap.putPreamble(null);
        multimap.putPreamble("who's this");
        assertEquals("who's this", multimap.getPreamble());
    }


    @Test
    public void verifyNoDuplicateFlagsFor_noDuplicates_exceptionNotThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a");
        multimap.put(FLAG_C, "c1");
        multimap.put(FLAG_C, "c2");

        // If we don't check C, it doesn't matter
        assertDoesNotThrow(() -> multimap.verifyNoDuplicateFlagsFor(FLAG_A, FLAG_B));
        assertDoesNotThrow(() -> multimap.verifyNoDuplicateFlagsFor(FLAG_A));
        assertDoesNotThrow(() -> multimap.verifyNoDuplicateFlagsFor(FLAG_B));
    }

    @Test
    public void verifyNoDuplicateFlagsFor_hasDuplicates_exceptionThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a");
        multimap.put(FLAG_C, "c1");
        multimap.put(FLAG_C, "c2");

        // If we do check C, we must throw an exception
        assertThrows(ParseException.class, () -> multimap.verifyNoDuplicateFlagsFor(FLAG_C));
        assertThrows(ParseException.class, () -> multimap.verifyNoDuplicateFlagsFor(FLAG_A, FLAG_C));
        assertThrows(ParseException.class, () -> multimap.verifyNoDuplicateFlagsFor(FLAG_B, FLAG_C));
        assertThrows(ParseException.class, () -> multimap.verifyNoDuplicateFlagsFor(FLAG_A, FLAG_B, FLAG_C));
    }


    @Test
    public void verifyNoExtraneousFlagsOnTopOf_noExtras_exceptionNotThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a");
        multimap.put(FLAG_B, "b1");
        multimap.put(FLAG_B, "b2");

        assertDoesNotThrow(() -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_A, FLAG_B)); // exact match is ok
        assertDoesNotThrow(() -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_A, FLAG_B, FLAG_C)); // superset is ok
    }

    @Test
    public void verifyNoExtraneousFlagsOnTopOf_hasExtras_exceptionThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "a");
        multimap.put(FLAG_B, "b1");
        multimap.put(FLAG_B, "b2");

        assertThrows(ParseException.class, () -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_A)); // extra B
        assertThrows(ParseException.class, () -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_B)); // extra A
        assertThrows(ParseException.class, () -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_C)); // extra A, B
        assertThrows(ParseException.class, () -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_A, FLAG_C)); // extra B
        assertThrows(ParseException.class, () -> multimap.verifyNoExtraneousFlagsOnTopOf(FLAG_B, FLAG_C)); // extra A
    }


    @Test
    public void verifyAllEmptyValuesAssignedFor_allEmpty_exceptionNotThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "");
        multimap.put(FLAG_A, "  ");
        multimap.put(FLAG_A, null);
        multimap.put(FLAG_B, "bb");
        multimap.put(FLAG_B, "bbb");
        multimap.put(FLAG_C, "");

        // A, C, D are empty
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_C));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_D));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A, FLAG_C));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A, FLAG_D));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_D, FLAG_C));
        assertDoesNotThrow(() -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A, FLAG_C, FLAG_D));
    }

    @Test
    public void verifyAllEmptyValuesAssignedFor_someNonEmpty_exceptionThrown() {
        ArgumentMultimap multimap = new ArgumentMultimap();
        multimap.put(FLAG_A, "");
        multimap.put(FLAG_A, "A");
        multimap.put(FLAG_A, "  ");
        multimap.put(FLAG_B, "B");

        // A and B aren't empty
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A));
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_B));
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A, FLAG_B));
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_A, FLAG_C));
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_D, FLAG_C, FLAG_B));
        assertThrows(ParseException.class, () -> multimap.verifyAllEmptyValuesAssignedFor(FLAG_C, FLAG_B, FLAG_A));
    }


}
