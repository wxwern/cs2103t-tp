package seedu.address.model.jobapplication;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the various job application deadlines.
 * Such as deadlines for applications, deadlines for online assessments.
 */
public class Deadline implements Comparable<Deadline> {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should be in the format of DD-MM-YYYY";

    public static final String VALIDATION_REGEX = "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-\\d{4}$";

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d-M-y");
    public final LocalDate deadline;

    /**
     * Gives a deadline given a string deadline.
     * @param deadline in the appropriate format.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        this.deadline = LocalDate.parse(deadline, FORMATTER);
    }

    /**
     * Gives a deadline given a {@link LocalDate} instance.
     * @param deadline as a {@link LocalDate} instance.
     */
    public Deadline(LocalDate deadline) {
        requireNonNull(deadline);
        this.deadline = deadline;
    }

    /**
     * Gives a default deadline 14 days from now.
     */
    public Deadline() {
        this.deadline = LocalDate.now().plusDays(14);
    }

    /**
     * Checks if the given string is strictly a valid date.
     */
    public static boolean isValidDeadline(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        String[] arr = test.split("-");
        if (arr.length != 3) {
            return false;
        }

        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);

        return isValidDate(day, month, year);
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

    @Override
    public int compareTo(Deadline d) {
        return deadline.compareTo(d.deadline);
    }

    @Override
    public String toString() {
        return FORMATTER.format(deadline);
    }

    private static boolean isValidDate(int day, int month, int year) {

        List<Integer> monthsWithThirtyDays = Arrays.asList(4, 6, 9, 11);

        // these are completely invalid
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0) {
            return false;
        }

        // these are for months with 30 days
        if (monthsWithThirtyDays.contains(month)) {
            return day < 31;
        }

        // these are for months with 31 days
        if (month != 2) {
            return true;
        }

        // remaining here is february
        if (isLeapYear(year)) {
            return day < 30;
        }

        return day < 29;


    }

    private static boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }

        if (year % 100 == 0) {
            return false;
        }

        return year % 4 == 0;
    }

}
