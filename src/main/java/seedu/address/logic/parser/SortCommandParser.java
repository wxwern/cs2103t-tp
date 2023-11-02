package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;
import static seedu.address.model.Model.COMPARATOR_ADDRESS;
import static seedu.address.model.Model.COMPARATOR_EMAIL;
import static seedu.address.model.Model.COMPARATOR_ID;
import static seedu.address.model.Model.COMPARATOR_NAME;
import static seedu.address.model.Model.COMPARATOR_PHONE;
import static seedu.address.model.Model.COMPARATOR_URL;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    //this is where i create the comparators
    // TODO: Tech debt - implement tests

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        SortCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        if (argMultimap.hasFlag(FLAG_ADDRESS)) {
            return new SortCommand(COMPARATOR_ADDRESS);
        } else if (argMultimap.hasFlag(FLAG_EMAIL)) {
            return new SortCommand(COMPARATOR_EMAIL);
        } else if (argMultimap.hasFlag(FLAG_NAME)) {
            return new SortCommand(COMPARATOR_NAME);
        } else if (argMultimap.hasFlag(FLAG_ID)) {
            return new SortCommand(COMPARATOR_ID);
        } else if (argMultimap.hasFlag(FLAG_PHONE)) {
            return new SortCommand(COMPARATOR_PHONE);
        } else if (argMultimap.hasFlag(FLAG_URL)) {
            return new SortCommand(COMPARATOR_URL);
        } else if (argMultimap.hasFlag(FLAG_STALE)) {
            return new SortCommand(JobApplication.LAST_UPDATED_COMPARATOR, true);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

}
