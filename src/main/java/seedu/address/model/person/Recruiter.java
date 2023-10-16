package seedu.address.model.person;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Recruiter in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruiter extends Contact {

    // TODO: Convert to ID class.
    private String orgID;

    public Recruiter(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags) {
        super(name, id, phone, email, url, address, tags);
    }

    // TODO: Implement method to check if recruiter is linked to a certain organisation.
    public boolean hasOrganisation() {
        return true;
    }

    // TODO: Append orgID to string.
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .add("name", super.getName())
            .add("id", super.getId())
            .add("phone", super.getPhone())
            .add("email", super.getEmail())
            .add("url", super.getUrl())
            .add("address", super.getAddress())
            .add("tags", super.getTags())
            .toString();
    }
}
