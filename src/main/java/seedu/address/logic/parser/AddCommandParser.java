package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECRUITER;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;

import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddOrganizationCommand;
import seedu.address.logic.commands.AddRecruiterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Url;
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
                        AddCommand.AUTOCOMPLETE_SUPPLIER.getAllPossibleFlags().toArray(Flag[]::new)
                );

        if (!argMultimap.hasAllOfFlags(FLAG_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_ID, FLAG_PHONE, FLAG_EMAIL, FLAG_URL, FLAG_ADDRESS);

        argMultimap.verifyAtMostOneOfFlagsUsedOutOf(FLAG_ORGANIZATION, FLAG_RECRUITER);

        if (argMultimap.hasFlag(FLAG_ORGANIZATION)) {
            return parseAsOrganization(argMultimap);
        } else if (argMultimap.hasFlag(FLAG_RECRUITER)) {
            return parseAsRecruiter(argMultimap);
        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private AddRecruiterCommand parseAsRecruiter(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicateFlagsFor(FLAG_ORGANIZATION_ID);
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());

        Optional<String> idString = argMultimap.getValue(FLAG_ID);
        Id id = idString.isPresent()
                ? ParserUtil.parseId(idString.get())
                : Id.synthesizeFrom(name.fullName);

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

        return new AddRecruiterCommand(name, id, phone, email, url, address, tagList, oid);
    }

    private AddOrganizationCommand parseAsOrganization(ArgumentMultimap argMultimap) throws ParseException {
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());

        Optional<String> idString = argMultimap.getValue(FLAG_ID);
        Id id = idString.isPresent()
                ? ParserUtil.parseId(idString.get())
                : Id.synthesizeFrom(name.fullName);

        Phone phone = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_PHONE), ParserUtil::parsePhone);
        Email email = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_EMAIL), ParserUtil::parseEmail);
        Address address = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_ADDRESS), ParserUtil::parseAddress);
        Url url = ParserUtil.parseOptionally(
                argMultimap.getValue(FLAG_URL), ParserUtil::parseUrl);
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));
        Set<Id> ridList = Set.of(); // TODO: This should be dynamically determined from oid in Recruiter.

        return new AddOrganizationCommand(name, id, phone, email, url, address, tagList, null, null, ridList);
    }
}
