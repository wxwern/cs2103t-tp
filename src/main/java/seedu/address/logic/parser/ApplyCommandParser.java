package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TITLE;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.ApplyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Id;


/**
 * Parses input arguments and creates a new ApplyCommand object
 */
public class ApplyCommandParser implements Parser<ApplyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ApplyCommand and returns an ApplyCommand
     * object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ApplyCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args,
                        ApplyCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new)
                );

        Object indexXorId = ParserUtil.parseIndexXorId(argumentMultimap.getPreamble());

        Optional<String> title = argumentMultimap.getValue(FLAG_TITLE);
        Optional<String> description = argumentMultimap.getValue(FLAG_DESCRIPTION);
        Optional<String> stage = argumentMultimap.getValue(FLAG_STAGE);
        Optional<String> status = argumentMultimap.getValue(FLAG_STATUS);
        Optional<String> deadline = argumentMultimap.getValue(FLAG_DEADLINE);

        if (title.isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ApplyCommand.MESSAGE_USAGE));
        }
        // TODO: Tech debt - Use Parserutil.parseOptionally
        return new ApplyCommand(
                indexXorId instanceof Id ? (Id) indexXorId : null,
                indexXorId instanceof Index ? (Index) indexXorId : null,
                ParserUtil.parseJobTitle(title.get()),
                description.isPresent() ? ParserUtil.parseJobDescription(description.get()) : null,
                deadline.isPresent() ? ParserUtil.parseDeadline(deadline.get()) : null,
                status.isPresent() ? ParserUtil.parseJobStatus(status.get()) : null,
                stage.isPresent() ? ParserUtil.parseApplicationStage(stage.get()) : null
            );
    }

}
