package seedu.address.storage;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.LastUpdatedTime;


/**
 * Jackson-friendly version of {@link JobApplication}.
 */
public class JsonAdaptedApplication {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job Application's %s field is missing!";

    private final String oid;
    private final String orgName;
    private final String title;
    private final String description;
    private final String lastUpdatedTime;
    private final String deadline;
    private final String status;
    private final String stage;

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("oid") String oid,
                              @JsonProperty("orgName") String orgName,
                              @JsonProperty("title") String title,
                              @JsonProperty("description") String description,
                              @JsonProperty("lastUpdatedTime") String lastUpdatedTime,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("status") String status,
                              @JsonProperty("stage") String stage) {
        this.oid = oid;
        this.orgName = orgName;
        this.title = title;
        this.description = description;
        this.lastUpdatedTime = lastUpdatedTime;
        this.deadline = deadline;
        this.status = status;
        this.stage = stage;
    }

    /**
     * Converts a given {@code JobApplication} into this class for Jackson use.
     */
    public JsonAdaptedApplication(JobApplication source) {
        this.oid = source
                .getOrganizationId()
                .toString();
        this.title = source
                .getJobTitle()
                .toString();
        this.description = source
                .getJobDescription()
                .map(JobDescription::toString)
                .orElse(null);
        this.lastUpdatedTime = source
                .getLastUpdatedTime()
                .toString();
        this.deadline = source
                .getDeadline()
                .toString();
        this.status = source
                .getStatus()
                .toString();
        this.stage = source
                .getApplicationStage()
                .toString();
        this.orgName = source
                .getOrgName()
                .toString();
    }

    /**
     * Converts this Jackson-friendly adapted application object into the model's {@code JobApplication} object.
     *
     * @throws IllegalValueException if there are any data constraints violated in the adapted application.
     */
    public JobApplication toModelType() throws IllegalValueException {
        final Id oid;
        final Name orgName;
        final JobTitle title;
        final Optional<JobDescription> description;
        final Deadline deadline;
        final LastUpdatedTime lastUpdatedTime;
        final JobStatus status;
        final ApplicationStage stage;

        if (this.oid == null || !Id.isValidId(this.oid)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        oid = new Id(this.oid);
        if (this.title == null || !JobTitle.isValidJobTitle(this.title)) {
            throw new IllegalValueException(JobTitle.MESSAGE_CONSTRAINTS);
        }
        title = new JobTitle(this.title);
        if (this.description != null && !JobDescription.isValidJobDescription(this.description)) {
            throw new IllegalValueException(JobDescription.MESSAGE_CONSTRAINTS);
        }
        description = Optional.<String>ofNullable(this.description).map(JobDescription::new);
        if (this.deadline == null || !Deadline.isValidDeadline(this.deadline)) {
            throw new IllegalValueException(Deadline.MESSAGE_CONSTRAINTS);
        }
        deadline = new Deadline(this.deadline);
        lastUpdatedTime = LastUpdatedTime.generateLastUpdatedTime(this.lastUpdatedTime);
        if (this.status == null || !JobStatus.isValidJobStatus(this.status)) {
            throw new IllegalValueException(JobStatus.MESSAGE_CONSTRAINTS);
        }
        status = JobStatus.fromString(this.status);
        if (this.stage == null || !ApplicationStage.isValidApplicationStage(this.stage)) {
            throw new IllegalValueException(ApplicationStage.MESSAGE_CONSTRAINTS);
        }
        stage = ApplicationStage.fromString(this.stage);
        if (this.orgName == null || !Name.isValidName(this.orgName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        orgName = new Name(this.orgName);

        return new JobApplication(
                oid,
                orgName,
                title,
                description.orElse(null),
                deadline,
                status,
                stage,
                lastUpdatedTime
        );
    }
}
