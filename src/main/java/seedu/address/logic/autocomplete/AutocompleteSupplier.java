package seedu.address.logic.autocomplete;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

import seedu.address.logic.autocomplete.components.AutocompleteItemSet;
import seedu.address.logic.autocomplete.components.FlagValueSupplier;
import seedu.address.logic.autocomplete.components.PartitionedCommand;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Supplies autocompletion details for arbitrary commands.
 */
public class AutocompleteSupplier {

    private final AutocompleteItemSet<Flag> flags;
    private final Map<Flag, FlagValueSupplier> values;

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param flags   The set of flags that should be used as part of the autocomplete results.
     * @param values  A map of auto-completable values for each flag that may be obtained via a model.
     */
    public AutocompleteSupplier(
            AutocompleteItemSet<Flag> flags,
            Map<Flag, FlagValueSupplier> values
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
     * @see #AutocompleteSupplier(AutocompleteItemSet, Map)
     */
    public static AutocompleteSupplier from(AutocompleteItemSet<Flag> flags) {
        return new AutocompleteSupplier(flags, Map.of());
    }

    /**
     * Constructs an autocomplete supplier that is capable of supplying useful details for autocompleting some command.
     * The ordering supplied via the parameters are followed when generating results.
     *
     * @param flagSets The sets of flags that should be used together as part of the autocomplete results.
     */
    @SafeVarargs
    public static AutocompleteSupplier from(AutocompleteItemSet<Flag>... flagSets) {
        return from(AutocompleteItemSet.concat(flagSets));
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
                AutocompleteItemSet.onceForEachOf(uniqueFlags)
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
                AutocompleteItemSet.anyNumberOf(repeatableFlags)
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
     * Returns an optional stream of possible values for a flag when computed against a given model.
     * If this optional is empty, then this flag is explicitly specified to not have any values,
     * and not just the lack of completion suggestions.
     *
     * @param flag The flag to check against. This may be null to represent the preamble.
     * @param currentCommand The current command structure. This should not be null.
     * @param model The model to be supplied for generation. This may be null if the model is unavailable.
     */
    public Optional<Stream<String>> getValidValuesForFlag(Flag flag, PartitionedCommand currentCommand, Model model) {
        try {
            return Optional.ofNullable(
                    this.values.getOrDefault(flag, (c, m) -> Stream.empty())
            ).map(flagValueSupplier -> flagValueSupplier.apply(currentCommand, model));

        } catch (RuntimeException e) {
            // Guard against errors like NPEs due to supplied lambdas not handling them.
            e.printStackTrace();
            // We simply return that we don't know what to auto-complete by.
            return Optional.of(Stream.of());
        }
    }

    /**
     * Configures the set of flags within this autocomplete supplier using the given {@code operator}.
     * This also returns {@code this} instance, which is useful for chaining.
     */
    public AutocompleteSupplier configureFlagSet(Consumer<AutocompleteItemSet<Flag>> operator) {
        operator.accept(this.flags);
        return this;
    }

    /**
     * Configures the map of values within this autocomplete supplier using the given {@code operator}.
     * This also returns {@code this} instance, which is useful for chaining.
     */
    public AutocompleteSupplier configureValueMap(Consumer<Map<Flag, FlagValueSupplier>> operator) {
        operator.accept(this.values);
        return this;
    }

}
