package seedu.address.model.jobapplication;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * The title of the job that is applied to.
 */
public class JobTitle {
    public static final String MESSAGE_CONSTRAINTS =
            "Job title should only contain alphanumeric characters, underscores and dashes, and it should not be blank";

    /**
     * The first character of the id must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[a-zA-Z0-9]([_\\-]?[a-zA-Z0-9])*";

    public final String title;

    /**
     * Constructs a {@code JobTitle}
     *
     * @params title a valid title.
     */
    public JobTitle(String title) {
        requireNonNull(title);
        checkArgument(isValidJobTitle(title), MESSAGE_CONSTRAINTS);
        this.title = title;
    }

    public static boolean isValidJobTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.title;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof JobTitle)) {
            return false;
        }

        JobTitle otherTitle = (JobTitle) other;
        return title.equals(otherTitle.title);
    }
}
