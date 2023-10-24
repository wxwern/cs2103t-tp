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
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Url;
import seedu.address.model.tag.Tag;

/**
 * Adds a recruiter to the address book.
 */
public class AddRecruiterCommand extends AddCommand {

    protected final Id oid;

    /**
     * Creates an AddCommand to add a {@code Recruiter} to the address book with the given parameters.
     */
    public AddRecruiterCommand(
            Name name,
            Id id,
            Phone phone,
            Email email,
            Url url,
            Address address,
            Set<Tag> tags,
            Id oid
    ) {
        super(name, id, phone, email, url, address, tags);
        this.oid = oid;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return super.execute(model);
    }

    @Override
    protected Recruiter createContact() {
        return new Recruiter(name, id, phone, email, url, address, tags, oid);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddRecruiterCommand)) {
            return false;
        }

        AddRecruiterCommand otherAddCommand = (AddRecruiterCommand) other;
        return id.equals(otherAddCommand.id)
                && name.equals(otherAddCommand.name)
                && Objects.equals(phone, otherAddCommand.phone)
                && Objects.equals(email, otherAddCommand.email)
                && Objects.equals(address, otherAddCommand.address)
                && Objects.equals(url, otherAddCommand.url)
                && tags.equals(otherAddCommand.tags)
                && Objects.equals(oid, otherAddCommand.oid);
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
                .add("tags", tags)
                .add("oid", oid);
    }
}
