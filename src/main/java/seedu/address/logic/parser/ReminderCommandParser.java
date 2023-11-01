package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URGENT;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ReminderCommandParser implements Parser<ReminderCommand> {
    // TODO: Tech debt - implement tests
    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public ReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        SortCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        if (argMultimap.hasFlag(FLAG_URGENT)) {
            return new ReminderCommand(true);
        } else if (argMultimap.hasFlag(FLAG_STALE)) {
            return new ReminderCommand(false);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }
}
