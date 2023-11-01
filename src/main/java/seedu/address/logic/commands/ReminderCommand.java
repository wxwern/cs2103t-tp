package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_STALE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URGENT;

import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.model.Model;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Reminds the user of urgent or stale applications, similar to the usage of {@code SortCommand}.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "remind";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteDataSet.oneAmongAllOf(
                    FLAG_STALE, FLAG_URGENT
            )
    ).configureValueMap(map -> {
        // Disable value autocompletion for:
        map.put(null /* preamble */, null);
    });

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reminds the user of applications based on the specified flag.\n"
            + "Parameters: " + FLAG_STALE + "/" + FLAG_URGENT + "\n"
            + "Example 1: " + COMMAND_WORD + " --stale\n"
            + "Example 2: " + COMMAND_WORD + " --urgent\n";

    public static final String MESSAGE_REMINDED_URGENT = "Reminded user of urgent applications";
    public static final String MESSAGE_REMINDED_STALE = "Reminded user of stale applications";
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
            return new CommandResult(MESSAGE_REMINDED_URGENT);
        }
        model.updateSortedApplicationList(JobApplication.DEADLINE_COMPARATOR.reversed());
        return new CommandResult(MESSAGE_REMINDED_STALE);
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
        if (this.isUrgent) {
            return otherReminderCommand.isUrgent;
        } else {
            return !otherReminderCommand.isUrgent;
        }
    }
}
