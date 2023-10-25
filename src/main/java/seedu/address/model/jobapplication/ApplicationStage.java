package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

/**
 * The different stages of internship application.
 */
public enum ApplicationStage {
    RESUME("resume"),
    ONLINE_ASSESSMENT("online assessment"),
    INTERVIEW("interview"),
    UNKNOWN("unknown");

    private static final ApplicationStage DEFAULT_STAGE = ApplicationStage.RESUME;
    private final String textRepresentation;

    ApplicationStage(String textRepresentation) {
        requireNonNull(textRepresentation);
        this.textRepresentation = textRepresentation;
    }

    /**
     * Returns the {@code ApplicationStage} enum as string representation. This is reversible, i.e., the string
     * representation here can be used to re-obtain the {@code ApplicationStage} enum by using {@link #fromString}.
     */
    @Override
    public String toString() {
        return this.textRepresentation;
    }

    /**
     * Returns a corresponding {@code ApplicationStage} enum value matching the given string representation of it.
     *
     * @param textRepresentation The text representation of the {@code ApplicationStage}.
     * @return The corresponding {@code JobStatus}.
     */
    public static ApplicationStage fromString(String textRepresentation) {
        for (ApplicationStage applicationStage : ApplicationStage.values()) {
            if (applicationStage.textRepresentation.equalsIgnoreCase(textRepresentation)) {
                return applicationStage;
            }
        }
        return UNKNOWN;
        // TODO: We should throw an exception instead. We are using UNKNOWN for now for compatibility.
    }
}
