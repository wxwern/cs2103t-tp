package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.EnumUtil;

/**
 * The different stages of internship application.
 */
public enum ApplicationStage {
    RESUME("resume"),
    ONLINE_ASSESSMENT("online assessment"),
    INTERVIEW("interview");

    public static final ApplicationStage DEFAULT_STAGE = ApplicationStage.RESUME;
    public static final String MESSAGE_CONSTRAINTS = "Applications accept one of these values: resume | online "
            + "assessment | interview";
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
     * Returns a corresponding {@code ApplicationStage} enum value matching the given text representation of it.
     *
     * @param textRepresentation The text representation of the {@code ApplicationStage}.
     * @return The corresponding {@code JobStatus}.
     * @throws IllegalArgumentException if the text representation does not match any known values.
     */
    public static ApplicationStage fromString(String textRepresentation) {
        return EnumUtil.lookupByToString(ApplicationStage.class, textRepresentation);
    }

    /**
     * Verifies if the given input is a valid job application stage.
     *
     * @param textRepresentation The text representation of the {@code ApplicationStage}.
     * @return Whether the application stage matches a known value.
     */
    public static boolean isValidApplicationStage(String textRepresentation) {
        return EnumUtil.hasMatchingToString(ApplicationStage.class, textRepresentation);
    }
}
