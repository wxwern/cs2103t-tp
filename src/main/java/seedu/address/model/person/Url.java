package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Contact's url in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidUrl(String)}
 */
public class Url {

    public static final String MESSAGE_CONSTRAINTS =
            "Url should at least contain a dot, and it should not be blank";

    /**
     * The first character of the url must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = ".+\\..+";

    public final String value;

    /**
     * Constructs a {@code Url}.
     *
     * @param url A valid url.
     */
    public Url(String url) {
        requireNonNull(url);
        checkArgument(isValidUrl(url), MESSAGE_CONSTRAINTS);
        value = url;
    }

    /**
     * Constructs an empty {@code Url}.
     */
    public Url() {
        value = "";

    }

    /**
     * Returns true if a given string is a valid url.
     */
    public static boolean isValidUrl(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Url)) {
            return false;
        }

        Url otherName = (Url) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
