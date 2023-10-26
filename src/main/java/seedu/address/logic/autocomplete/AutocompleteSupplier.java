package seedu.address.logic.autocomplete;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Supplies autocompletion details for arbitrary commands.
 */
public class AutocompleteSupplier {

    private final AutocompleteDataSet<Flag> flags;
    private final Map<Flag, Function<Model, List<String>>> values;

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param flags   The set of flags that should be used as part of the autocomplete results.
     * @param values  A map of auto-completable values for each flag that may be obtained via a model.
     */
    public AutocompleteSupplier(
            AutocompleteDataSet<Flag> flags,
            Map<Flag, Function<Model, List<String>>> values
    ) {
        // Create new copies to prevent external modification.
        this.flags = flags.copy();
        this.values = new LinkedHashMap<>(values);
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param flags The set of flags that should be used as part of the autocomplete results.
     *
     * @see #AutocompleteSupplier(AutocompleteDataSet, Map)
     */
    public static AutocompleteSupplier from(AutocompleteDataSet<Flag> flags) {
        return new AutocompleteSupplier(flags, Map.of());
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param flagSets The sets of flags that should be used together as part of the autocomplete results.
     */
    @SafeVarargs
    public static AutocompleteSupplier from(AutocompleteDataSet<Flag>... flagSets) {
        return from(AutocompleteDataSet.concat(flagSets));
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the unique flags (flags which may appear
     * at most once) for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param uniqueFlags A set of flags that each may appear at most once in the command.
     */
    public static AutocompleteSupplier fromUniqueFlags(Flag... uniqueFlags) {
        return AutocompleteSupplier.from(
                AutocompleteDataSet.onceForEachOf(uniqueFlags)
        );
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying all the repeatable flags (flags which may
     * appear any number of times in the command) for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param repeatableFlags A set of flags that may appear at any point in the command any number of times.
     */
    public static AutocompleteSupplier fromRepeatableFlags(Flag... repeatableFlags) {
        return AutocompleteSupplier.from(
                AutocompleteDataSet.anyNumberOf(repeatableFlags)
        );
    }

    /**
     * Returns a set of all possible flags that can be used in the command.
     * The set has predictable iteration order: it follows the ordering supplied via the original inputs.
     */
    public Set<Flag> getAllPossibleFlags() {
        return flags.copy();
    }

    /**
     * Returns a set of other possible flags given the list of flags that are already present in the command.
     * If there are conflicting constraints specified, this will use the tightest possible constraint.
     * The set has predictable iteration order: it follows the ordering supplied via the original inputs.
     */
    public Set<Flag> getOtherPossibleFlagsAsideFromFlagsPresent(Set<Flag> flagsPresent) {
        return flags.getElementsAfterConsuming(flagsPresent);
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
