package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.ContactId;

/**
 * Deletes a contact identified using its ID from the addressbook.
 */
public class DeleteByIdCommand extends DeleteCommand {

    public static final String NO_SUCH_CONTACT_WITH_ID = "No such contact with id: %s";
    private final ContactId contactId;

    /**
     * @param id of the contact to be deleted.
     */
    public DeleteByIdCommand(ContactId id) {
        // TODO: Might want to change this since null is dangerous
        super(null);
        this.contactId = id;
    }

    @Override
    public CommandResult execute(Model model) {
        // TODO: Implement the get function based on id for the model
        Contact c = model.getContactById(contactId);
        if (c == null) {
            return new CommandResult(String.format(NO_SUCH_CONTACT_WITH_ID, contactId));
        }
        model.deleteContact(c);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS, c));
    }
}
