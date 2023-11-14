package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EXTRA_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {

    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "--org",
                MESSAGE_EXTRA_FIELDS + "--org");
        assertParseFailure(parser, "--by",
                MESSAGE_EXTRA_FIELDS + "--by");
    }

    @Test
    public void parse_validArgs_returnsReminderCommand() {
        ReminderCommand expectedReminderCommand =
                new ReminderCommand(true);
        assertParseSuccess(parser, "--earliest", expectedReminderCommand);
        ReminderCommand expectedReminderCommand2 =
                new ReminderCommand(false);
        assertParseSuccess(parser, "--latest", expectedReminderCommand2);
    }
}
