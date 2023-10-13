package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Organization;

public class AddOrganizationCommand extends AddCommand{


    public AddOrganizationCommand(Contact contact) {
        super(contact);
    }


//    @Override
//    public CommandResult execute(Model model) throws CommandException {
//        requireNonNull(model);
//
//        if (model.hasContact(toAdd)) {
//            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
//        }
//
//        model.addContact(toAdd);
//        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
//    }

}
