package seedu.address.logic.parser;

import java.util.Objects;
import java.util.Optional;

/**
 * A flag is an argument in and of itself. It functions as a option specifier, or as a marker for the beginning of a
 * command argument.
 * E.g. '--t' in 'add James --t friend'.
 */
public class Flag {

    public static final String DEFAULT_PREFIX = "--";
    public static final String DEFAULT_POSTFIX = "";

    private final String name;
    private final String prefix;
    private final String postfix;

    /**
     * Constructs a flag with the {@link #DEFAULT_PREFIX} and {@link #DEFAULT_POSTFIX} surrounding the name.
     * If the name has any leading or trailing whitespace, it'll be trimmed.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     */
    public Flag(String name) {
        this(name, DEFAULT_PREFIX, DEFAULT_POSTFIX);
    }

    /**
     * Constructs a flag with a custom prefix and custom postfix.
     * Any fields with leading or trailing whitespace are trimmed.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     * @param prefix The prefix of the flag. May be null, which will set it to an empty string.
     * @param postfix The postfix of the flag. May be null, which will set it to an empty string.
     */
    public Flag(String name, String prefix, String postfix) {
        this.name = name == null ? "" : name.trim();
        this.prefix = prefix == null ? "" : prefix.trim();
        this.postfix = postfix == null ? "" : postfix.trim();
    }

    /**
     * Parses the given string using the default prefix and postfix format into a {@link Flag}.
     *
     * @param string The string to check for flag-like formats.
     * @return true if the string resembles a flag, false otherwise.
     * @throws IllegalArgumentException if the flag is invalid.
     */
    public static Flag parse(String string) {
        if (!isFlagSyntax(string)) {
            throw new IllegalArgumentException("Attempted to parse non-flag string as flag");
        }

        return new Flag(string.substring(
                DEFAULT_PREFIX.length(),
                string.length() - DEFAULT_POSTFIX.length()
        ));
    }

    /**
     * Finds a {@link Flag} from the given {@code flags} that matches the given string representation.
     *
     * @param string The string to check for a corresponding matching flag or flag-like formats.
     * @param flags The array of flags to check from.
     * @return An optional instance with the result if there is a successful match.
     * @throws IllegalArgumentException if the flag is invalid.
     */
    public static Optional<Flag> findMatch(String string, Flag[] flags) {
        for (Flag flag : flags) {
            if (string != null && string.equals(flag.getFlagString())) {
                return Optional.of(flag);
            }
        }
        return Optional.empty();
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
     * Checks whether the given string representation resembles a flag.
     * If this is true, then it resembles the default prefix-name-postfix format specified in {@link Flag},
     * and is a plausible output from {@link Flag#getFlagString()}.
     *
     * @param string The string to check for flag-like formats.
     * @return true if the string resembles a flag, false otherwise.
     */
    public static boolean isFlagSyntax(String string) {
        if (string == null) {
            return false;
        }
        return string.startsWith(DEFAULT_PREFIX) && string.endsWith(DEFAULT_POSTFIX);
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
