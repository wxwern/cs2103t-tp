package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EXTRA_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "--title --name",
                MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS + "--name, --title");
        assertParseFailure(parser, "--none --descending",
                MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS + "--descending, --none");
        assertParseFailure(parser, "--org",
                MESSAGE_EXTRA_FIELDS + "--org");
    }

    @Test
    public void parse_validArgs_returnsSortCommand() {
        SortCommand expectedSortCommand =
                new SortCommand(Model.COMPARATOR_ADDRESS, null, false);
        assertParseSuccess(parser, "--address", expectedSortCommand);
        SortCommand expectedSortCommand2 =
                new SortCommand(Model.COMPARATOR_ADDRESS, null, true);
        assertParseSuccess(parser, "--none", expectedSortCommand2);
    }
}
