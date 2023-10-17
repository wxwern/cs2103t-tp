package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Recruiter in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Recruiter extends Contact {

    private Id oid;

    /**
     * Every field except oid must be present and not null.
     */
    public Recruiter(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags, Id oid) {
        super(name, id, phone, email, url, address, tags);
        this.oid = oid;
    }

    public Id getOid() {
        return oid;
    }

    @Override
    public Type getType() {
        return Type.RECRUITER;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
