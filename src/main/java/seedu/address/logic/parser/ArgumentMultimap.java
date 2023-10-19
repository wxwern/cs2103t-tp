package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Stores mapping of flags to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same flag.
 */
public class ArgumentMultimap {

    /** Flags mapped to their respective arguments. **/
    private final Map<Flag, List<String>> argMultimap = new HashMap<>();

    /** The preamble value (the text before the first valid flag). **/
    private String preamble = "";

    /**
     * Associates the specified argument value with {@code flag} key in this map.
     * If the map previously contained a mapping for the key, the new value is appended to the list of existing values.
     * Leading and trailing whitespaces are trimmed, and null values are treated as empty strings.
     *
     * @param flag     Flag key with which the specified argument value is to be associated.
     * @param argValue Argument value to be associated with the specified flag key.
     */
    public void put(Flag flag, String argValue) {
        List<String> argValues = getAllValues(flag);
        argValues.add(argValue == null ? "" : argValue.trim());
        argMultimap.put(flag, argValues);
    }

    /**
     * Associates the specified value with the preamble of this map.
     * If the map previously contained a preamble, it will be replaced with this one.
     * Leading and trailing whitespaces are trimmed, and null values are treated as empty strings.
     *
     * @param preamble Argument value to be associated with the preamble.
     */
    public void putPreamble(String preamble) {
        this.preamble = preamble == null ? "" : preamble.trim();
    }

    /**
     * Returns whether there exists at least one occurrence of the given {@code flag} in this map.
     * Invoking {@code .hasFlag(flag)} is equivalent to the result obtained via {@code .getValue(flag).isPresent()}.
     */
    public boolean hasFlag(Flag flag) {
        return !getAllValues(flag).isEmpty();
    }

    /**
     * Returns whether there exists at least one occurrence of all of these {@code flags} in this map.
     * Equivalent to the AND of booleans obtained via {@link #hasFlag} for all provided flags.
     */
    public boolean hasAllOfFlags(Flag... flags) {
        for (Flag flag : flags) {
            if (!this.hasFlag(flag)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether there exists at least one occurrence of at least one of these {@code flags} in this map.
     * Equivalent to the OR of booleans obtained via {@link #hasFlag} for all provided flags.
     */
    public boolean hasAnyOfFlags(Flag... flags) {
        for (Flag flag : flags) {
            if (this.hasFlag(flag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the given {@code flag} has a non-empty value assigned to it.
     * This returns true if the flag exists and is set to some non-empty string, and false otherwise.
     */
    public boolean hasNonEmptyValue(Flag flag) {
        for (String value : getAllValues(flag)) {
            if (!value.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the last value of {@code flag}, if the flag exists.
     * Note that an empty string or longer is guaranteed to be given if the flag exists.
     */
    public Optional<String> getValue(Flag flag) {
        List<String> values = getAllValues(flag);
        return values.isEmpty() ? Optional.empty() : Optional.of(values.get(values.size() - 1));
    }

    /**
     * Returns all values of {@code flag}.
     * If the flag does not exist or has no values assigned (i.e., not even empty strings), this returns an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    public List<String> getAllValues(Flag flag) {
        if (!argMultimap.containsKey(flag)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(argMultimap.get(flag));
    }

    /**
     * Returns the preamble (text before the first valid flag).
     */
    public String getPreamble() {
        return preamble;
    }

    /**
     * Throws a {@code ParseException} if any of the flags given in {@code flags} appeared more than
     * once among the arguments.
     */
    public void verifyNoDuplicateFlagsFor(Flag... flags) throws ParseException {
        Flag[] duplicatedFlags = Stream.of(flags).distinct()
                .filter(flag -> argMultimap.containsKey(flag) && argMultimap.get(flag).size() > 1)
                .toArray(Flag[]::new);

        if (duplicatedFlags.length > 0) {
            throw new ParseException(Messages.getErrorMessageForDuplicateFlags(duplicatedFlags));
        }
    }

    /**
     * Throws a {@code ParseException} if there exists any more flags than the ones given in {@code flags}
     * among the ones put in this map.
     */
    public void verifyNoExtraneousFlagsOnTopOf(Flag... flags) throws ParseException {
        List<Flag> referenceFlagsList = List.of(flags);

        Flag[] extraneousFlags = argMultimap.keySet().stream()
                .filter(f -> !referenceFlagsList.contains(f))
                .toArray(Flag[]::new);

        if (extraneousFlags.length > 0) {
            throw new ParseException(Messages.getErrorMessageForExtraneousFlags(extraneousFlags));
        }
    }

    /**
     * Throws a {@code ParseException} if any of the flags given in {@code flags} have a non-empty value
     * assigned to it.
     */
    public void verifyAllEmptyValuesAssignedFor(Flag... flags) throws ParseException {
        Flag[] flagsWithUsefulValues = Stream.of(flags).distinct()
                .filter(argMultimap::containsKey)
                .filter(f -> argMultimap.get(f).stream().anyMatch(s -> !s.isEmpty()))
                .toArray(Flag[]::new);

        if (flagsWithUsefulValues.length > 0) {
            throw new ParseException(Messages.getErrorMessageForNonEmptyValuedFlags(flagsWithUsefulValues));
        }
    }
}
