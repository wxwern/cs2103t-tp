package seedu.address.testutil;

import seedu.address.model.contact.Organization;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobTitle;

/**
 * The typical list of job applications.
 */
public class TypicalJobApplications {

    private static Organization dummyOrg = new OrganizationBuilder().withName("Dummy Org").build();

    public static final JobApplication[] JOB_APPLICATIONS = new JobApplication[] {
        new JobApplication(dummyOrg, new JobTitle("SWE"), null, new Deadline()),
        new JobApplication(dummyOrg, new JobTitle("SRE"), null, new Deadline()),
        new JobApplication(dummyOrg, new JobTitle("SSWE"), null, new Deadline()),
        new JobApplication(dummyOrg, new JobTitle("Level 3 Engineer"), null, new Deadline())
    };
}
