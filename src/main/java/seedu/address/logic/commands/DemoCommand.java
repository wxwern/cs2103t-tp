package seedu.address.logic.commands;

import java.util.Objects;

import seedu.address.commons.util.AppUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class DemoCommand extends Command {

    public static final String COMMAND_WORD = "demo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": A demo command that demonstrates how internal code changes are made."
            + "This is no functionality to the end-user.\n"
            + "Parameters: VALUE t/ [TYPE] (may be 'echo' or 'error')\n"
            + "Example: " + COMMAND_WORD + " yada yada t/ echo";

    public enum Type {
        ECHO, ERROR;

        public static final String MESSAGE_CONSTRAINTS = "Type must either be 'echo' or 'error'";

        public static boolean isValidTypeFormat(String typeString) {
            return Objects.equals(typeString, "echo") || Objects.equals(typeString, "error");
        }

        public static Type getTypeFromString(String typeString) {
            switch (typeString) {
            case "echo":
                return Type.ECHO;
            case "error":
                return Type.ERROR;
            default:
                AppUtil.checkArgument(false, MESSAGE_CONSTRAINTS);
                return null; // This will never be executed, the above line throws an exception.
            }
        }
    }

    private final String value;
    private final Type type;

    public DemoCommand(String value, Type type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (type) {
        case ECHO:
            return new CommandResult(value);
        case ERROR:
            throw new CommandException(value);
        default:
            throw new CommandException(MESSAGE_USAGE);
        }
    }
}
