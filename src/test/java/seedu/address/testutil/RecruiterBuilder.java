package seedu.address.testutil;

import static seedu.address.testutil.TypicalContacts.NUS;

import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Recruiter;

/**
 * A utility class to help with building Recruiter objects.
 */
public class RecruiterBuilder extends ContactBuilder {

    public static final Organization DEFAULT_ORGANIZATION = NUS;

    private Organization organization;

    /**
     * Creates a {@code RecruiterBuilder} with the default details.
     */
    public RecruiterBuilder() {
        super();
        this.organization = DEFAULT_ORGANIZATION;
    }

    /**
     * Initializes the RecruiterBuilder with the data of {@code recruiterToCopy}.
     */
    public RecruiterBuilder(Recruiter recruiterToCopy) {
        super(recruiterToCopy);
        this.organization = recruiterToCopy.getOrganization().orElse(null);
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
     * Sets the {@code organization} of the {@code Recruiter} that we are building.
     */
    public RecruiterBuilder withOrganization(Organization organization) {
        this.organization = organization;
        return this;
    }

    @Override
    public Recruiter build() {
        return new Recruiter(name, id, phone, email, url, address, tags, organization);
    }
}
