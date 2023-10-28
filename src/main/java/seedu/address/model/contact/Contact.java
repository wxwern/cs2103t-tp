package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: name and id are present and not null, field values are immutable and if present, are validated.
 */
public class Contact {

    private static final String ILLEGAL_OPERATION_MESSAGE = "Contact cannot have child contacts";

    // Identity fields
    private final Name name;
    private final Id id;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Url> url;
    private final Optional<Address> address;
    private final Set<Tag> tags = new HashSet<>();

    private Optional<Contact> parent;

    /**
     * Name and id fields must be non-null.
     * Tags must be non-null but can be empty as well.
     * The other fields can be null.
     */
    public Contact(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags,
                   Contact parent) {
        requireAllNonNull(name, id, tags);
        this.name = name;
        this.id = id;
        this.phone = Optional.ofNullable(phone);
        this.email = Optional.ofNullable(email);
        this.url = Optional.ofNullable(url);
        this.address = Optional.ofNullable(address);
        this.tags.addAll(tags);
        this.parent = Optional.ofNullable(parent);
    }

    public Type getType() {
        // TODO: This should be an abstract method.
        return Type.UNKNOWN;
    }

    public Name getName() {
        return name;
    }

    public Id getId() {
        return id;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public Optional<Address> getAddress() {
        return address;
    }

    public Optional<Url> getUrl() {
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
     * Returns true if both contacts have the same id.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getId().equals(getId());
    }

    /**
     * Gives the parent of this contact.
     */
    public Optional<Contact> getParent() {
        return parent;
    }

    /**
     * Gives the array of contacts that are linked under this contact.
     */
    public Contact[] getChildren() {
        // default return value
        // TODO add to DG
        return new Contact[]{};
    }

    /**
     * Adds a child contact under this contact.
     * @throws IllegalOperationException if this contact cannot accept child contacts
     */
    public void addChild(Contact childContact) throws IllegalOperationException {
        // Should throw exception if the type of contact cannot have child contacts.
        // TODO add to DG, do JavaDocs
        throw new IllegalOperationException(ILLEGAL_OPERATION_MESSAGE);
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
