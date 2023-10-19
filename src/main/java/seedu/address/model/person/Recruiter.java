package seedu.address.model.person;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Recruiter in the address book.
 * Guarantees: Guarantees: name and id are present and not null, field values are immutable and if present, are validated.
 */
public class Recruiter extends Contact {

    private final Optional<Id> oid;

    /**
     * Every field except oid must be present and not null.
     */
    public Recruiter(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags, Id oid) {
        super(name, id, phone, email, url, address, tags);
        this.oid = Optional.ofNullable(oid);
    }

    public Optional<Id> getOrganizationId() {
        return oid;
    }

    @Override
    public Type getType() {
        return Type.RECRUITER;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls implicitly
        if (!(other instanceof Recruiter)) {
            return false;
        }

        Recruiter otherContact = (Recruiter) other;
        return getId().equals(otherContact.getId())
                && getType().equals(otherContact.getType())
                && getName().equals(otherContact.getName())
                && getPhone().equals(otherContact.getPhone())
                && getEmail().equals(otherContact.getEmail())
                && getAddress().equals(otherContact.getAddress())
                && getUrl().equals(otherContact.getUrl())
                && getTags().equals(otherContact.getTags())
                && oid.equals(otherContact.oid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getType(), getName(), getPhone(), getEmail(), getAddress(), getTags(), oid
        );
    }

    @Override
    public ToStringBuilder toStringBuilder() {
        return super.toStringBuilder()
                .add("oid", oid);
    }
}
