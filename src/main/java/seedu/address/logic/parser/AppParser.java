package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.ClassMappings.COMMAND_TO_PARSER_MAP;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.autocomplete.AutocompleteGenerator;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Processes user input for the application.
 * It is a utility class for parsing and performing actions on command strings app-wide.
 */
public class AppParser {


    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    private static final Logger logger = LogsCenter.getLogger(AppParser.class);



    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string.
     * @return the command based on the user input.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);


        // Iterate through the available command and parser classes and locate the one matching the command word.
        final Optional<Class<? extends Command>> commandClass = COMMAND_TO_PARSER_MAP
                .keySet()
                .stream()
                .filter(c -> commandWord.equals(
                        Command.getCommandWord(c).orElse(null)
                ))
                .findFirst();

        final Optional<Class<? extends Parser<? extends Command>>> parserClass = commandClass
                .map(COMMAND_TO_PARSER_MAP::get)
                .flatMap(x -> x);

        // Instantiate the class found and return the command instance.
        try {

            if (parserClass.isPresent()) {
                return parserClass.get()
                        .getDeclaredConstructor()
                        .newInstance()
                        .parse(arguments);

            } else if (commandClass.isPresent()) {
                return commandClass
                        .get()
                        .getDeclaredConstructor()
                        .newInstance();

            } else {
                // We don't know what this command is.
                // - Note: To add a support for new commands, add them in ClassMappings.java.
                logger.finer("This user input has no known mapped commands: " + userInput);
                throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
            }

        } catch (InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException
                 | InvocationTargetException e) {

            assert false
                    : "All command and parser classes should be correctly defined to have an no-args constructor, "
                    + "but " + e.getClass().getName() + " was thrown for command '" + commandWord + "'!";

            logger.severe("This user input unexpectedly caused an error during instantiation: " + e);
            logger.severe("Will report to the user that the command doesn't exist instead.");
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

    /**
     * Parses user input into an evaluator that can be executed to obtain autocompletion results.
     *
     * @param userInput full user input string.
     * @return the command based on the user input.
     */
    public AutocompleteGenerator parseCompletionGenerator(String userInput) {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches() && !userInput.isEmpty()) {
            return AutocompleteGenerator.NO_RESULTS;
        }
        final String commandWord = userInput.isEmpty() ? "" : matcher.group("commandWord");

        logger.finest("Preparing autocomplete: '" + userInput + "'");



        // Case 1: There is no command name followed by a whitespace character.
        // - The command name is incomplete - we're still typing the name.
        // - Suggest available command names.
        if (!userInput.matches(".+\\s.*")) {
            return new AutocompleteGenerator(() ->
                    Command.getCommandWords(COMMAND_TO_PARSER_MAP.keySet().stream())
                            .filter(Optional::isPresent)
                            .map(Optional::get)
            );
        }

        // Case 2: There exists a whitespace character.
        // - The command name is complete, and we're typing the arguments now.
        // - Lookup and suggest with the relevant command supplier.
        return COMMAND_TO_PARSER_MAP.keySet()
                .stream()
                .filter(cls -> commandWord.equals(
                        Command.getCommandWord(cls).orElse(null)
                ))
                .findFirst()
                .flatMap(Command::getAutocompleteSupplier)
                .map(AutocompleteGenerator::new)
                .orElse(AutocompleteGenerator.NO_RESULTS);
    }

}
