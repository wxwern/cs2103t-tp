package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECURSIVE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Id;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        FLAG_ID, FLAG_RECURSIVE);

        boolean hasIndex = !argumentMultimap.getPreamble().isEmpty();
        boolean hasId = argumentMultimap.getValue(FLAG_ID).isPresent();
        boolean isRecursive = argumentMultimap.getValue(FLAG_RECURSIVE).isPresent();

        if (hasIndex) {
            return parseDeleteIndexCommand(argumentMultimap.getPreamble(), isRecursive);
        }

        if (hasId) {
            return parseDeleteIdCommand(argumentMultimap.getValue(FLAG_ID).get(), isRecursive);
        }
        throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    private static DeleteCommand parseDeleteIdCommand(String idString, boolean isRecursive) throws ParseException {
        Id id;
        try {
            id = ParserUtil.parseId(idString);
            return DeleteCommand.selectId(id, isRecursive);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    private static DeleteCommand parseDeleteIndexCommand(String indexStr, boolean isRecursive) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(indexStr);
            return DeleteCommand.selectIndex(index, isRecursive);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
