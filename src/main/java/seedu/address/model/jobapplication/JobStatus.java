package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

/**
 * Information on the status of the job application: pending, rejected, offered, accepted, turned-down
 */
public enum JobStatus {
    PENDING("pending"),
    REJECTED("rejected"),
    OFFERED("offered"),
    ACCEPTED("accepted"),
    TURNED_DOWN("turned down"),
    UNKNOWN("unknown");

    public static final JobStatus DEFAULT_STATUS = JobStatus.PENDING;

    private final String textRepresentation;

    JobStatus(String textRepresentation) {
        requireNonNull(textRepresentation);
        this.textRepresentation = textRepresentation;
    }

    /**
     * Returns the {@code JobStatus} enum as string representation. This is reversible, i.e., the string
     * representation here can be used to re-obtain the {@code JobStatus} enum by using {@link #fromString}.
     */
    @Override
    public String toString() {
        return this.textRepresentation;
    }

    /**
     * Returns a corresponding {@code JobStatus} enum value matching the given string representation of it.
     *
     * @param textRepresentation The text representation of the {@code JobStatus}.
     * @return The corresponding {@code JobStatus}.
     */
    public static JobStatus fromString(String textRepresentation) {
        for (JobStatus jobStatus : JobStatus.values()) {
            if (jobStatus.textRepresentation.equalsIgnoreCase(textRepresentation)) {
                return jobStatus;
            }
        }
        return UNKNOWN;
        // TODO: We should throw an exception instead. We are using UNKNOWN for now for compatibility.
    }
}
