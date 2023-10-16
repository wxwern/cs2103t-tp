package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditContactDescriptor;
import seedu.address.model.person.Contact;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Contact.
 */
public class ContactUtil {

    /**
     * Returns an add command string for adding the {@code contact}.
     */
    public static String getAddCommand(Contact contact) {
        return AddCommand.COMMAND_WORD + " " + getContactDetails(contact);
    }

    /**
     * Returns the part of command string for the given {@code contact}'s details.
     */
    public static String getContactDetails(Contact contact) {
        StringBuilder sb = new StringBuilder();
        sb.append(FLAG_NAME + contact.getName().fullName + " ");
        sb.append(FLAG_ID + contact.getId().value + " ");
        sb.append(FLAG_PHONE + contact.getPhone().value + " ");
        sb.append(FLAG_EMAIL + contact.getEmail().value + " ");
        sb.append(FLAG_ADDRESS + contact.getAddress().value + " ");
        contact.getTags().stream().forEach(
            s -> sb.append(FLAG_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditContactDescriptor}'s details.
     */
    public static String getEditContactDescriptorDetails(EditContactDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(FLAG_NAME).append(name.fullName).append(" "));
        descriptor.getId().ifPresent(id -> sb.append(FLAG_ID).append(id.value).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(FLAG_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(FLAG_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(FLAG_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(FLAG_TAG);
            } else {
                tags.forEach(s -> sb.append(FLAG_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
