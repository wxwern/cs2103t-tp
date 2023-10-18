package seedu.address.testutil;

import java.util.Set;

import seedu.address.model.person.Contact;
import seedu.address.model.person.Id;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Position;
import seedu.address.model.person.Recruiter;
import seedu.address.model.tag.Tag;

public class OrganizationBuilder extends ContactBuilder {
    public static final String DEFAULT_OID = "111111";

    private Id oid;

    private Position position;
    private Set<Recruiter> recruiters;

    /**
     * Creates a {@code OrganizationBuilder} with the default details.
     */
    public OrganizationBuilder() {
        super();
        this.oid = new Id(DEFAULT_OID);
    }

    /**
     * Initializes the RecruiterBuilder with the data of {@code recruiterToCopy}.
     */
    public OrganizationBuilder(Organization organizationToCopy) {
        super(organizationToCopy);
    }

    /**
     * Sets the organization {@code Id} of the {@code Recruiter} that we are building.
     */
    public OrganizationBuilder withOid(String oid) {
        this.oid = new Id(oid);
        return this;
    }

    @Override
    public Organization build() {
        Organization contact = (Organization) super.build();
        return new Organization(
                contact.getName(),
                contact.getId(),
                contact.getPhone(),
                contact.getEmail(),
                contact.getUrl(),
                contact.getAddress(),
                contact.getTags(),
                contact.getStatus(),
                contact.getPosition()
        );
    }

}
