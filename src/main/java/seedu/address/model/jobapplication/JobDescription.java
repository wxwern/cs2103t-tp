package seedu.address.model.jobapplication;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.contact.Id;

/**
 * Describes the functions of the job to be applied to.
 */
public class JobDescription {
    public static final String MESSAGE_CONSTRAINTS =
            "Job description should only contain alphanumeric characters, underscores and dashes";

    /**
     * The first character of the id must not be a whitespace, otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z0-9]([_\\-]?[a-zA-Z0-9])*";

    public final String description;

    /**
     * Constructs a {@code JobDescription}.
     *
     * @param description A valid description.
     */
    public JobDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidJobDescription(description), MESSAGE_CONSTRAINTS);
        this.description = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidJobDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.description;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobDescription)) {
            return false;
        }

        JobDescription otherDescription = (JobDescription) other;
        return description.equals(otherDescription.description);
    }

}
