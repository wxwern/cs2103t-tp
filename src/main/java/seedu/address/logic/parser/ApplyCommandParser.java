package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TITLE;

import java.util.Optional;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ApplyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
                        FLAG_ID, FLAG_TITLE, FLAG_STAGE,
                        FLAG_DEADLINE, FLAG_TITLE, FLAG_DESCRIPTION,
                        FLAG_STATUS
                );

        boolean hasIndex = !argumentMultimap.getPreamble().isEmpty();
        boolean hasId = argumentMultimap.getValue(FLAG_ID).isPresent();

        String id = argumentMultimap.getValue(FLAG_ID).orElse(null);
        String index = hasIndex ? argumentMultimap.getPreamble() : null;
        Optional<String> title = argumentMultimap.getValue(FLAG_TITLE);
        Optional<String> description = argumentMultimap.getValue(FLAG_DESCRIPTION);
        Optional<String> stage = argumentMultimap.getValue(FLAG_STAGE);
        Optional<String> status = argumentMultimap.getValue(FLAG_STATUS);
        Optional<String> deadline = argumentMultimap.getValue(FLAG_DEADLINE);


        if (hasId && hasIndex) {
            throw new ParseException(
                    String.format(Messages.MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS + "Index, Id")
            );
        }

        if (!hasId && !hasIndex) {
            throw new ParseException(Messages.MESSAGE_INVALID_COMMAND_FORMAT + ApplyCommand.MESSAGE_USAGE);
        }

        if (title.isEmpty()) {
            throw new ParseException(Messages.MESSAGE_INVALID_COMMAND_FORMAT + ApplyCommand.MESSAGE_USAGE);
        }
        return new ApplyCommand(
                hasId ? ParserUtil.parseId(id) : null,
                hasIndex ? ParserUtil.parseIndex(index) : null,
                ParserUtil.parseJobTitle(title.get()),
                description.isPresent() ? ParserUtil.parseJobDescription(description.get()) : null,
                deadline.isPresent() ? ParserUtil.parseDeadline(deadline.get()) : null,
                status.isPresent() ? ParserUtil.parseJobStatus(status.get()) : null,
                stage.isPresent() ? ParserUtil.parseApplicationStage(stage.get()) : null
            );
    }

}
