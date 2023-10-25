package seedu.address.logic.commands;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;



    /**
     * Obtains the {@code AUTOCOMPLETE_SUPPLIER} which can supply autocompletion results for a given command.
     *
     * @param cls The subclass of {@link Command} to obtain the autocomplete supplier for.
     * @return The autocomplete supplier as an optional.
     */
    public static Optional<AutocompleteSupplier> getAutocompleteSupplier(Class<? extends Command> cls) {
        AutocompleteSupplier supplier = null;
        try {
            Field field = cls.getDeclaredField("AUTOCOMPLETE_SUPPLIER");
            Object data = field.get(cls);
            if (data instanceof AutocompleteSupplier) {
                supplier = (AutocompleteSupplier) data;
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            // No available autocompletion results... too bad.
        }

        return Optional.ofNullable(supplier);
    }

    /**
     * Obtains the {@code COMMAND_WORD} which represents the command name for a given command.
     *
     * @param cls The subclass of {@link Command} to obtain the command word for.
     * @return The command word as an optional string.
     */
    public static Optional<String> getCommandWord(Class<? extends Command> cls) {
        String value = null;
        try {
            Field field = cls.getDeclaredField("COMMAND_WORD");
            Object data = field.get(cls);
            if (data instanceof String) {
                value = (String) data;
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Should not reach here...

            assert cls.equals(Command.class)
                    : "a public COMMAND_WORD static field should be present for Command subclasses, but missing in "
                            + cls.getName();
        }

        return Optional.ofNullable(value);
    }

    /**
     * Obtains a stream of {@code COMMAND_WORD}s which represent the command names for all given commands.
     *
     * @param clsStream The subclasses of {@link Command} to obtain the command word for.
     * @return The command words as a list of optional strings.
     */
    public static Stream<Optional<String>> getCommandWords(Stream<Class<? extends Command>> clsStream) {
        return clsStream.map(Command::getCommandWord);
    }

    /**
     * Obtains a list of {@code COMMAND_WORD}s which represent the command names for all given commands.
     *
     * @see #getCommandWords(Stream)
     */
    public static List<Optional<String>> getCommandWords(Collection<Class<? extends Command>> clsList) {
        return getCommandWords(clsList.stream()).collect(Collectors.toList());
    }



}
