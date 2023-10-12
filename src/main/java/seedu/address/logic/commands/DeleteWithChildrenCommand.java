package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Deletes a contact identified with its displayed index and also deletes its child contacts.
 */
public class DeleteWithChildrenCommand extends DeleteCommand {
    /**
     * @param targetIndex of the contact to be deleted in the current contact list
     */
    public DeleteWithChildrenCommand(Index targetIndex) {
        // TODO add documentation in DG
        super(targetIndex);
    }
}
