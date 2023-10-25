package seedu.address.logic.autocomplete;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;

/**
 * Computes the available autocomplete results using a given supplier.
 */
public class AutocompleteEngine {

    /**
     * Generates a set of possible command completions given the partial command and the expected full commands.
     * The set is guaranteed to have a consistent iteration order dependent on the expected full commands array.
     */
    public static Set<String> generateCompletions(String partialCommand, String[] expectedFullCommands) {
        return Arrays.stream(expectedFullCommands)
                .filter(s -> s.startsWith(partialCommand))
                .sorted()
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * Generates a set of possible command completions given the partial command, autocomplete supplier and model.
     * The set is guaranteed to have a consistent iteration order dependent on the supplier's results.
     */
    public static Set<String> generateCompletions(String partialCommand, AutocompleteSupplier supplier, Model model) {
        PartitionedCommand command = new PartitionedCommand(partialCommand);

        Stream<String> possibleTerminalValues;
        if (command.hasFlagSyntaxPrefixInTrailingText()) {
            // The trailing text is a flag-like term - try to autocomplete flags.
            possibleTerminalValues = getPossibleFlags(command, supplier)
                    .filter(flag -> flag.getFlagString().startsWith(command.getTrailingText())
                            || flag.getFlagAliasString().startsWith(command.getTrailingText()))
                    .map(Flag::getFlagString);
        } else {
            // The trailing text is a value - try to autocomplete values.
            possibleTerminalValues = getPossibleValues(command, supplier, model)
                    .filter(term -> term.startsWith(command.getTrailingText()));
        }

        return possibleTerminalValues
                .map(command::toStringWithNewTrailingTerm)
                .collect(Collectors.toCollection(LinkedHashSet::new));
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
                .orElse(Stream.of());
    }



}
