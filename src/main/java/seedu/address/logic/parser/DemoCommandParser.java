package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.ParserUtil.parseDemoCommandType;

import java.util.Optional;

import seedu.address.logic.commands.DemoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DemoCommandParser implements Parser<DemoCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DemoCommand
     * and returns a DemoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DemoCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TYPE);

        String value = argMultimap.getPreamble();
        Optional<String> typeStr = argMultimap.getValue(PREFIX_TYPE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TYPE);
        if (!typeStr.isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DemoCommand.MESSAGE_USAGE));
        }

        DemoCommand.Type type = parseDemoCommandType(typeStr.get());

        return new DemoCommand(value, type);
    }

}
