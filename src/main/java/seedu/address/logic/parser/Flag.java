package seedu.address.logic.parser;

import java.util.Objects;
import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A flag is an argument in and of itself. It functions as a option specifier, or as a marker for the beginning of a
 * command argument.
 * E.g. '--t' in 'add James --t friend'.
 */
public class Flag {

    public static final String DEFAULT_PREFIX = "--";
    public static final String DEFAULT_POSTFIX = "";

    public static final String DEFAULT_ALIAS_PREFIX = "-";
    public static final String DEFAULT_ALIAS_POSTFIX = "";

    private static final String FULL_OR_ALIAS_NAME_VALIDATION_REGEX = "[a-zA-Z][a-zA-Z0-9]*";
    private static final String FULL_OR_ALIAS_NAME_FORMAT_ERROR =
            "Flags must only have alphanumeric characters (a-z, A-Z, 0-9), "
                    + "may not start with a number, and may not be empty.";

    private final String name;
    private final String prefix;
    private final String postfix;

    private final String alias;
    private final String aliasPrefix;
    private final String aliasPostfix;

    /**
     * Constructs a flag with the {@link #DEFAULT_PREFIX} and {@link #DEFAULT_POSTFIX} surrounding the name.
     * No alias will be assigned to this flag. If the name has any leading or trailing whitespace, it'll be trimmed.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     */
    public Flag(String name) {
        this(
                name, DEFAULT_PREFIX, DEFAULT_POSTFIX,
                null, null, null
        );
    }

    /**
     * Constructs a flag with a custom alias.
     * Any fields with leading or trailing whitespace are trimmed.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     * @param alias The prefix of the flag. May be null, which will behave as if no alias is set.
     */
    public Flag(String name, String alias) {
        this(
                name,
                DEFAULT_PREFIX,
                DEFAULT_POSTFIX,
                alias,
                alias == null ? null : DEFAULT_ALIAS_PREFIX,
                alias == null ? null : DEFAULT_ALIAS_POSTFIX
        );
    }

    /**
     * Constructs a flag with the given properties for the name and alias.
     */
    private Flag(String name, String prefix, String postfix, String alias, String aliasPrefix, String aliasPostfix) {
        this.name = name == null ? "" : name.trim();
        this.prefix = prefix == null ? "" : prefix.trim();
        this.postfix = postfix == null ? "" : postfix.trim();

        boolean isAliasAvailable = alias != null && !alias.isBlank();

        if (isAliasAvailable) {
            this.alias = alias.trim();
            this.aliasPrefix = aliasPrefix == null ? this.prefix : aliasPrefix.trim();
            this.aliasPostfix = aliasPostfix == null ? this.postfix : aliasPostfix.trim();
        } else {
            this.alias = this.name;
            this.aliasPrefix = this.prefix;
            this.aliasPostfix = this.postfix;
        }

        if (!this.name.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX)
            || !this.alias.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX)) {

            throw new IllegalArgumentException(FULL_OR_ALIAS_NAME_FORMAT_ERROR);
        }
    }

    /**
     * Constructs a flag with a custom name, custom prefix and custom postfix.
     * Any fields with leading or trailing whitespace are trimmed.
     *
     * @param name The name of the flag. May be null, which will set it to an empty string.
     * @param prefix The prefix of the flag. May be null, which will set it to an empty string.
     * @param postfix The postfix of the flag. May be null, which will set it to an empty string.
     */
    public static Flag ofCustomFormat(String name, String prefix, String postfix) {
        return new Flag(name, prefix, postfix, null, null, null);
    }

    /**
     * Parses the given string using the default prefix and postfix format into a {@link Flag}.
     * This will work for both full flag strings and flag aliases.
     *
     * @param string The string to parse as a flag.
     * @return The corresponding {@link Flag} instance.
     * @throws ParseException if the flag syntax is invalid.
     */
    public static Flag parse(String string) throws ParseException {

        if (string != null && string.startsWith(DEFAULT_PREFIX) && string.endsWith(DEFAULT_POSTFIX)) {
            String flag = string.substring(
                    DEFAULT_PREFIX.length(),
                    string.length() - DEFAULT_POSTFIX.length()
            );
            if (flag.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX)) {
                return new Flag(flag);
            }
        }

        if (string != null && string.startsWith(DEFAULT_ALIAS_PREFIX) && string.endsWith(DEFAULT_ALIAS_POSTFIX)) {
            String alias = string.substring(
                    DEFAULT_ALIAS_PREFIX.length(),
                    string.length() - DEFAULT_ALIAS_POSTFIX.length()
            );
            if (alias.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX)) {
                return new Flag(alias, alias); // Treat alias as name, since we don't know any better.
            }
        }

        throw new ParseException(
                Messages.getErrorMessageForInvalidFlagString(string)
        );
    }

    /**
     * Parses the given string using the default prefix and postfix format into an optional {@link Flag},
     * which will return an empty optional if it's invalid.
     *
     * @param string The string to check for flag-like formats.
     * @return An optional containing the flag if it is a valid flag format.
     */
    public static Optional<Flag> parseOptional(String string) {
        try {
            return Optional.of(parse(string));
        } catch (ParseException e) {
            return Optional.empty();
        }
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
            if (string == null) {
                break;
            }
            if (string.equals(flag.getFlagString())
                    || string.equals(flag.getFlagAliasString())) {
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

    public String getAlias() {
        return alias;
    }

    public String getAliasPrefix() {
        return aliasPrefix;
    }

    public String getAliasPostfix() {
        return aliasPostfix;
    }

    /**
     * Returns the full string that would be used by the user to input a flag.
     *
     * <p>
     * This is the full string that would be used by a user to input a flag.
     * This means it's the concatenated result of prefix, name, postfix together.
     * </p>
     */
    public String getFlagString() {
        return this.getPrefix() + this.getName() + this.getPostfix();
    }

    /**
     * Returns the alias string that would be used by the user to input a flag.
     * It may be the same as {@link #getFlagString()} if there's no alias assigned to this flag.
     *
     * <p>
     * This is the full alias string that would be used by a user to input a flag.
     * This means it's the concatenated result of alias prefix, alias, alias postfix together.
     * </p>
     */
    public String getFlagAliasString() {
        return this.getAliasPrefix() + this.getAlias() + this.getAliasPostfix();
    }

    /**
     * Returns whether the flag has a distinct alias from its full string form.
     */
    public boolean hasAlias() {
        return !this.getFlagString().equals(this.getFlagAliasString());
    }

    /**
     * Checks whether the given string representation resembles a flag.
     * If this is true, then it resembles the default prefix-name-postfix format specified in {@link Flag},
     * or resembles the equivalent format for the alias counterpart, and thus a plausible output from
     * {@link Flag#getFlagString()} or {@link Flag#getFlagAliasString()}.
     *
     * @param string The string to check for flag-like formats.
     * @return true if the string resembles a flag, false otherwise.
     */
    public static boolean isFlagSyntax(String string) {
        if (string == null) {
            return false;
        }

        if (string.startsWith(DEFAULT_PREFIX) && string.endsWith(DEFAULT_POSTFIX)) {
            String part = string.substring(
                    DEFAULT_PREFIX.length(),
                    string.length() - DEFAULT_POSTFIX.length()
            );
            return part.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX);
        }

        if (string.startsWith(DEFAULT_ALIAS_PREFIX) && string.endsWith(DEFAULT_ALIAS_POSTFIX)) {
            String part = string.substring(
                    DEFAULT_ALIAS_PREFIX.length(),
                    string.length() - DEFAULT_ALIAS_POSTFIX.length()
            );
            return part.matches(FULL_OR_ALIAS_NAME_VALIDATION_REGEX);
        }

        return false;
    }

    /**
     * Returns a string representation of this flag.
     * Equivalent to the result from {@link #getFlagString()}.
     *
     * @return The string representation of this flag.
     */
    @Override
    public String toString() {
        return this.getFlagString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, prefix, postfix, alias, aliasPrefix, aliasPostfix);
    }

    /**
     * Returns whether the two flags have the same full flag string formats.
     */
    public boolean equalsFlagString(Flag other) {
        if (other == null) {
            return false;
        }

        return this.getFlagString().equals(other.getFlagString());
    }


    /**
     * Returns whether the two flags have the same flag alias formats.
     */
    public boolean equalsFlagAliasString(Flag other) {
        if (other == null) {
            return false;
        }

        return this.getFlagAliasString().equals(other.getFlagAliasString());
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
                && Objects.equals(postfix, otherFlag.postfix)
                && Objects.equals(alias, otherFlag.alias)
                && Objects.equals(aliasPrefix, otherFlag.aliasPrefix)
                && Objects.equals(aliasPostfix, otherFlag.aliasPostfix);
    }
}
