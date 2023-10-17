package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECRUITER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_RECRUITERS;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public ListCommand parse(String args) {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, FLAG_ORGANIZATION, FLAG_RECRUITER);

        if (areFlagsPresent(argMultimap, FLAG_ORGANIZATION, FLAG_RECRUITER)) {
            return new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
        }

        if (areFlagsPresent(argMultimap, FLAG_ORGANIZATION)) {
            return new ListCommand(PREDICATE_SHOW_ONLY_ORGANIZATIONS);
        } else if (areFlagsPresent(argMultimap, FLAG_RECRUITER)) {
            return new ListCommand(PREDICATE_SHOW_ONLY_RECRUITERS);
        }

        return new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
    }

    /**
     * Returns true if none of the flags contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areFlagsPresent(ArgumentMultimap argumentMultimap, Flag... flags) {
        return Stream.of(flags).allMatch(flag -> argumentMultimap.getValue(flag).isPresent());
    }

}
