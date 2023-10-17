package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Id;

/**
 * Deletes a contact and also deletes its child contacts.
 */
public class DeleteWithChildrenCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = DeleteCommand.MESSAGE_DELETE_CONTACT_SUCCESS + " with"
            + ":\n%2$s";


    /**
     * @param targetIndex of the contact to be deleted in the current contact list
     */
    public DeleteWithChildrenCommand(Index targetIndex) {
        // TODO add documentation in DG
        super(targetIndex);
    }

    public DeleteWithChildrenCommand(Id targetId) {
        super(targetId);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Contact contactToDelete = super.getContact(model);
        super.execute(model);
        // At this point if the contact is null, the superclass would have thrown exception.
        // Superclass would have also deleted the contact from the list.
        assert contactToDelete != null;
        Contact[] childContacts = contactToDelete.getChildren();
        Arrays.stream(childContacts).forEach(contact -> {
            model.deleteContact(contactToDelete);
        });
        return new CommandResult(String.format(
                MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Arrays.stream(childContacts)
                        .map(c -> Messages.format(c) + "\n")
                        .reduce((c1, c2) -> c1 + c2)
                        .orElse("No other contacts found") // I can't find a better method.
        ));
    }
}
