package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_NONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TITLE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;

import java.util.Comparator;
import java.util.Objects;

import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteConstraint;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Sorts contacts and job applications in the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteDataSet.concat(
                    AutocompleteDataSet.oneAmongAllOf(
                            FLAG_NAME, FLAG_ID, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_URL,
                            FLAG_STALE, FLAG_STAGE, FLAG_STATUS, FLAG_DEADLINE, FLAG_TITLE,
                            FLAG_NONE
                    ), AutocompleteDataSet.oneAmongAllOf(
                            FLAG_ASCENDING, FLAG_DESCENDING
                    )
            ).addConstraint(
                    AutocompleteConstraint.oneAmongAllOf(
                            FLAG_NONE, FLAG_ASCENDING, FLAG_DESCENDING
                    )
            )
    ).configureValueMap(map -> {
        // Disable value autocompletion for:
        map.put(null /* preamble */, null);
    });

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts or applications based on the specified flag.\n"
            + "Parameters: " + FLAG_NAME + "/" + FLAG_ID + "/"
            + FLAG_PHONE + "/" + FLAG_EMAIL + "/"
            + FLAG_ADDRESS + "/" + FLAG_URL + "/"
            + FLAG_DEADLINE + "/" + FLAG_STATUS + "/"
            + FLAG_STAGE + "/" + FLAG_STALE + "/"
            + FLAG_TITLE + "/" + FLAG_NONE
            + " [" + FLAG_ASCENDING + "/" + FLAG_DESCENDING + "]\n"
            + "Example 1: " + COMMAND_WORD + " --name\n"
            + "Example 2: " + COMMAND_WORD + " --id" + " --descending\n"
            + "Example 3: " + COMMAND_WORD + " --stale" + " --ascending\n";

    public static final String MESSAGE_SORTED_CONTACTS = "Sorted contacts as specified";
    public static final String MESSAGE_SORTED_APPLICATIONS = "Sorted applications as specified";
    public static final String MESSAGE_RESET_SORTING = "Sorting reset to default order";

    private final Comparator<Contact> comparatorContact;
    private final Comparator<JobApplication> comparatorApplication;
    private final Boolean isReset;

    /**
     * Creates a SortCommand sorting the {@code Contact} entries and the {@code JobApplication}
     * entries by the specified comparators.
     * @param contactComparator the comparator determining the sorting of {@code Contact} entries.
     *                          May be null, should the SortCommand seek to sort applications instead.
     * @param applicationComparator the comparator determining the sorting of {@code JobApplication} entries.
     *                              May be null, should the SortCommand seek to sort contacts.
     * @param isReset checks if the {@code SortCommand} is going to reset the sorting order
     *                to the original order.
     */
    public SortCommand(Comparator<Contact> contactComparator,
                       Comparator<JobApplication> applicationComparator, Boolean isReset) {
        this.comparatorContact = contactComparator;
        this.comparatorApplication = applicationComparator;
        this.isReset = isReset;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isReset) {
            //both comparators should be null here
            model.updateSortedContactList(comparatorContact);
            model.updateSortedApplicationList(comparatorApplication);
            return new CommandResult(MESSAGE_RESET_SORTING);
        }
        if (comparatorApplication != null) {
            model.updateSortedApplicationList(comparatorApplication);
            return new CommandResult(MESSAGE_SORTED_APPLICATIONS);
        }
        model.updateSortedContactList(comparatorContact);
        return new CommandResult(MESSAGE_SORTED_CONTACTS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherSortCommand = (SortCommand) other;
        if (this.isReset) {
            return otherSortCommand.isReset;
        }
        return Objects.equals(comparatorContact, otherSortCommand.comparatorContact)
                && Objects.equals(comparatorApplication, otherSortCommand.comparatorApplication);
    }
}
