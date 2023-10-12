package seedu.address.logic.commands;

import seedu.address.model.person.ContactId;

/**
 * Deletes a contact identified with its id and also deletes its child contacts.
 */
public class DeleteByIdWithChildrenCommand extends DeleteByIdCommand {

    /**
     * @param id of the contact to be deleted along with its child contacts
     */
    public DeleteByIdWithChildrenCommand(ContactId id) {
        super(id);
    }
}
