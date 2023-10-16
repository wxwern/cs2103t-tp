package seedu.address.logic.commands;

import seedu.address.model.person.Contact;

/**
 * Adds an organisation to the address book.
 */
public class AddOrganizationCommand extends AddCommand {

    public AddOrganizationCommand(Contact contact) {
        super(contact);
    }

}
