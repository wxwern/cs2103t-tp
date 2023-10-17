package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Name name;
    private final Id id;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Url url;
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Contact(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Type getType() {
        // TODO: This should be an abstract method.
        return Type.UNKNOWN;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Id getId() {
        return id;
    }

    public Url getUrl() {
        return url;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both contacts have the same name.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getName().equals(getName());
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        // TODO: This should be an abstract method.

        if (other == this) {
            return true;
        }

        // instanceof handles nulls implicitly
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        if (this.getType() != otherContact.getType()) {
            return false;
        }

        if (this.getType() == Type.UNKNOWN) {
            return id.equals(otherContact.id)
                    && name.equals(otherContact.name)
                    && phone.equals(otherContact.phone)
                    && email.equals(otherContact.email)
                    && address.equals(otherContact.address)
                    && url.equals(otherContact.url)
                    && tags.equals(otherContact.tags);
        }

        throw new IllegalStateException("The equality comparison should be overriden by a subclass.");
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getType(), name, phone, email, url, address, tags);
    }

    /**
     * Returns a builder for the {@link #toString} method of this class using {@code ToStringBuilder}.
     * This can be overriden by subclasses to add properties to the builder.
     *
     * @return An instance of {@code ToStringBuilder} capable of crafting a string representation of this instance.
     */
    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("type", getType())
                .add("id", id)
                .add("phone", phone)
                .add("email", email)
                .add("url", url)
                .add("address", address)
                .add("tags", tags);
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }

}
