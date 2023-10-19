package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_POSITION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECRUITER;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;

import java.util.Optional;
import java.util.Set;

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
import seedu.address.model.person.Recruiter;
import seedu.address.model.person.Status;
import seedu.address.model.person.Url;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        FLAG_NAME, FLAG_PHONE, FLAG_EMAIL,
                        FLAG_ADDRESS, FLAG_TAG, FLAG_URL,
                        FLAG_ID, FLAG_STATUS, FLAG_POSITION,
                        FLAG_ORGANIZATION_ID,
                        FLAG_ORGANIZATION, FLAG_RECRUITER
                );

        if (!argMultimap.hasAllOfFlags(FLAG_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_ID, FLAG_PHONE, FLAG_EMAIL, FLAG_URL, FLAG_ADDRESS);

        if (argMultimap.hasFlag(FLAG_ORGANIZATION)) {
            Organization organization = parseAsOrganization(argMultimap);
            return new AddOrganizationCommand(organization);
        } else if (argMultimap.hasFlag(FLAG_RECRUITER)) {
            Recruiter recruiter = parseAsRecruiter(argMultimap);
            return new AddCommand(recruiter);
        }

        // Deprecated contact format. Will be removed in future versions.
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());
        Optional<String> idString = argMultimap.getValue(FLAG_ID);
        Id id = idString.isPresent()
                ? ParserUtil.parseId(idString.get())
                : new Id();
        Phone phone = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_PHONE), ParserUtil::parsePhone);
        Email email = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_EMAIL), ParserUtil::parseEmail);
        Address address = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_ADDRESS), ParserUtil::parseAddress);
        Url url = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_URL), ParserUtil::parseUrl);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));

        Contact contact = new Contact(name, id, phone, email, url, address, tagList);

        return new AddCommand(contact);
    }

    private Recruiter parseAsRecruiter(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicateFlagsFor(FLAG_ORGANIZATION_ID);
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());

        Optional<String> idString = argMultimap.getValue(FLAG_ID);
        Id id = idString.isPresent()
                ? ParserUtil.parseId(idString.get())
                : new Id();

        Phone phone = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_PHONE), ParserUtil::parsePhone);
        Email email = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_EMAIL), ParserUtil::parseEmail);
        Address address = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_ADDRESS), ParserUtil::parseAddress);
        Url url = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_URL), ParserUtil::parseUrl);
        Id oid = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_ORGANIZATION_ID), ParserUtil::parseId);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));

        return new Recruiter(name, id, phone, email, url, address, tagList, oid);
    }

    private Organization parseAsOrganization(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicateFlagsFor(FLAG_POSITION, FLAG_STATUS);
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());

        Optional<String> idString = argMultimap.getValue(FLAG_ID);
        Id id = idString.isPresent()
                ? ParserUtil.parseId(idString.get())
                : new Id();

        Phone phone = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_PHONE), ParserUtil::parsePhone);
        Email email = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_EMAIL), ParserUtil::parseEmail);
        Address address = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_ADDRESS), ParserUtil::parseAddress);
        Url url = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_URL), ParserUtil::parseUrl);
        Position position = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_POSITION), ParserUtil::parsePosition);
        Status status = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_STATUS), ParserUtil::parseStatus);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));

        return new Organization(name, id, phone, email, url, address, tagList, status, position);
    }
}
