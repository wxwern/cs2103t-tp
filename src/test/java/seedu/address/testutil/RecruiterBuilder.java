package seedu.address.testutil;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Recruiter;

/**
 * A utility class to help with building Recruiter objects.
 */
public class RecruiterBuilder extends ContactBuilder {

    public static final String DEFAULT_OID = "111111";

    private Id oid;

    /**
     * Creates a {@code RecruiterBuilder} with the default details.
     */
    public RecruiterBuilder() {
        super();
        this.oid = new Id(DEFAULT_OID);
    }

    /**
     * Initializes the RecruiterBuilder with the data of {@code recruiterToCopy}.
     */
    public RecruiterBuilder(Recruiter recruiterToCopy) {
        super(recruiterToCopy);
        this.oid = recruiterToCopy.getOrganizationId().orElse(null);
    }

    @Override
    public RecruiterBuilder withName(String name) {
        return (RecruiterBuilder) super.withName(name);
    }

    @Override
    public RecruiterBuilder withId(String id) {
        return (RecruiterBuilder) super.withId(id);
    }

    @Override
    public RecruiterBuilder withPhone(String phone) {
        return (RecruiterBuilder) super.withPhone(phone);
    }

    @Override
    public RecruiterBuilder withEmail(String email) {
        return (RecruiterBuilder) super.withEmail(email);
    }

    @Override
    public RecruiterBuilder withUrl(String url) {
        return (RecruiterBuilder) super.withUrl(url);
    }

    @Override
    public RecruiterBuilder withAddress(String address) {
        return (RecruiterBuilder) super.withAddress(address);
    }

    @Override
    public RecruiterBuilder withTags(String... tags) {
        return (RecruiterBuilder) super.withTags(tags);
    }

    /**
     * Sets the organization {@code Id} of the {@code Recruiter} that we are building.
     */
    public RecruiterBuilder withOid(String oid) {
        this.oid = oid == null ? null : new Id(oid);
        return this;
    }

    @Override
    public Recruiter build() {
        Contact contact = super.build();
        return new Recruiter(
                contact.getName(),
                contact.getId(),
                contact.getPhone().orElse(null),
                contact.getEmail().orElse(null),
                contact.getUrl().orElse(null),
                contact.getAddress().orElse(null),
                contact.getTags(),
                oid
        );
    }
}
