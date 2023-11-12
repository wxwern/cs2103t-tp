package seedu.address.logic.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ApplyCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ReminderCommand;
import seedu.address.logic.commands.SortCommand;

/**
 * A utility class that provides a mapping of all available class values.
 */
public class ClassMappings {

    /** Provides information about commands as a map of command classes to their parser classes. */
    public static final Map<Class<? extends Command>, Optional<Class<? extends Parser<? extends Command>>>>
            COMMAND_TO_PARSER_MAP = getCommandToParserMap();

    /**
     * Creates and returns an ordered map of command classes as keys, and optionally their corresponding parsers
     * if they accept arguments.
     *
     * <p>
     * Developer's note: This method may be modified to include support for new commands.
     * </p>
     */
    private static Map<Class<? extends Command>, Optional<Class<? extends Parser<? extends Command>>>>
        getCommandToParserMap() {

        // Create a map that preserves insertion order.
        Map<Class<? extends Command>, Optional<Class<? extends Parser<? extends Command>>>>
                orderedMap = new LinkedHashMap<>();

        // Insert the command and parser pairs in order.
        orderedMap.put(AddCommand.class, Optional.of(AddCommandParser.class));
        orderedMap.put(ApplyCommand.class, Optional.of(ApplyCommandParser.class));
        orderedMap.put(DeleteCommand.class, Optional.of(DeleteCommandParser.class));
        orderedMap.put(EditCommand.class, Optional.of(EditCommandParser.class));

        orderedMap.put(ListCommand.class, Optional.of(ListCommandParser.class));
        orderedMap.put(FindCommand.class, Optional.of(FindCommandParser.class));
        orderedMap.put(SortCommand.class, Optional.of(SortCommandParser.class));
        orderedMap.put(ReminderCommand.class, Optional.of(ReminderCommandParser.class));

        orderedMap.put(HelpCommand.class, Optional.empty());
        orderedMap.put(ClearCommand.class, Optional.empty());
        orderedMap.put(ExitCommand.class, Optional.empty());

        // Validate that the resulting map is valid.
        assert isCommandToParserMapOperational(orderedMap);

        // Return the result.
        return orderedMap;
    }

    /**
     * This is a helper method to validate whether the given map of commands and parsers meet the expected
     * specifications.
     *
     * <ul>
     *     <li>All commands must have a command word.</li>
     *     <li>All commands must either have a parser that can initialize with no arguments, or itself be initalized
     *     with no-arguments</li>
     * </ul>
     *
     * <p>
     *     This method's purpose for validating the specifications is because we would be retrieving values
     *     directly via Java's Reflection API and initializing the instances that way, which does not have compile time
     *     checks. Adding an assertion to ensure this works helps validate that no programmer error has slipped by.
     * </p>
     */
    private static boolean isCommandToParserMapOperational(
            Map<Class<? extends Command>, Optional<Class<? extends Parser<? extends Command>>>> map
    ) {
        try {
            for (var entry: map.entrySet()) {

                // We must have a command word for every command.
                assert Command.getCommandWord(entry.getKey()).isPresent()
                        : "All commands must have COMMAND_WORD set, but "
                        + entry.getKey().getSimpleName() + " does not!";

                if (entry.getValue().isPresent()) {
                    // If there's a parser class, we must be able to initialize them with no args without errors.
                    entry.getValue().get().getDeclaredConstructor().newInstance();
                } else {
                    // Otherwise, we must be able to initialize the command class directly with no args without errors.
                    entry.getKey().getDeclaredConstructor().newInstance();
                }
            }

            return true;

        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {

            assert false
                    : "A parser class must have a no-args constructor. "
                    + "If a command has no parser class, then the command class must have a no-args constructor. "
                    + "However, an initialization has unexpectedly failed with error: " + e.getMessage();
        }

        return false;
    }

}
