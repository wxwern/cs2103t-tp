package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Contact;
import seedu.address.model.person.Id;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Organization objects.
 */
public class OrganizationBuilder extends ContactBuilder {
    public static final String DEFAULT_STATUS = "Applied";
    public static final String DEFAULT_POSITION = "Manager";
    private Position position;
    private Set<Id> rids;
    private Status status;


    /**
     * Creates a {@code OrganizationBuilder} with the default details.
     */
    public OrganizationBuilder() {
        super();
        position = new Position(DEFAULT_POSITION);
        status = new Status(DEFAULT_STATUS);
        rids = new HashSet<>();
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

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public OrganizationBuilder withRids(String ... rids) {
        this.rids = SampleDataUtil.getIdSet(rids);
        return this;
    }


    /**
     * Sets the {@code Status} of the {@code Contact} that we are building.
     */
    public OrganizationBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Contact} that we are building.
     */
    public OrganizationBuilder withPosition(String position) {
        this.status = new Status(position);
        return this;
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
                contact.getTags(),
                status,
                position,
                rids
        );
    }

}
