package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ArgumentTokenizerTest {

    private final Flag unknownFlagMatchingDefault1 = new Flag("abc");
    private final Flag unknownFlagMatchingDefault2 = new Flag("def");
    private final Flag unknownFlagMatchingDefault3 = new Flag("ghi");
    private final Flag unknownFlagNonDefault = Flag.ofCustomFormat("u", "**", null);

    private final Flag defaultFlag = new Flag("flag");
    private final Flag pSlash = Flag.ofCustomFormat("p", null, "/");
    private final Flag dashT = Flag.ofCustomFormat("t", "-", null);
    private final Flag hatQ = Flag.ofCustomFormat("Q", "^", null);

    @Test
    public void tokenize_emptyArgsString_noValues() {
        String argsString = "  ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);

        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);
    }

    private void assertPreamblePresent(ArgumentMultimap argMultimap, String expectedPreamble) {
        assertEquals(expectedPreamble, argMultimap.getPreamble());
    }

    private void assertPreambleEmpty(ArgumentMultimap argMultimap) {
        assertTrue(argMultimap.getPreamble().isEmpty());
    }

    /**
     * Asserts all the arguments in {@code argMultimap} with {@code flag} match the {@code expectedValues}
     * and only the last value is returned upon calling {@code ArgumentMultimap#getValue(Prefix)}.
     */
    private void assertArgumentPresent(ArgumentMultimap argMultimap, Flag flag, String... expectedValues) {

        // Verify the last value is returned
        assertEquals(expectedValues[expectedValues.length - 1], argMultimap.getValue(flag).get());

        // Verify the number of values returned is as expected
        assertEquals(expectedValues.length, argMultimap.getAllValues(flag).size());

        // Verify all values returned are as expected and in order
        for (int i = 0; i < expectedValues.length; i++) {
            assertEquals(expectedValues[i], argMultimap.getAllValues(flag).get(i));
        }
    }

    private void assertArgumentAbsent(ArgumentMultimap argMultimap, Flag flag) {
        assertFalse(argMultimap.getValue(flag).isPresent());
    }

    @Test
    public void tokenize_noFlags_allTakenAsPreamble() {
        String argsString = "  some random string /t tag with leading and trailing spaces ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);

        // Same string expected as preamble, but leading/trailing spaces should be trimmed
        assertPreamblePresent(argMultimap, argsString.trim());

    }

    @Test
    public void tokenize_oneArgument() {
        // Preamble present
        String argsString = "  Some preamble string p/ Argument value ";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreamblePresent(argMultimap, "Some preamble string");
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

        // No preamble
        argsString = " p/   Argument value ";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash);
        assertPreambleEmpty(argMultimap);
        assertArgumentPresent(argMultimap, pSlash, "Argument value");

    }

    @Test
    public void tokenize_multipleArguments() {
        // Only two arguments are present
        String argsString = "SomePreambleString -t dashT-Value p/ pSlash value";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentAbsent(argMultimap, hatQ);

        // All three arguments are present
        argsString = "Different Preamble String ^Q 111 -t dashT-Value p/ pSlash value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "Different Preamble String");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value");
        assertArgumentPresent(argMultimap, hatQ, "111");

        /* Also covers: Reusing of the tokenizer multiple times */

        // Reuse tokenizer on an empty string to ensure ArgumentMultimap is correctly reset
        // (i.e. no stale values from the previous tokenizing remain)
        argsString = "";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreambleEmpty(argMultimap);
        assertArgumentAbsent(argMultimap, pSlash);
    }

    @Test
    public void tokenize_multipleArgumentsWithRepeats() {
        // Two arguments repeated, some have empty values
        String argsString = "SomePreambleString -t dashT-Value ^Q ^Q -t another dashT value p/ pSlash value -t";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleString");
        assertArgumentPresent(argMultimap, pSlash, "pSlash value");
        assertArgumentPresent(argMultimap, dashT, "dashT-Value", "another dashT value", "");
        assertArgumentPresent(argMultimap, hatQ, "", "");
    }

    @Test
    public void tokenize_multipleArgumentsJoined() {
        // Any flags not surrounded by spaces must not be present, and if surrounded must be.
        String argsString = "SomePreambleStringp/ pSlash joined-tjoined"
                + " -t " + "not joined^Qjoined"
                + " ^Q " + "p/prefixed postfixed-t";

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertPreamblePresent(argMultimap, "SomePreambleStringp/ pSlash joined-tjoined");
        assertArgumentAbsent(argMultimap, pSlash);
        assertArgumentPresent(argMultimap, dashT, "not joined^Qjoined");
        assertArgumentPresent(argMultimap, hatQ, "p/prefixed postfixed-t");
    }

    @Test
    public void tokenize_flagsNotExplicitlyGiven_defaultSyntaxFlagsAreAutoTokenized() {

        /* Covers: Standard syntax flags (--flag) */

        // Flags matching the standard syntax should be auto-tokenized.
        String argsString = unknownFlagMatchingDefault1 + " some value 0";
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(argsString);
        assertArgumentPresent(argMultimap, unknownFlagMatchingDefault1, "some value 0");

        argsString = unknownFlagMatchingDefault2 + " some value blah";
        argMultimap = ArgumentTokenizer.tokenize(argsString);
        assertArgumentPresent(argMultimap, unknownFlagMatchingDefault2, "some value blah");

        argsString = unknownFlagMatchingDefault3 + " yet another  value";
        argMultimap = ArgumentTokenizer.tokenize(argsString);
        assertArgumentPresent(argMultimap, unknownFlagMatchingDefault3, "yet another  value");

        // Other flags being specified or not will have no influence on
        // whether the standard-syntax-flag is correctly tokenized.
        argsString = defaultFlag + " another value";
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ, defaultFlag); // All provided.
        assertArgumentPresent(argMultimap, defaultFlag, "another value");

        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ); // Omitted the target default.
        assertArgumentPresent(argMultimap, defaultFlag, "another value");

        argMultimap = ArgumentTokenizer.tokenize(argsString); // Not specified at all.
        assertArgumentPresent(argMultimap, defaultFlag, "another value");

        /* Covers: Non-standard syntax flags */

        // Non-standard flags not given to the tokenizer should not return any values
        argsString = unknownFlagNonDefault + " some value 1";
        argMultimap = ArgumentTokenizer.tokenize(argsString);
        assertArgumentAbsent(argMultimap, unknownFlagNonDefault);
        assertPreamblePresent(argMultimap, argsString); // Unknown flag is taken as part of preamble

        // Also works if some flags already given but the non-standard flag is not one of them.
        argMultimap = ArgumentTokenizer.tokenize(argsString, pSlash, dashT, hatQ);
        assertArgumentAbsent(argMultimap, unknownFlagNonDefault);
        assertPreamblePresent(argMultimap, argsString); // Unknown flag is taken as part of preamble

    }

    @Test
    public void equalsMethod() {
        Flag aaa = Flag.ofCustomFormat("aaa", "-", "");

        assertEquals(aaa, aaa);
        assertEquals(aaa, Flag.ofCustomFormat("aaa", "-", null));

        assertNotEquals(aaa, "-aaa");
        assertNotEquals(aaa, Flag.ofCustomFormat("aab", "-", null));
        assertNotEquals(aaa, Flag.ofCustomFormat("aaa", null, "/"));
    }

}
