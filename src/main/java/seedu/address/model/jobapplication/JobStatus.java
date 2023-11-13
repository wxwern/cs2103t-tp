package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.EnumUtil;

/**
 * Information on the status of the job application: pending, rejected, offered, accepted, turned-down
 */
public enum JobStatus {
    PENDING("pending"),
    REJECTED("rejected"),
    OFFERED("offered"),
    ACCEPTED("accepted"),
    TURNED_DOWN("turned down");

    public static final JobStatus DEFAULT_STATUS = JobStatus.PENDING;

    public static final String MESSAGE_CONSTRAINTS = "Job status are one of the values: pending | rejected | offered "
            + "| accepted | turned down";

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
     * Returns a corresponding {@code JobStatus} enum value matching the given text representation of it.
     *
     * @param textRepresentation The text representation of the {@code JobStatus}.
     * @return The corresponding {@code JobStatus}.
     * @throws IllegalArgumentException if the text representation does not match any known values.
     */
    public static JobStatus fromString(String textRepresentation) {
        return EnumUtil.lookupByToString(JobStatus.class, textRepresentation);
    }

    /**
     * Verifies if the given input is a valid job status.
     *
     * @param textRepresentation The text representation of the {@code JobStatus}.
     * @return Whether the job status matches a known value.
     */
    public static boolean isValidJobStatus(String textRepresentation) {
        return EnumUtil.hasMatchingToString(JobStatus.class, textRepresentation);
    }
}
