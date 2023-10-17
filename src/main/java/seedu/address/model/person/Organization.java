package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents an Organisation in the address book. Guarantees: details are present and not null, field values are
 * validated, immutable.
 */
public class Organization extends Contact {
    // TODO: Override the getChildren method

    private final Status status;
    private final Position position;

    /**
     * Every field must be present and not null.
     */
    public Organization(
            Name name, Id id, Phone phone, Email email, Url url,
            Address address, Set<Tag> tags, Status status, Position position
    ) {
        super(name, id, phone, email, url, address, tags);
        requireAllNonNull(status, position);
        this.status = status;
        this.position = position;
    }

    @Override
    public Type getType() {
        return Type.ORGANIZATION;
    }

    public Status getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls implicitly
        if (!(other instanceof Organization)) {
            return false;
        }

        Organization otherContact = (Organization) other;
        return getId().equals(otherContact.getId())
                && getType().equals(otherContact.getType())
                && getName().equals(otherContact.getName())
                && getPhone().equals(otherContact.getPhone())
                && getEmail().equals(otherContact.getEmail())
                && getAddress().equals(otherContact.getAddress())
                && getUrl().equals(otherContact.getUrl())
                && getTags().equals(otherContact.getTags())
                && status.equals(otherContact.status)
                && position.equals(otherContact.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getType(), getName(), getPhone(), getEmail(), getAddress(), getTags(), status, position
        );
    }

    @Override
    public ToStringBuilder toStringBuilder() {
        return super.toStringBuilder()
                .add("status", status)
                .add("position", position);
    }

}
