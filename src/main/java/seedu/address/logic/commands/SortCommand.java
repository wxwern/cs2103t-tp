package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Comparator;

import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Sorts all contacts in the address book.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER =
            AutocompleteSupplier.from(AutocompleteDataSet.oneAmongAllOf(
            FLAG_NAME, FLAG_ID, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_URL, FLAG_STALE)
    );

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all contacts based on the specified flag.\n"
            + "Parameters: " + FLAG_NAME + "/" + FLAG_ID + "/"
            + FLAG_PHONE + "/" + FLAG_EMAIL + "/"
            + FLAG_ADDRESS + "/" + FLAG_URL + "\n"
            + "Example 1: " + COMMAND_WORD + " --name\n"
            + "Example 2: " + COMMAND_WORD + " --id\n"
            + "Example 3: " + COMMAND_WORD + " --url\n";

    public static final String MESSAGE_SORTED_SUCCESS = "Sorted all contacts";

    // TODO: Tech debt - bad implementation
    private final Comparator<Contact> comparator;
    private final Comparator<JobApplication> comparatorApplication;


    /**
     * Creates a SortCommand sorting the {@code Contact} entries by the specified comparator.
     * @param comparator the comparator determining the sorting of {@code Contact} entries
     */
    public SortCommand(Comparator<Contact> comparator) {
        requireNonNull(comparator);
        this.comparator = comparator;
        this.comparatorApplication = null;
    }

    /**
     * Creats a SortCommand sorting the {@code JobApplication} entries by the specified comparator.
     * @param comparator the comparator determining the sorting of the application
     * @param iAmLosingMyMind does nothing
     */
    public SortCommand(Comparator<JobApplication> comparator, Boolean iAmLosingMyMind) {
        requireNonNull(comparator);
        this.comparatorApplication = comparator;
        this.comparator = null;
    }



    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (comparatorApplication != null) {
            model.updateSortedApplicationList(comparatorApplication);
            return new CommandResult("Sorted by last updated application");
        }
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        model.updateSortedContactList(comparator);
        return new CommandResult(MESSAGE_SORTED_SUCCESS);
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
        return comparator.equals(otherSortCommand.comparator);
    }
}
