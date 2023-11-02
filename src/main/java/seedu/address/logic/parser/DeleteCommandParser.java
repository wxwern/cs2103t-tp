package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECURSIVE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteApplicationCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
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
                        DeleteCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new));

        if (!argumentMultimap.getValue(FLAG_APPLICATION).isEmpty()) {
            return handleDeleteApplication(argumentMultimap);
        }

        if (argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Object indexXorId = ParserUtil.parseIndexXorId(argumentMultimap.getPreamble());
        boolean isRecursive = argumentMultimap.getValue(FLAG_RECURSIVE).isPresent();

        if (indexXorId instanceof Index) {
            return DeleteCommand.selectIndex((Index) indexXorId, isRecursive);
        }

        if (indexXorId instanceof Id) {
            return DeleteCommand.selectId((Id) indexXorId, isRecursive);
        }

        assert false : "If indexXorId is neither Index nor Id, then ParserUtil should've thrown ParseException!";
        throw new IllegalStateException();
    }

    private static DeleteCommand handleDeleteApplication(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(FLAG_APPLICATION).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(FLAG_APPLICATION).get());

        return new DeleteApplicationCommand(index);
    }

}
