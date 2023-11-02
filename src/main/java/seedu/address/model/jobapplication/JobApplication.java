package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;

/**
 * Represents a Job Application in the address book.
 */
public class JobApplication {

    public static final Comparator<JobApplication> STATUS_COMPARATOR = (a, b) ->
            a.status.compareTo(b.status);

    public static final Comparator<JobApplication> STAGE_COMPARATOR = (a, b) ->
            a.applicationStage.compareTo(b.applicationStage);

    public static final Comparator<JobApplication> DEADLINE_COMPARATOR = (a, b) ->
            a.deadline.compareTo(b.deadline);

    public static final Comparator<JobApplication> LAST_UPDATED_COMPARATOR = (a, b) ->
            a.lastUpdatedTime.compareTo(b.lastUpdatedTime);

    public static final Comparator<JobApplication> JOB_TITLE_COMPARATOR = Comparator.comparing(
            application -> application.getJobTitle().title, String.CASE_INSENSITIVE_ORDER);

    private final Id oid;
    private final Name orgName;

    private final JobTitle jobTitle;

    private final Optional<JobDescription> jobDescription;

    private final LastUpdatedTime lastUpdatedTime;

    private final Deadline deadline;

    private final JobStatus status;

    private final ApplicationStage applicationStage;

    /**
     * Constructs a job application.
     *
     * @param org that is being applied to
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param status of the application
     */
    public JobApplication(Organization org, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline, JobStatus status, ApplicationStage applicationStage) {
        this(org, jobTitle, jobDescription, deadline, status, applicationStage, new LastUpdatedTime());
    }

    /**
     * Constructs a job application with default status and application stage.
     *
     * @param org that is being applied to
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     */
    public JobApplication(Organization org, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline) {
        this(org, jobTitle, jobDescription, deadline, JobStatus.DEFAULT_STATUS, ApplicationStage.DEFAULT_STAGE);
    }

    /**
     * Constructs a job application with default status.
     *
     * @param org that is being applied to
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param applicationStage of the application.
     */
    public JobApplication(Organization org, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline, ApplicationStage applicationStage) {
        this(org, jobTitle, jobDescription, deadline, JobStatus.DEFAULT_STATUS, applicationStage);
    }

    /**
     * Constructs a job application (should be directly used by Jackson only)
     *
     * @param oid of the organization that is being applied to.
     * @param orgName of the organization that is being applied to.
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param status of the application
     * @param lastUpdatedTime of the application
     */
    public JobApplication(
            Id oid, Name orgName, JobTitle jobTitle, JobDescription jobDescription,
            Deadline deadline, JobStatus status, ApplicationStage applicationStage,
            LastUpdatedTime lastUpdatedTime) {
        requireNonNull(oid);
        requireNonNull(orgName);
        requireNonNull(jobTitle);
        this.oid = oid;
        this.orgName = orgName;
        this.jobTitle = jobTitle;
        this.jobDescription = Optional.ofNullable(jobDescription);

        this.deadline = deadline == null ? new Deadline() : deadline;
        this.status = status == null ? JobStatus.DEFAULT_STATUS : status;
        this.applicationStage = applicationStage == null ? ApplicationStage.DEFAULT_STAGE : applicationStage;
        this.lastUpdatedTime = lastUpdatedTime;
    }

    /**
     * Constructs a job application (should be directly used by Jackson only)
     *
     * @param org that is being applied to
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param status of the application
     * @param lastUpdatedTime of the application
     */
    public JobApplication(
            Organization org, JobTitle jobTitle, JobDescription jobDescription,
            Deadline deadline, JobStatus status, ApplicationStage applicationStage,
            LastUpdatedTime lastUpdatedTime) {
        this(org.getId(), org.getName(), jobTitle, jobDescription, deadline, status, applicationStage,
                lastUpdatedTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JobApplication)) {
            return false;
        }

        JobApplication otherApplication = (JobApplication) other;
        return oid.equals(otherApplication.oid)
                && jobTitle.equals(otherApplication.jobTitle)
                && jobDescription.equals(otherApplication.jobDescription)
                && deadline.equals(otherApplication.deadline)
                && lastUpdatedTime.equals(otherApplication.lastUpdatedTime)
                && status.equals(otherApplication.status)
                && applicationStage.equals(otherApplication.applicationStage);
    }

    public Id getOrganizationId() {
        return oid;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public Optional<JobDescription> getJobDescription() {
        return jobDescription;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public LastUpdatedTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public JobStatus getStatus() {
        return status;
    }

    public ApplicationStage getApplicationStage() {
        return applicationStage;
    }
    public Name getOrgName() {
        return orgName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                oid.toString(),
                jobTitle.toString(),
                jobDescription.map(JobDescription::toString).orElse("None"),
                deadline.toString(),
                lastUpdatedTime.toString(),
                status.toString(),
                applicationStage.toString()
        );
    }

    /**
     * Returns a builder for the {@link #toString} method of this class
     * @return an instance if {@code ToStringBuilder}
     */
    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder("")
                .add("title", jobTitle)
                .add("\nstage", applicationStage)
                .add("\nstatus", status)
                .add("\ndeadline", deadline)
                .add("\ndescription", jobDescription.map(JobDescription::toString).orElse("None"));
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
}
