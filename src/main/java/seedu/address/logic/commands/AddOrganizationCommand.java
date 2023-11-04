package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Position;
import seedu.address.model.contact.Status;
import seedu.address.model.contact.Url;
import seedu.address.model.tag.Tag;

/**
 * Adds an organization to the address book.
 */
public class AddOrganizationCommand extends AddCommand {

    /**
     * Creates an AddCommand to add a {@code Organization} to the address book with the given parameters.
     */
    public AddOrganizationCommand(
            Name name,
            Id id,
            Phone phone,
            Email email,
            Url url,
            Address address,
            Set<Tag> tags,
            Status status,
            Position position
    ) {
        super(name, id, phone, email, url, address, tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return super.execute(model);
    }

    @Override
    protected Organization createContact() {
        return new Organization(name, id, phone, email, url, address, tags, null, null);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddOrganizationCommand)) {
            return false;
        }

        AddOrganizationCommand otherAddCommand = (AddOrganizationCommand) other;
        return id.equals(otherAddCommand.id)
                && name.equals(otherAddCommand.name)
                && Objects.equals(phone, otherAddCommand.phone)
                && Objects.equals(email, otherAddCommand.email)
                && Objects.equals(address, otherAddCommand.address)
                && Objects.equals(url, otherAddCommand.url)
                && tags.equals(otherAddCommand.tags);
    }

    @Override
    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("phone", phone)
                .add("email", email)
                .add("url", url)
                .add("address", address)
                .add("tags", tags);
    }
}
