package seedu.address.logic.autocomplete;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Creates a generator based on the given supplier or reference commands, so as it can generate
 * auto-completions when requested.
 */
public class AutocompleteGenerator {

    /** An autocompletion generator that generates no results. */
    public static final AutocompleteGenerator NO_RESULTS = new AutocompleteGenerator(Stream::empty);


    /** A comparator used to order fuzzily matched strings where better matches against the input go first. */
    private static final Function<String, Comparator<String>> TEXT_FUZZY_MATCH_COMPARATOR = (input) -> (s1, s2) -> {
        // Get how well s1 is ahead of s2 (note: higher is better).
        int score = StringUtil.getFuzzyMatchScore(input, s1) - StringUtil.getFuzzyMatchScore(input, s2);

        return -score; // -ve implies s1 < s2
    };

    /** A comparator used to order fuzzily matched flags where better matches against the input go first. */
    private static final Function<String, Comparator<Flag>> FLAG_FUZZY_MATCH_COMPARATOR = (input) -> (f1, f2) -> {
        // Get how well f1 is ahead of f2 in both metrics (note: higher is better).
        int score = Math.max(
                StringUtil.getFuzzyMatchScore(input, f1.getFlagString()),
                StringUtil.getFuzzyMatchScore(input, f1.getFlagAliasString())
        ) - Math.max(
                StringUtil.getFuzzyMatchScore(input, f2.getFlagString()),
                StringUtil.getFuzzyMatchScore(input, f2.getFlagAliasString())
        );

        return -score; // -ve implies f1 < f2
    };



    /** The cached instance of the result evaluation function. */
    private final BiFunction<String, Model, Stream<String>> resultEvaluationFunction;

    /**
     * Constructs an autocomplete generator based on the given set of reference full command strings.
     */
    public AutocompleteGenerator(Stream<String> referenceCommands) {
        this(() -> referenceCommands);
    }

    /**
     * Constructs an autocomplete generator based on the given supplier of full command strings.
     */
    public AutocompleteGenerator(Supplier<Stream<String>> referenceCommandsSupplier) {
        resultEvaluationFunction = (partialCommand, model) -> {
            if (partialCommand == null) {
                return Stream.empty();
            }

            return referenceCommandsSupplier.get()
                    .filter(term -> StringUtil.isFuzzyMatch(partialCommand, term))
                    .sorted(TEXT_FUZZY_MATCH_COMPARATOR.apply(partialCommand))
                    .distinct();
        };
    }

    /**
     * Constructs an autocomplete generator based on the given {@link AutocompleteSupplier}.
     */
    public AutocompleteGenerator(AutocompleteSupplier supplier) {
        resultEvaluationFunction = (partialCommand, model) -> {

            PartitionedCommand command = new PartitionedCommand(partialCommand == null ? "" : partialCommand);
            String trailingText = command.getTrailingText();

            // Compute the possible flags and flag-values.
            Stream<String> possibleFlags = getPossibleFlags(command, supplier)
                    .filter(flag -> StringUtil.isFuzzyMatch(trailingText, flag.getFlagString())
                            || StringUtil.isFuzzyMatch(trailingText, flag.getFlagAliasString()))
                    .sorted(FLAG_FUZZY_MATCH_COMPARATOR.apply(trailingText))
                    .map(Flag::getFlagString);

            Stream<String> possibleValues = getPossibleValues(command, supplier, model)
                    .map(s -> s.filter(term -> StringUtil.isFuzzyMatch(trailingText, term))
                            .sorted(TEXT_FUZZY_MATCH_COMPARATOR.apply(trailingText)))
                    .orElse(possibleFlags);

            // Decide which stream to use based on whether it's of a flag syntax or not.
            Stream<String> possibleTerminalValues;
            if (command.hasFlagSyntaxPrefixInTrailingText()) {
                possibleTerminalValues = possibleFlags;
            } else {
                possibleTerminalValues = possibleValues;
            }

            // Return the results as a full completion string, distinct.
            return possibleTerminalValues
                    .map(command::toStringWithNewTrailingTerm)
                    .distinct();
        };
    }

    /**
     * Generates a stream of completions based on the partial command given and no model.
     * Note that omitting the model may limit the ability to return useful results.
     */
    public Stream<String> generateCompletions(String command) {
        return resultEvaluationFunction.apply(command, null);
    }

    /**
     * Generates a stream of completions based on the partial command given and the model.
     */
    public Stream<String> generateCompletions(String command, Model model) {
        return resultEvaluationFunction.apply(command, model);
    }




    /**
     * Obtains the set of possible flags based on the partitioned command and supplier.
     */
    private static Stream<Flag> getPossibleFlags(
            PartitionedCommand command,
            AutocompleteSupplier supplier
    ) {
        Flag[] allPossibleFlags = supplier.getAllPossibleFlags().toArray(Flag[]::new);

        Set<Flag> existingCommandFlags = command.getConfirmedFlagStrings()
                .stream()
                .map(flagStr -> Flag.findMatch(flagStr, allPossibleFlags))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());


        return supplier.getOtherPossibleFlagsAsideFromFlagsPresent(existingCommandFlags).stream();
    }

    /**
     * Obtains the optional set of possible values based on the partitioned command, supplier, and model.
     * If this optional is empty, that means it is explicitly specified that the flag cannot accept values.
     */
    private static Optional<Stream<String>> getPossibleValues(
            PartitionedCommand command,
            AutocompleteSupplier supplier,
            Model model
    ) {
        return supplier.getValidValues(
                Flag.parseOptional(
                        command.getLastConfirmedFlagString().orElse(null)
                ).orElse(null),
                model
        );
    }

}
