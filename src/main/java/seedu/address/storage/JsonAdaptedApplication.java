package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.jobapplication.JobApplication;

/**
 * Jackson-friendly version of {@link JobApplication}.
 */
public class JsonAdaptedApplication {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Job Application's %s field is missing!";

    private final String oid;
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
                              @JsonProperty("title") String title,
                              @JsonProperty("description") String description,
                              @JsonProperty("lastUpdatedTime") String lastUpdatedTime,
                              @JsonProperty("deadline") String deadline,
                              @JsonProperty("status") String status,
                              @JsonProperty("stage") String stage) {
        this.oid = oid;
        this.title = title;
        this.description = description;
        this.lastUpdatedTime = lastUpdatedTime;
        this.deadline = deadline;
        this.status = status;
        this.stage = stage;
    }
}
