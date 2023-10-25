package seedu.address.logic.autocomplete;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Computes the available autocomplete results using a given supplier.
 * This functions as a utility class which provides static methods for immediately generating completions.
 */
public class AutocompleteUtil {

    /** A comparator used to order fuzzily matched strings where better matches against the input go first. */
    private static final Function<String, Comparator<String>> TEXT_FUZZY_MATCH_COMPARATOR = (input) -> (s1, s2)
            -> -(StringUtil.getFuzzyMatchScore(input, s1) - StringUtil.getFuzzyMatchScore(input, s2));

    /** A comparator used to order fuzzily matched flags where better matches against the input go first. */
    private static final Function<String, Comparator<Flag>> FLAG_FUZZY_MATCH_COMPARATOR = (input) -> (o1, o2)
            -> {

        // Get how well o1 is ahead of o2 in both metrics (note: higher is better).
        int scoreStd = StringUtil.getFuzzyMatchScore(input, o1.getFlagString())
                - StringUtil.getFuzzyMatchScore(input, o2.getFlagString());

        int scoreAlias = StringUtil.getFuzzyMatchScore(input, o1.getFlagAliasString())
                - StringUtil.getFuzzyMatchScore(input, o2.getFlagAliasString());

        // Use standard flag score first, then alias score
        if (scoreStd != 0) {
            return -scoreStd;
        } else if (scoreAlias != 0) {
            return -scoreAlias;
        } else {
            return 0;
        }
    };

    /**
     * Generates a set of possible command completions given the partial command and the expected full commands.
     * The returned stream is guaranteed to have a consistent iteration order dependent on the original stream.
     */
    public static Stream<String> generateCompletions(String partialCommand, Stream<String> expectedFullCommands) {
        if (partialCommand == null) {
            return Stream.empty();
        }

        return expectedFullCommands
                .filter(term -> StringUtil.isFuzzyMatch(partialCommand, term))
                .sorted(TEXT_FUZZY_MATCH_COMPARATOR.apply(partialCommand));
    }

    /**
     * Generates a set of possible command completions given the partial command and the expected full commands.
     * The returned stream is guaranteed to have a consistent iteration order dependent on the original collection,
     * provided it has a well-defined order.
     *
     * @see #generateCompletions(String, Stream)
     */
    public static Stream<String> generateCompletions(String partialCommand, Collection<String> expectedFullCommands) {
        return generateCompletions(partialCommand, expectedFullCommands.stream());
    }

    /**
     * Generates a set of possible command completions given the partial command and the expected full commands.
     * The returned stream is guaranteed to have a consistent iteration order dependent on the original stream.
     *
     * @see #generateCompletions(String, Stream)
     */
    public static Stream<String> generateCompletions(String partialCommand, String[] expectedFullCommands) {
        return generateCompletions(partialCommand, Arrays.stream(expectedFullCommands));
    }

    /**
     * Generates a set of possible command completions given the partial command, autocomplete supplier and model.
     * The set is guaranteed to have a consistent iteration order dependent on the supplier's results.
     */
    public static Stream<String> generateCompletions(
            String partialCommand, AutocompleteSupplier supplier, Model model
    ) {
        PartitionedCommand command = new PartitionedCommand(partialCommand == null ? "" : partialCommand);
        String trailingText = command.getTrailingText();

        Stream<String> possibleTerminalValues;
        if (command.hasFlagSyntaxPrefixInTrailingText()) {
            // The trailing text is a flag-like term - try to autocomplete flags.
            possibleTerminalValues = getPossibleFlags(command, supplier)
                    .filter(flag -> StringUtil.isFuzzyMatch(trailingText, flag.getFlagString())
                            || StringUtil.isFuzzyMatch(trailingText, flag.getFlagAliasString()))
                    .sorted(FLAG_FUZZY_MATCH_COMPARATOR.apply(trailingText))
                    .map(Flag::getFlagString);

        } else {
            // The trailing text is a value - try to autocomplete values.
            possibleTerminalValues = getPossibleValues(command, supplier, model)
                    .filter(term -> StringUtil.isFuzzyMatch(trailingText, term))
                    .sorted(TEXT_FUZZY_MATCH_COMPARATOR.apply(trailingText));
        }

        return possibleTerminalValues
                .map(command::toStringWithNewTrailingTerm)
                .distinct();
    }



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


    private static Stream<String> getPossibleValues(
            PartitionedCommand command,
            AutocompleteSupplier supplier,
            Model model
    ) {
        return command.getLastConfirmedFlagString()
                .flatMap(Flag::parseOptional)
                .map(f -> supplier.getValidValues(f, model).stream())
                .orElse(Stream.empty());
    }



}
