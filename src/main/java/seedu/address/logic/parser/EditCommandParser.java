package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_POSITION;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditContactDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Id;
import seedu.address.model.tag.Tag;





/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, FLAG_NAME, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_TAG,
                        FLAG_URL);

        Index index;
        Id targetId;

        try {
            String preambleStr = argMultimap.getPreamble();
            if (preambleStr.matches("^[A-Za-z].*")) {
                targetId = ParserUtil.parseId(preambleStr);
                index = null;
            } else {
                index = ParserUtil.parseIndex(preambleStr);
                targetId = null;
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_PHONE, FLAG_EMAIL,
                FLAG_ADDRESS, FLAG_URL, FLAG_ID, FLAG_STATUS, FLAG_POSITION, FLAG_ORGANIZATION_ID);

        EditContactDescriptor editContactDescriptor = new EditContactDescriptor();

        if (argMultimap.getValue(FLAG_NAME).isPresent()) {
            editContactDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get()));
        }
        if (argMultimap.getValue(FLAG_PHONE).isPresent()) {
            editContactDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(FLAG_PHONE).get()));
        }
        if (argMultimap.getValue(FLAG_EMAIL).isPresent()) {
            editContactDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(FLAG_EMAIL).get()));
        }
        if (argMultimap.getValue(FLAG_ADDRESS).isPresent()) {
            editContactDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(FLAG_ADDRESS).get()));
        }
        if (argMultimap.getValue(FLAG_URL).isPresent()) {
            editContactDescriptor.setUrl(ParserUtil.parseUrl(argMultimap.getValue(FLAG_URL).get()));
        }
        if (argMultimap.getValue(FLAG_ID).isPresent()) {
            editContactDescriptor.setId(ParserUtil.parseId(argMultimap.getValue(FLAG_ID).get()));
        }
        if (argMultimap.getValue(FLAG_STATUS).isPresent()) {
            editContactDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(FLAG_STATUS).get()));
        }
        if (argMultimap.getValue(FLAG_POSITION).isPresent()) {
            editContactDescriptor.setPosition(ParserUtil.parsePosition(argMultimap.getValue(FLAG_POSITION).get()));
        }
        if (argMultimap.getValue(FLAG_ORGANIZATION_ID).isPresent()) {
            editContactDescriptor.setOid(ParserUtil.parseId(argMultimap.getValue(FLAG_ORGANIZATION_ID).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(FLAG_TAG)).ifPresent(editContactDescriptor::setTags);

        if (!editContactDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        if (targetId == null) {
            return new EditCommand(index, editContactDescriptor);

        } else {
            return new EditCommand(targetId, editContactDescriptor);

        }

    }



    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
