package seedu.address.logic.autocomplete;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Supplies autocompletion details for arbitrary commands.
 */
public class AutocompleteSupplier {

    private final Set<Set<Flag>> uniqueFlagSets;
    private final Set<Flag> repeatableFlags;
    private final Map<Flag, Function<Model, List<String>>> values;

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param uniqueFlagSets  A set of group of flags, where each group may only have exactly one flag within it be
     *                        present in commands. Thus, if the set is <code>{{ -A, -B }, { -C }}</code>, then this
     *                        command may have <code>-A</code>, or <code>-A -C</code>, or <code>-B -C</code>...
     *                        but NEVER <code>-A -B</code>, or <code>-C -C</code>...
     * @param repeatableFlags A set of flags that may appear at any point in the command any number of times.
     * @param values          A map of auto-completable values for each flag that may be obtained via a model.
     */
    public AutocompleteSupplier(
            List<Set<Flag>> uniqueFlagSets,
            List<Flag> repeatableFlags,
            Map<Flag, Function<Model, List<String>>> values
    ) {
        // Create new copies to prevent external modification.
        this.uniqueFlagSets = new LinkedHashSet<>(uniqueFlagSets);
        this.repeatableFlags = new LinkedHashSet<>(repeatableFlags);
        this.values = new LinkedHashMap<>(values);
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @see #AutocompleteSupplier(List, List, Map)
     */
    public AutocompleteSupplier(
            List<Set<Flag>> uniqueFlagSets, List<Flag> repeatableFlags
    ) {
        this(uniqueFlagSets, repeatableFlags, Map.of());
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param uniqueFlags A set of flags that each may appear at most once in the command.
     * @param repeatableFlags A set of flags that may appear at any point in the command any number of times.
     */
    public static AutocompleteSupplier fromFlags(
            List<Flag> uniqueFlags, List<Flag> repeatableFlags
    ) {
        return new AutocompleteSupplier(
                uniqueFlags != null
                        ? uniqueFlags.stream().map(Set::of).collect(Collectors.toList())
                        : List.of(),
                repeatableFlags
        );
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the unique flags (flags which may appear
     * at most once) for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @see #fromFlags(List, List)
     */
    public static AutocompleteSupplier fromUniqueFlags(List<Flag> uniqueFlags) {
        return fromFlags(uniqueFlags, List.of());
    }

    /**
     * @see #fromUniqueFlags(List)
     */
    public static AutocompleteSupplier fromUniqueFlags(Flag... uniqueFlags) {
        return fromUniqueFlags(List.of(uniqueFlags));
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the repeatable flags (flags which may
     * appear any number of times in the command) for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @see #fromFlags(List, List)
     */
    public static AutocompleteSupplier fromRepeatableFlags(List<Flag> repeatableFlags) {
        return fromFlags(List.of(), repeatableFlags);
    }

    /**
     * @see #fromRepeatableFlags(List)
     */
    public static AutocompleteSupplier fromRepeatableFlags(Flag... uniqueFlags) {
        return fromRepeatableFlags(List.of(uniqueFlags));
    }

    /**
     * Returns a set of all possible flags that can be used in the command.
     * The set has predictable iteration order: it follows the ordering supplied via the original inputs.
     */
    public Set<Flag> getAllPossibleFlags() {
        Set<Flag> result = new LinkedHashSet<>();

        result.addAll(uniqueFlagSets.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        result.addAll(repeatableFlags);

        return result;
    }

    /**
     * Returns a set of other possible flags given the list of flags that are already present in the command.
     * If there are conflicting constraints specified, this will use the tightest possible constraint.
     * The set has predictable iteration order: it follows the ordering supplied via the original inputs.
     */
    public Set<Flag> getOtherPossibleFlagsAsideFromFlagsPresent(Set<Flag> flagsPresent) {
        Set<Flag> exclusionSet = new LinkedHashSet<>();

        // Find all groups that already exist and add them to the exclusions.
        uniqueFlagSets.stream()
                .filter(flagSet -> flagSet.stream().anyMatch(flagsPresent::contains))
                .forEach(exclusionSet::addAll);

        // Get all flags, then subtract the exclusions to obtain all remaining possibilities.
        Set<Flag> resultSet = getAllPossibleFlags();
        resultSet.removeAll(exclusionSet);

        return resultSet;
    }

    /**
     * Returns a list of possible values for a flag when computed against a given model.
     */
    public List<String> getValidValues(Flag flag, Model model) {
        try {
            return this.values.getOrDefault(flag, m -> List.of()).apply(model);
        } catch (NullPointerException e) {
            // Guard against NPEs due to supplied lambdas not handling them.
            // We simply assume no auto-completion values are available.
            return List.of();
        }
    }


}
