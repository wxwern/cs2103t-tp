package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_NONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_REVERSE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;
import static seedu.address.model.Model.COMPARATOR_ADDRESS;
import static seedu.address.model.Model.COMPARATOR_EMAIL;
import static seedu.address.model.Model.COMPARATOR_ID;
import static seedu.address.model.Model.COMPARATOR_NAME;
import static seedu.address.model.Model.COMPARATOR_PHONE;
import static seedu.address.model.Model.COMPARATOR_URL;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Contact;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    // TODO: Tech debt - implement tests

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns a SortCommand object for execution.
     */
    public SortCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        SortCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        if (argMultimap.hasFlag(FLAG_NONE)) {
            return new SortCommand(null, null, true);
        }

        Boolean isReverse = false;
        Comparator<Contact> contactComparator = null;
        Comparator<JobApplication> applicationComparator = null;

        if (argMultimap.hasFlag(FLAG_REVERSE)) {
            isReverse = true;
        }
        //I feel there should be a more elegant way to write this.
        if (argMultimap.hasFlag(FLAG_ADDRESS)) {
            if (isReverse) {
                contactComparator = COMPARATOR_ADDRESS.reversed();
            } else {
                contactComparator = COMPARATOR_ADDRESS;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_EMAIL)) {
            if (isReverse) {
                contactComparator = COMPARATOR_EMAIL.reversed();
            } else {
                contactComparator = COMPARATOR_EMAIL;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_NAME)) {
            if (isReverse) {
                contactComparator = COMPARATOR_NAME.reversed();
            } else {
                contactComparator = COMPARATOR_NAME;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_ID)) {
            if (isReverse) {
                contactComparator = COMPARATOR_ID.reversed();
            } else {
                contactComparator = COMPARATOR_ID;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_PHONE)) {
            if (isReverse) {
                contactComparator = COMPARATOR_PHONE.reversed();
            } else {
                contactComparator = COMPARATOR_PHONE;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_URL)) {
            if (isReverse) {
                contactComparator = COMPARATOR_URL.reversed();
            } else {
                contactComparator = COMPARATOR_URL;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_STALE)) {
            if (isReverse) {
                applicationComparator = JobApplication.LAST_UPDATED_COMPARATOR.reversed();
            } else {
                applicationComparator = JobApplication.LAST_UPDATED_COMPARATOR;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_STAGE)) {
            if (isReverse) {
                applicationComparator = JobApplication.STAGE_COMPARATOR.reversed();
            } else {
                applicationComparator = JobApplication.STAGE_COMPARATOR;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_STATUS)) {
            if (isReverse) {
                applicationComparator = JobApplication.STATUS_COMPARATOR.reversed();
            } else {
                applicationComparator = JobApplication.STATUS_COMPARATOR;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        } else if (argMultimap.hasFlag(FLAG_DEADLINE)) {
            if (isReverse) {
                applicationComparator = JobApplication.DEADLINE_COMPARATOR.reversed();
            } else {
                applicationComparator = JobApplication.DEADLINE_COMPARATOR;
            }
            return new SortCommand(contactComparator, applicationComparator, false);
        }

        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

}
