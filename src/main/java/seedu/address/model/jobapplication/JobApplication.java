package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.contact.Id;

/**
 * Represents a Job Application in the address book.
 */
public class JobApplication {
    private final Id oid;

    private final JobTitle jobTitle;

    private final Optional<JobDescription> jobDescription;

    private final LastUpdatedTime lastUpdatedTime = new LastUpdatedTime();

    private final Deadline deadline;

    private final JobStatus status;

    private final ApplicationStage applicationStage;

    /**
     * Constructs a job application.
     *
     * @param oid of the organization applied to.
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param status of the application
     */
    public JobApplication(Id oid, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline, JobStatus status, ApplicationStage applicationStage) {
        requireNonNull(oid);
        requireNonNull(jobTitle);
        requireNonNull(deadline);
        requireNonNull(status);
        requireNonNull(applicationStage);
        this.oid = oid;
        this.jobTitle = jobTitle;
        this.jobDescription = Optional.ofNullable(jobDescription);
        this.deadline = deadline;
        this.status = status;
        this.applicationStage = applicationStage;
    }

    /**
     * Constructs a job application with default status and application stage.
     *
     * @param oid of the organization applied to.
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     */
    public JobApplication(Id oid, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline) {
        this(oid, jobTitle, jobDescription, deadline, JobStatus.PENDING, ApplicationStage.RESUME);
    }

    /**
     * Constructs a job application with default status.
     *
     * @param oid of the organization applied to.
     * @param jobTitle of the postion applied to.
     * @param jobDescription of the positon applied to.
     * @param deadline of the application or interview if relevant.
     * @param applicationStage of the application.
     */
    public JobApplication(Id oid, JobTitle jobTitle, JobDescription jobDescription,
                          Deadline deadline, ApplicationStage applicationStage) {
        this(oid, jobTitle, jobDescription, deadline, JobStatus.PENDING, applicationStage);
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
}
