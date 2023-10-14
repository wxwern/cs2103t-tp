package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Organisation in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Organization extends Contact {

    private final Status status;
    //private final Position position;
    //private final Url url;

    // Data fields
    //private final Set<Recruiter> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Organization(Name name, Phone phone, Email email, Address address,
                        Set<Tag> tags, Status status) {
        super(name, phone, email, address, tags);
        requireAllNonNull(status);
        this.status = status;
        //this.position = position;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public Boolean isOrganization() {
        return true;
    }

    //    public Position getPosition() {
    //        return position;
    //    }

    //    public Address getUrl() {
    //        return url;
    //    }

    //    public Set<Recruiter> getTags() {
    //        return Collections.unmodifiableSet(recruiters);
    //    }

    /**
     * Returns true if both contacts have the same name.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameOrganization(Organization otherOrganization) {
        if (otherOrganization == this) {
            return true;
        }

        return otherOrganization != null
                && otherOrganization.getName().equals(getName());
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    //    @Override
    //    public boolean equals(Object other) {
    //        if (other == this) {
    //            return true;
    //        }
    //
    //        // instanceof handles nulls
    //        if (!(other instanceof Contact)) {
    //            return false;
    //        }
    //
    //        Contact otherContact = (Contact) other;
    //        return name.equals(otherContact.name)
    //                && phone.equals(otherContact.phone)
    //                && email.equals(otherContact.email)
    //                && address.equals(otherContact.address)
    //                && tags.equals(otherContact.tags);
    //    }

    //    @Override
    //    public int hashCode() {
    //        // use this method for custom fields hashing instead of implementing your own
    //        return Objects.hash(name, phone, email, address, tags);
    //    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", super.getName())
                .add("phone", super.getPhone())
                .add("email", super.getEmail())
                .add("address", super.getAddress())
                .add("tags", super.getTags())
                .add("status", status)
                .toString();
    }

}
