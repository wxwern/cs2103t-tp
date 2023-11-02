package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_NOT_APPLIED;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECRUITER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_NOT_APPLIED_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_RECRUITERS;

import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                    ListCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        if (argMultimap.hasAllOfFlags(FLAG_ORGANIZATION, FLAG_RECRUITER)) {
            return new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
        }

        if (argMultimap.hasFlag(FLAG_ORGANIZATION)) {
            return new ListCommand(PREDICATE_SHOW_ONLY_ORGANIZATIONS);
        } else if (argMultimap.hasFlag(FLAG_RECRUITER)) {
            return new ListCommand(PREDICATE_SHOW_ONLY_RECRUITERS);
        }
        if (argMultimap.hasFlag(FLAG_NOT_APPLIED)) {
            return new ListCommand(PREDICATE_SHOW_NOT_APPLIED_ORGANIZATIONS);
        }

        return new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
    }

}
