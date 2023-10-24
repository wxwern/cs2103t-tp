package seedu.address.model.jobapplication;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents the various job application deadlines.
 * Such as deadlines for applications, deadlines for online assessments.
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "";

    public static final String VALIDATION_REGEX = "";
    public final LocalDate deadline;

    /**
     * Gives a deadline given a string deadline.
     * @param deadline in the appropriate format.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        this.deadline = LocalDate.parse(deadline);
    }

    /**
     * Gives a default deadline 14 days from now.
     */
    public Deadline() {
        this.deadline = LocalDate.now().plusDays(14);
    }

    public static boolean isValidDeadline(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return deadline.equals(otherDeadline.deadline);
    }
}
