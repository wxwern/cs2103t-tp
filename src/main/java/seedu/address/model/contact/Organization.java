package seedu.address.model.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

    private final Optional<Status> status;
    private final Optional<Position> position;

    private final Set<Id> rids = new HashSet<>();

    private final List<JobApplication> jobApplications = new ArrayList<>();


    /**
     * Name and id fields must be non-null.
     * Tags must be non-null but can be empty as well.
     * The other fields can be null.
     */
    public Organization(
            Name name, Id id, Phone phone, Email email, Url url,
            Address address, Set<Tag> tags, Status status, Position position,
            Set<Id> rids
    ) {
        super(name, id, phone, email, url, address, tags);
        this.status = Optional.ofNullable(status);
        this.position = Optional.ofNullable(position);
        // Todo: Likely to deprecate rids completely
        // this.rids.addAll(rids);
    }

    @Override
    public Type getType() {
        return Type.ORGANIZATION;
    }

    public Optional<Status> getStatus() {
        return status;
    }

    public Optional<Position> getPosition() {
        return position;
    }

    /**
     * Returns a list of {@code JobApplication} made to this organization.
     */
    public JobApplication[] getJobApplications() {
        return this.jobApplications.toArray(new JobApplication[]{});
    }

    /**
     * Adds a {@code JobApplication} to the list of applications.
     */
    public void addJobApplication(JobApplication jobApplication) {
        this.jobApplications.add(jobApplication);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Id> getRecruiterIds() {
        return Collections.unmodifiableSet(rids);
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
                && getTags().equals(otherContact.getTags())
                && status.equals(otherContact.status)
                && position.equals(otherContact.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getId(), getType(), getName(), getPhone(), getEmail(), getAddress(), getTags(), status, position
        );
    }

    @Override
    public ToStringBuilder toStringBuilder() {
        return super.toStringBuilder()
                .add("status", status)
                .add("position", position);
    }

}
