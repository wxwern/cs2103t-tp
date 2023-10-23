package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_RECRUITERS;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS_ALL_CONTACTS = "Listed all contacts";
    public static final String MESSAGE_SUCCESS_ORGANIZATIONS = "Listed all organizations";
    public static final String MESSAGE_SUCCESS_RECRUITERS = "Listed all recruiters";

    private final Predicate<Contact> predicate;

    /**
     * Creates a ListCommand listing the {@code Contact} entries of the specified type.
     * @param predicate the predicate determining the type of {@code Contact} to be listed
     */
    public ListCommand(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContactList(predicate);
        if (predicate.equals(PREDICATE_SHOW_ONLY_ORGANIZATIONS)) {
            return new CommandResult(MESSAGE_SUCCESS_ORGANIZATIONS);
        } else if (predicate.equals(PREDICATE_SHOW_ONLY_RECRUITERS)) {
            return new CommandResult(MESSAGE_SUCCESS_RECRUITERS);
        }
        return new CommandResult(MESSAGE_SUCCESS_ALL_CONTACTS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListCommand)) {
            return false;
        }

        ListCommand otherListCommand = (ListCommand) other;
        return predicate.equals(otherListCommand.predicate);
    }
}
