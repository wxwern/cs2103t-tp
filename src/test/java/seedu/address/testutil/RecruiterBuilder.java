package seedu.address.testutil;

import seedu.address.model.person.Contact;
import seedu.address.model.person.Recruiter;

/**
 * A utility class to help with building Recruiter objects.
 */
public class RecruiterBuilder extends ContactBuilder {

    public static final String DEFAULT_ORG_ID = "111111";

    private String orgID;

    /**
     * Creates a {@code RecruiterBuilder} with the default details.
     */
    public RecruiterBuilder() {
        super();
        this.orgID = DEFAULT_ORG_ID;
    }

    /**
     * Initializes the RecruiterBuilder with the data of {@code recruiterToCopy}.
     */
    public RecruiterBuilder(Recruiter recruiterToCopy) {
        super(recruiterToCopy);
    }

    /**
     * Sets the {@code ID} of the {@code Contact} that we are building.
     */
    public RecruiterBuilder withOrgID(String orgID) {
        this.orgID = orgID;
        return this;
    }

    @Override
    public Recruiter build() {
        Contact contact = super.build();
        return new Recruiter(
            contact.getName(),
            contact.getPhone(),
            contact.getEmail(),
            contact.getAddress(),
            contact.getTags()
        );
    }

}
