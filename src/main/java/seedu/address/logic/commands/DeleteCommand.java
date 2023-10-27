package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECURSIVE;

import java.util.List;
import java.util.function.Function;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Type;

/**
 * Deletes a contact identified using its displayed index or its contact id from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.fromUniqueFlags(
            FLAG_ID, FLAG_RECURSIVE
    ).configureValueMap(map -> {
        // Add value autocompletion data for:
        map.put(FLAG_ID, (command, model) -> model.getAddressBook()
                .getContactList()
                .stream()
                .filter(c -> c.getType() == Type.ORGANIZATION)
                .map(o -> o.getId().value)
        );

        // Disable value autocompletion for:
        map.put(FLAG_RECURSIVE, null);
    });


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + FLAG_ID + " ID "
            + FLAG_RECURSIVE + " RECURSIVE "
            + "\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " --id 0d0h4\n"
            + "Example 3: " + COMMAND_WORD + " 1 --recursive\n";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    private final Object selector; // TODO: This is very sus but this will only be used for equals comparison

    private final Function<Model, Contact> contactFunction;

    private final CommandException commandException;

    /**
     * @param targetIndex of the contact to be deleted
     */
    public DeleteCommand(Index targetIndex) {
        this.selector = targetIndex;
        this.contactFunction = (Model model) -> {
            List<Contact> lastShownList = model.getFilteredContactList();

            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                return null;
            }

            return lastShownList.get(targetIndex.getZeroBased());
        };
        this.commandException = new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
    }

    /**
     * @param targetId of the contact to be deleted
     */
    public DeleteCommand(Id targetId) {
        this.selector = targetId;
        this.contactFunction = (Model model) -> model.getContactById(targetId);
        this.commandException = new CommandException(Messages.MESSAGE_NO_SUCH_CONTACT);
    }

    /**
     * Creates an executable DeleteCommand based on whether to delete recursively.
     *
     * @param targetIndex of the contact to delete in the current list
     * @param shouldDeleteChildren specifies if child contacts should be deleted
     */
    public static DeleteCommand selectIndex(Index targetIndex, boolean shouldDeleteChildren) {
        // TODO: Add documentation to DG
        requireNonNull(targetIndex);
        if (shouldDeleteChildren) {
            return new DeleteWithChildrenCommand(targetIndex);
        }
        return new DeleteCommand(targetIndex);
    }

    /**
     * Creates an executable DeleteCommand based on whether to delete recursively.
     *
     * @param id of the contact to delete in the current list
     * @param shouldDeleteChildren specifies if child contacts should be deleted
     */
    public static DeleteCommand selectId(Id id, boolean shouldDeleteChildren) {
        // TODO: Add documentation to DG
        requireNonNull(id);
        if (shouldDeleteChildren) {
            return new DeleteWithChildrenCommand(id);
        }
        return new DeleteCommand(id);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Contact contactToDelete = this.contactFunction.apply(model);
        if (contactToDelete == null) {
            throw commandException;
        }
        model.deleteContact(contactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, Messages.format(contactToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return selector.equals(otherDeleteCommand.selector);
    }

    @Override
    public String toString() {
        // TODO: replace this toString method with sth better than targetIndex
        // To not replace yet until we do the tests
        return new ToStringBuilder(this)
                .add("targetIndex", selector)
                .toString();
    }

    /**
     * Gives the contact that the DeleteCommand is going to delete if a model is given.
     * If such a contact does not exist, gives null.
     */
    protected Contact getContact(Model model) {
        return contactFunction.apply(model);
    }
}
