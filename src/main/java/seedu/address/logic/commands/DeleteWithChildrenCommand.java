package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;

/**
 * Deletes a contact identified with its displayed index and also deletes its child contacts.
 */
public class DeleteWithChildrenCommand extends DeleteCommand {

    public static String MESSAGE_DELETE_CONTACT_SUCCESS = DeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS + "with:\n%s";

    private final Index targetIndex;

    /**
     * @param targetIndex of the contact to be deleted in the current contact list
     */
    public DeleteWithChildrenCommand(Index targetIndex) {
        // TODO add documentation in DG
        super(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToDelete = lastShownList.get(targetIndex.getZeroBased());
        Contact[] childContacts = contactToDelete.getChildren();
        model.deleteContact(contactToDelete);
        Arrays.stream(childContacts).forEach(contact -> {
            model.deleteContact(contactToDelete);
        });
        return new CommandResult(String.format(
                MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Arrays.stream(childContacts)
                        .map(c -> Messages.format(c) + "\n")
                        .reduce((c1, c2) -> c1 + c2)
        ));
    }
}
