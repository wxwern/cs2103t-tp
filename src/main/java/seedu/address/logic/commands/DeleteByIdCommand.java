package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.person.ContactId;

/**
 * Deletes a contact identified using its ID from the addressbook.
 */
public class DeleteByIdCommand extends DeleteCommand {

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
        // Currently overridden to avoid NPE
        return null;
    }
}
