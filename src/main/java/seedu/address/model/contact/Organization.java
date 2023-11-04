package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.tag.Tag;

/**
 * Represents an Organisation in the address book.
 * Guarantees: Guarantees: name and id are present and not null,
 * field values are immutable and if present, are validated.
 */
public class Organization extends Contact {
    // TODO: Override the getChildren method

    private final List<JobApplication> jobApplications = new ArrayList<>();

    /**
     * Name and id fields must be non-null.
     * Tags must be non-null but can be empty as well.
     * The other fields can be null.
     */
    public Organization(
            Name name, Id id, Phone phone, Email email, Url url,
            Address address, Set<Tag> tags, Status status, Position position
    ) {
        this(name, id, phone, email, url, address, tags, status, position, new ArrayList<>());
    }

    /**
     * Name and id fields must be non-null.
     * Tags must be non-null but can be empty as well.
     * List of applications must not be null.
     * The other fields can be null.
     */
    public Organization(
            Name name, Id id, Phone phone, Email email, Url url,
            Address address, Set<Tag> tags, Status status, Position position,
            List<JobApplication> jobApplications
    ) {
        super(name, id, phone, email, url, address, tags, null);
        this.jobApplications.addAll(jobApplications);
    }

    @Override
    public Type getType() {
        return Type.ORGANIZATION;
    }

    @Deprecated
    public Optional<Status> getStatus() {
        return Optional.empty(); // TODO: Remove entirely
    }

    @Deprecated
    public Optional<Position> getPosition() {
        return Optional.empty(); // TODO: Remove entirely
    }

    /**
     * Returns a list of {@code JobApplication} made to this organization.
     */
    public JobApplication[] getJobApplications() {
        return this.jobApplications.toArray(new JobApplication[]{});
    }

    /**
     * Checks if the organization has the given {@code JobApplication}.
     */
    public boolean hasJobApplication(JobApplication jobApplication) {
        return this.jobApplications.stream()
                .anyMatch(application -> application.isSameApplication(jobApplication));
    }

    /**
     * Adds a {@code JobApplication} to the list of applications.
     */
    public void addJobApplication(JobApplication jobApplication) {
        this.jobApplications.add(jobApplication);
    }

    /**
     * Replaces the old job application in the list with the new one.
     */
    public void replaceJobApplication(JobApplication oldApplication, JobApplication newApplication) {
        assert newApplication.getOrganizationId().equals(this.getId());
        assert newApplication.getOrganizationId().equals(oldApplication.getOrganizationId());

        this.jobApplications.remove(oldApplication);
        this.jobApplications.add(newApplication);
    }

    /**
     * Deletes the job application in the list.
     */
    public void deleteJobApplication(JobApplication application) {
        this.jobApplications.remove(application);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls implicitly
        if (!(other instanceof Organization)) {
            return false;
        }

        Organization otherContact = (Organization) other;
        return getId().equals(otherContact.getId())
                && getType().equals(otherContact.getType())
                && getName().equals(otherContact.getName())
                && getPhone().equals(otherContact.getPhone())
                && getEmail().equals(otherContact.getEmail())
                && getAddress().equals(otherContact.getAddress())
                && getUrl().equals(otherContact.getUrl())
                && getTags().equals(otherContact.getTags());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getType(), getName(), getPhone(), getEmail(), getAddress(), getTags()
        );
    }

    @Override
    public ToStringBuilder toStringBuilder() {
        return super.toStringBuilder();
    }

}
