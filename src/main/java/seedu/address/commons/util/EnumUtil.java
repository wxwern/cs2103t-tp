package seedu.address.commons.util;

import java.util.Arrays;

/**
 * Helper class to for enums to perform common operations.
 */
public class EnumUtil {

    private static final String ENUM_LOOKUP_FAILURE_STRING_FORMAT =
            "'%s' is not a valid string representation for any enum constants of type '%s'";

    /**
     * Obtains the enum constant by looking up the enum's {@link Enum#toString()} value that matches the given input.
     *
     * @param input The input string to match against the enum's string with.
     * @param cls The enum class.
     * @param <E> The enum type.
     * @return The enum constant.
     * @throws IllegalArgumentException if the string does not match any of the enum's values.
     */
    public static <E extends Enum<E>> E lookupByToString(Class<E> cls, String input)
            throws IllegalArgumentException {

        return Arrays.stream(cls.getEnumConstants())
                .filter(e -> e.toString().equals(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(
                        ENUM_LOOKUP_FAILURE_STRING_FORMAT,
                        input, cls.getSimpleName()
                )));
    }

    /**
     * Checks whether there exists an enum constant whose {@link Enum#toString()} value matches the given input.
     *
     * @param input The input string to match against the enum's string with.
     * @param cls The enum class to enumerate through.
     * @param <E> The enum type.
     * @return true if an enum constant with the given input string as text representation exists, false otherwise.
     */
    public static <E extends Enum<E>> boolean hasMatchingToString(Class<E> cls, String input) {
        return Arrays.stream(cls.getEnumConstants()).anyMatch(e -> e.toString().equals(input));
    }

}
