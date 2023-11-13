package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_EARLIEST;
import static seedu.address.logic.parser.CliSyntax.FLAG_LATEST;

import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.components.AutocompleteItemSet;
import seedu.address.model.Model;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Reminds the user of urgent or stale applications, similar to the usage of {@code SortCommand}.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "remind";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteItemSet.oneAmongAllOf(
                    FLAG_EARLIEST, FLAG_LATEST
            )
    ).configureValueMap(map -> {
        // Disable value autocompletion for:
        map.put(null /* preamble */, null);
    });

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reminds the user of applications based on the specified flag.\n"
            + "Parameters: " + FLAG_EARLIEST + "/" + FLAG_LATEST + "\n"
            + "Example 1: " + COMMAND_WORD + " --earliest\n"
            + "Example 2: " + COMMAND_WORD + " --latest\n";

    public static final String MESSAGE_REMINDED_EARLIEST = "Reminded user of high priority applications";
    public static final String MESSAGE_REMINDED_LATEST = "Reminded user of low priority applications";
    private final Boolean isUrgent;

    /**
     * Creates a ReminderCommand sorting the {@code JobApplication} entries by deadline.
     * @param isUrgent checks if the {@code ReminderCommand} should display urgent or stale applications.
     */
    public ReminderCommand(Boolean isUrgent) {
        this.isUrgent = isUrgent;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isUrgent) {
            model.updateSortedApplicationList(JobApplication.DEADLINE_COMPARATOR);
            return new CommandResult(MESSAGE_REMINDED_EARLIEST);
        }
        model.updateSortedApplicationList(JobApplication.DEADLINE_COMPARATOR.reversed());
        return new CommandResult(MESSAGE_REMINDED_LATEST);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderCommand)) {
            return false;
        }

        ReminderCommand otherReminderCommand = (ReminderCommand) other;
        return this.isUrgent.equals(otherReminderCommand.isUrgent);
    }
}
