package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteApplicationCommand;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {

        // providing index of contact is valid
        assertParseSuccess(parser, "1", new DeleteCommand(INDEX_FIRST_CONTACT));

        // providing index of job application is valid given the appropriate flag.
        // TODO: Tech debt - Index is no longer limited to just contact. May need to change name.
        assertParseSuccess(parser, "--application 1", new DeleteApplicationCommand(INDEX_FIRST_CONTACT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // index not given
        assertParseFailure(parser, "--recursive", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE));

        // TODO: Index is not proper index
        // TODO: Index is not proper application index
        // TODO: Index is out of bounds for contact
        // TODO: Index is out of bounds for application
    }
}
