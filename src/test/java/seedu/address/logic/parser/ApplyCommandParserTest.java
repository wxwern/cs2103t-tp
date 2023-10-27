package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

class ApplyCommandParserTest {

    private static final String VALID_ARGS_1 = "1 --title SWE";
    private static final String VALID_ARGS_2 = "1 --title SWE --by 11-10-2022";
    private static final String VALID_ARGS_3 = "1 --title SWE --desc Pay: $100";
    private static final String VALID_ARGS_4 = "1 --title SWE --stage interview";
    private static final String VALID_ARGS_5 = "1 --title SWE --stat pending";
    private static final String VALID_ARGS_6 = "--id TEST_ID --title SWE";
    private static final String INVALID_ARGS_1 = "1";
    private static final String INVALID_ARGS_2 = "--title SWE";

    @Test
    void parse_validArgs_doesNotThrowException() {
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_1));
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_2));
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_3));
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_4));
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_5));
        assertDoesNotThrow(() -> new ApplyCommandParser().parse(VALID_ARGS_6));
    }

    @Test
    void parse_invalidArgs_throwsException() {
        assertThrows(ParseException.class, () -> new ApplyCommandParser().parse(INVALID_ARGS_1));
        assertThrows(ParseException.class, () -> new ApplyCommandParser().parse(INVALID_ARGS_2));
    }
}
