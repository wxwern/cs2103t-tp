package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EARLIEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_LATEST;

import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ReminderCommand} object
 */
public class ReminderCommandParser implements Parser<ReminderCommand> {
    // TODO: Tech debt - implement tests
    /**
     * Parses the given {@code String} of arguments in the context of the
     * {@code ReminderCommand} and returns a {@code ReminderCommand} object for execution.
     */
    public ReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        ReminderCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        argMultimap.verifyAtMostOneOfFlagsUsedOutOf(FLAG_EARLIEST, FLAG_LATEST);

        if (argMultimap.hasFlag(FLAG_EARLIEST)) {
            return new ReminderCommand(true);
        } else if (argMultimap.hasFlag(FLAG_LATEST)) {
            return new ReminderCommand(false);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE));
    }
}
