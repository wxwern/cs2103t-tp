package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Recruiter;
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
                ArgumentTokenizer.tokenize(args, FLAG_NAME, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_TAG);


        if (!areFlagsPresent(argMultimap, FLAG_NAME, FLAG_ADDRESS, FLAG_PHONE, FLAG_EMAIL)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if ("recruiter".startsWith(argMultimap.getPreamble())) {
            Recruiter recruiter = parseRecruiter(argMultimap);
            return new AddCommand(recruiter);
        }

        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(FLAG_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(FLAG_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(FLAG_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));

        Contact contact = new Contact(name, phone, email, address, tagList);

        return new AddCommand(contact);
    }

    private Recruiter parseRecruiter(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicateFlagsFor(FLAG_NAME, FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS);
        Name name = ParserUtil.parseName(argMultimap.getValue(FLAG_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(FLAG_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(FLAG_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(FLAG_ADDRESS).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(FLAG_TAG));
        return new Recruiter(name, phone, email, address, tagList);
    }

    /**
     * Returns true if none of the flags contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areFlagsPresent(ArgumentMultimap argumentMultimap, Flag... flags) {
        return Stream.of(flags).allMatch(flag -> argumentMultimap.getValue(flag).isPresent());
    }

}
