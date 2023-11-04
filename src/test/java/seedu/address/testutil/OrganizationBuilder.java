package seedu.address.testutil;

import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Organization;

/**
 * A utility class to help with building Organization objects.
 */
public class OrganizationBuilder extends ContactBuilder {

    /**
     * Creates a {@code OrganizationBuilder} with the default details.
     */
    public OrganizationBuilder() {
        super();
    }

    /**
     * Initializes the OrganizationBuilder with the data of {@code organizationToCopy}.
     */
    public OrganizationBuilder(Organization organizationToCopy) {
        super(organizationToCopy);
    }

    @Override
    public OrganizationBuilder withId(String id) {
        return (OrganizationBuilder) super.withId(id);
    }

    @Override
    public OrganizationBuilder withAddress(String address) {
        return (OrganizationBuilder) super.withAddress(address);
    }

    @Override
    public OrganizationBuilder withEmail(String email) {
        return (OrganizationBuilder) super.withEmail(email);
    }

    @Override
    public OrganizationBuilder withName(String name) {
        return (OrganizationBuilder) super.withName(name);
    }

    @Override
    public OrganizationBuilder withPhone(String phone) {
        return (OrganizationBuilder) super.withPhone(phone);
    }

    @Override
    public OrganizationBuilder withTags(String... tags) {
        return (OrganizationBuilder) super.withTags(tags);
    }

    @Override
    public OrganizationBuilder withUrl(String url) {
        return (OrganizationBuilder) super.withUrl(url);
    }

    @Override
    public Organization build() {
        Contact contact = super.build();
        return new Organization(
                contact.getName(),
                contact.getId(),
                contact.getPhone().orElse(null),
                contact.getEmail().orElse(null),
                contact.getUrl().orElse(null),
                contact.getAddress().orElse(null),
                contact.getTags()
        );
    }

}
