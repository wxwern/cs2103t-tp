package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IdTest {

    private static final String RANDOM_ID_REGEX = "i-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    @Test
    public void constructor_noArguments_randomId() {
        assertTrue(new Id().value.matches(RANDOM_ID_REGEX));
    }

    @Test
    public void constructor_inputValue_usesValueAsId() {
        assertEquals("abcde_ABCDE-123", new Id("abcde_ABCDE-123").value);
    }

    @Test
    public void synthesizeFrom_inputValue_expectedFormat() {
        // Eq: All letters auto-lowercased
        String value = "John Smith";
        String regex = "john-smith-[0-9a-f]{6}";

        assertTrue(Id.synthesizeFrom(value).value.matches(regex));

        // Eq: Format non-standard chars to "x"
        value = " Non-std@CHARACTERS résumé?";
        regex = "non-stdxcharacters-rxsumxx-[0-9a-f]{6}";

        assertTrue(Id.synthesizeFrom(value).value.matches(regex));

        // Eq: Strip repeated dashes and spaces with single "-"
        value = "a123- numbers --456_7890- ";
        regex = "a123-numbers-456_7890-[0-9a-f]{6}";

        assertTrue(Id.synthesizeFrom(value).value.matches(regex));

        // Eq: Require leading letters
        value = "1á2b3c";
        regex = "i-1x2b3c-[0-9a-f]{6}";

        assertTrue(Id.synthesizeFrom(value).value.matches(regex));

        // Combined tests
        value = "  123-- abc*def _--___-456áà?7890**ABC-  ";
        regex = "i-123-abcxdef-456xxx7890xxabc-[0-9a-f]{6}";
        assertTrue(Id.synthesizeFrom(value).value.matches(regex));
    }

    @Test
    public void synthesizeFrom_blankValue_randomId() {
        assertTrue(Id.synthesizeFrom("").value.matches(RANDOM_ID_REGEX));
        assertTrue(Id.synthesizeFrom("  ").value.matches(RANDOM_ID_REGEX));
        assertTrue(Id.synthesizeFrom(null).value.matches(RANDOM_ID_REGEX));
    }

}
