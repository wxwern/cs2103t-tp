package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORG;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_POSITION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECRUITER;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddOrganizationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args,
                FLAG_NAME, FLAG_PHONE, FLAG_EMAIL,
                FLAG_ADDRESS, FLAG_TAG, FLAG_URL,
                FLAG_ID, FLAG_STATUS, FLAG_POSITION,
                FLAG_ORG, FLAG_RECRUITER);

        if (!areFlagsPresent(argMultimap, FLAG_NAME, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_ID, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS);

        if (areFlagsPresent(argMultimap, FLAG_ORG)) {
            Organization organization = parseAsOrganization(argMultimap);
            return new AddOrganizationCommand(organization);
        }

        // Deprecated contact format. Will be removed in future versions.
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());
        Id id;
        try {
            id = ParserUtil.parseId(argMultimap.getValue(FLAG_ID).get());
        } catch (NoSuchElementException e) {
            id = new Id();
        }
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(FLAG_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(FLAG_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(FLAG_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));

        Contact contact = new Contact(name, id, phone, email, address, tagList);

        return new AddCommand(contact);
    }

    private Organization parseAsOrganization(ArgumentMultimap argMultimap) throws ParseException {
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(FLAG_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(FLAG_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(FLAG_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());
        Status status;
        Position position;
        Id id;
        try {
            id = ParserUtil.parseId(argMultimap.getValue(FLAG_ID).get());
        } catch (NoSuchElementException e) {
            id = new Id();
        }
        try {
            position = ParserUtil.parsePosition(argMultimap.getValue(FLAG_POSITION).get());
        } catch (NoSuchElementException e) {
            position = new Position();
        }
        try {
            status = ParserUtil.parseStatus(argMultimap.getValue(FLAG_STATUS).get());
        } catch (NoSuchElementException e) {
            status = new Status();
        }

        return new Organization(name, id, phone, email, address, tagList, status, position);
    }

    /**
     * Returns true if none of the flags contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areFlagsPresent(ArgumentMultimap argumentMultimap, Flag... flags) {
        return Stream.of(flags).allMatch(flag -> argumentMultimap.getValue(flag).isPresent());
    }

}
