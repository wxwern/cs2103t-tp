package seedu.address.logic.parser;

import java.util.Objects;

/**
 * A flag is an argument in and of itself. It functions as a option specifier, or as a marker for the beginning of an
 * command argument.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Flag {

    public static final String DEFAULT_PREFIX = "";
    public static final String DEFAULT_POSTFIX = "/";

    private final String name;
    private final String prefix;
    private final String postfix;

    /**
     * Constructs a flag with the {@link #DEFAULT_PREFIX} and {@link #DEFAULT_POSTFIX} surrounding the name.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     */
    public Flag(String name) {
        this(name, DEFAULT_PREFIX, DEFAULT_POSTFIX);
    }

    /**
     * Constructs a flag with a custom prefix and custom postfix.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     * @param prefix The prefix of the flag. May be null, which will set it to an empty string.
     * @param postfix The postfix of the flag. May be null, which will set it to an empty string.
     */
    public Flag(String name, String prefix, String postfix) {
        this.name = name == null ? "" : name;
        this.prefix = prefix == null ? "" : prefix;
        this.postfix = postfix == null ? "" : postfix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPostfix() {
        return postfix;
    }


    /**
     * Returns the full string that would be used by the user to input a flag.
     * Equivalent to calling {@link #toString()}.
     */
    public String getFlagString() {
        return this.toString();
    }

    /**
     * Returns a string representation of this flag.
     *
     * <p>
     * This is the full string that would be used by a user to input a flag.
     * This means it's the concatenated result of prefix, name, postfix together.
     * </p>
     *
     * @return The string representation of this flag.
     */
    @Override
    public String toString() {
        return this.getPrefix() + this.getName() + this.getPostfix();
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Flag)) {
            return false;
        }

        Flag otherFlag = (Flag) other;
        return Objects.equals(name, otherFlag.name)
                && Objects.equals(prefix, otherFlag.prefix)
                && Objects.equals(postfix, otherFlag.postfix);
    }
}
