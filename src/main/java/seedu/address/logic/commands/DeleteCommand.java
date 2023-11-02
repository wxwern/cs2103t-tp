package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECURSIVE;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Type;

/**
 * Deletes a contact identified using its displayed index or its contact id from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.fromUniqueFlags(
            FLAG_RECURSIVE
    ).configureValueMap(map -> {
        // Add value autocompletion data for:
        map.put(null /* preamble */, (command, model) -> {

            String partialText = command.getAutocompletableText();
            if (partialText.isEmpty() || StringUtil.isNonZeroUnsignedInteger(partialText)) {
                // Preamble is likely of type Index
                return Stream.empty();

            } else {
                // Preamble is likely of type Id
                return model.getAddressBook()
                        .getContactList()
                        .stream()
                        .map(o -> o.getId().value);
            }
        });
    });

    public static final String MESSAGE_ILLEGAL_DELETE = "Contacts of type %s cannot have links to a parent contact.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the contact identified by the index number used in the displayed contact list.\n"
            + "Parameters: "
            + "INDEX/ID "
            + "[" + FLAG_RECURSIVE + "] "
            + "\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " amazon-sg\n"
            + "Example 3: " + COMMAND_WORD + " 1 --recursive\n";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted %s: %s";

    protected final CommandException commandException;

    private final Object selector; // TODO: This is very sus but this will only be used for equals comparison

    private final Function<Model, Contact> contactFunction;

    /**
     * @param targetIndex of the contact to be deleted
     */
    public DeleteCommand(Index targetIndex) {
        this.selector = targetIndex;
        this.contactFunction = (Model model) -> {
            List<Contact> lastShownList = model.getDisplayedContactList();

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
        handleChildren(model, contactToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS,
                contactToDelete.getType(), Messages.format(contactToDelete)));
    }

    protected void handleChildren(Model model, Contact contactToDelete) throws CommandException {
        requireNonNull(model);
        // At this point if the contact is null, the superclass would have thrown exception.
        // Superclass would have also deleted the contact from the list.
        assert contactToDelete != null;
        List<Contact> childrenContacts = contactToDelete.getChildren(model);
        for (Contact child : childrenContacts) {
            // Enforce that only recruiters can have links to parent organizations.
            if (child.getType() != Type.RECRUITER) {
                throw new CommandException(String.format(MESSAGE_ILLEGAL_DELETE, child.getType().toString()));
            }
            // Create a new recruiter with no link to the deleted organization.
            Recruiter newRecruiter = new Recruiter(
                    child.getName(), child.getId(), child.getPhone().orElse(null),
                    child.getEmail().orElse(null), child.getUrl().orElse(null),
                    child.getAddress().orElse(null), child.getTags(), null
            );
            model.setContact(child, newRecruiter);
        }
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
