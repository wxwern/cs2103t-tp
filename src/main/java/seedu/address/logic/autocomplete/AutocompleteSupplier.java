package seedu.address.logic.autocomplete;

import java.util.Collection;
import java.util.HashSet;
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
     *
     * @param uniqueFlagSets  A set of group of flags, where each group may only have exactly one flag within it be
     *                        present in commands. Thus, if the set is <code>{{ -A, -B }, { -C }}</code>, then this
     *                        command may have <code>-A</code>, or <code>-A -C</code>, or <code>-B -C</code>...
     *                        but NEVER <code>-A -B</code>, or <code>-C -C</code>...
     * @param repeatableFlags A set of flags that may appear at any point in the command any number of times.
     * @param values          A map of auto-completable values for each flag that may be obtained via a model.
     */
    public AutocompleteSupplier(
            Set<Set<Flag>> uniqueFlagSets,
            Set<Flag> repeatableFlags,
            Map<Flag, Function<Model, List<String>>> values
    ) {
        this.uniqueFlagSets = new LinkedHashSet<>(uniqueFlagSets);
        this.repeatableFlags = new LinkedHashSet<>(repeatableFlags);
        this.values = new LinkedHashMap<>(values);
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     *
     * @see #AutocompleteSupplier(Set, Set, Map)
     */
    public AutocompleteSupplier(
            Set<Set<Flag>> uniqueFlagSets, Set<Flag> repeatableFlags
    ) {
        this(uniqueFlagSets, repeatableFlags, Map.of());
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     *
     * @param uniqueFlags A set of flags that each may appear at most once in the command.
     * @param repeatableFlags A set of flags that may appear at any point in the command any number of times.
     */
    public static AutocompleteSupplier fromFlags(
            Set<Flag> uniqueFlags, Set<Flag> repeatableFlags
    ) {
        return new AutocompleteSupplier(
                uniqueFlags != null
                        ? uniqueFlags.stream().map(Set::of).collect(Collectors.toSet())
                        : Set.of(),
                repeatableFlags
        );
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the unique flags (flags which may appear
     * at most once) for autocompleting some command.
     *
     * @see #fromFlags(Set, Set)
     */
    public static AutocompleteSupplier fromUniqueFlags(Set<Flag> uniqueFlags) {
        return fromFlags(uniqueFlags, Set.of());
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the repeatable flags (flags which may
     * appear any number of times in the command) for autocompleting some command.
     *
     * @see #fromFlags(Set, Set)
     */
    public static AutocompleteSupplier fromRepeatableFlags(Set<Flag> repeatableFlags) {
        return fromFlags(Set.of(), repeatableFlags);
    }

    /**
     * Returns a set of all possible flags that can be used in the command.
     */
    public Set<Flag> getAllPossibleFlags() {
        Set<Flag> result = new HashSet<>();

        result.addAll(repeatableFlags);
        result.addAll(uniqueFlagSets.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet())
        );

        return result;
    }

    /**
     * Returns a set of other possible flags given the list of flags that are already present in the command.
     */
    public Set<Flag> getOtherPossibleFlagsAsideFromFlagsPresent(Set<Flag> flagsPresent) {
        Set<Flag> result = new HashSet<>();

        // Add groups of flags from the unique flags,
        // provided no flag in any group is already present in the command.
        uniqueFlagSets.stream()
                .filter(flagSet -> flagSet.stream().anyMatch(flagsPresent::contains))
                .forEach(result::addAll);

        // Add all repeatable flags. They don't matter.
        result.addAll(repeatableFlags);

        return result;
    }

    /**
     * Returns a list of possible values for a flag when computed against a given model.
     */
    public List<String> getValidValues(Flag flag, Model model) {
        return this.values.getOrDefault(flag, m -> List.of()).apply(model);
    }


}
