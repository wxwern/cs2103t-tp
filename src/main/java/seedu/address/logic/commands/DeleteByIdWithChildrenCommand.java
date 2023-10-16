package seedu.address.logic.commands;

import static seedu.address.logic.commands.DeleteByIdCommand.NO_SUCH_CONTACT_WITH_ID;

import java.util.Arrays;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.ContactId;

/**
 * Deletes a contact identified with its id and also deletes its child contacts.
 */
public class DeleteByIdWithChildrenCommand extends DeleteCommand {

    private final ContactId contactId;
    /**
     * @param id of the contact to be deleted along with its child contacts.
     */
    public DeleteByIdWithChildrenCommand(ContactId id) {
        super(null);
        this.contactId = id;
    }

    @Override
    public CommandResult execute(Model model) {
        Contact contactToDelete = model.getContactById(contactId);
        if (contactToDelete == null) {
            return new CommandResult(String.format(NO_SUCH_CONTACT_WITH_ID, contactId));
        }
        Contact[] childContacts = contactToDelete.getChildren();
        model.deleteContact(contactToDelete);
        Arrays.stream(childContacts).forEach(contact -> {
            model.deleteContact(contactToDelete);
        });
        return new CommandResult(String.format(
                DeleteWithChildrenCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                Messages.format(contactToDelete),
                Arrays.stream(childContacts)
                        .map(c -> Messages.format(c) + "\n")
                        .reduce((c1, c2) -> c1 + c2)
        ));
    }
}
