package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Flag;
import seedu.address.model.person.Contact;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EXTRA_FIELDS =
            "Extra irrelevant flags found in the command: ";
    public static final String MESSAGE_INVALID_FIELD =
            "The term '%s' is not a valid flag!";

    /**
     * Returns an error message indicating the duplicate flags.
     */
    public static String getErrorMessageForDuplicateFlags(Flag... duplicateFlags) {
        assert duplicateFlags.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicateFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the extraneous flags.
     */
    public static String getErrorMessageForExtraneousFlags(Flag... extraneousFlags) {
        assert extraneousFlags.length > 0;

        Set<String> duplicateFields =
                Stream.of(extraneousFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_EXTRA_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Returns an error message indicating the invalid flag.
     */
    public static String getErrorMessageForInvalidFlagString(String flagString) {
        return String.format(
                MESSAGE_INVALID_FIELD, flagString
        );
    }

    /**
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contact.getName())
                .append("; Id: ")
                .append(contact.getId())
                .append("; Phone: ")
                .append(contact.getPhone())
                .append("; Email: ")
                .append(contact.getEmail())
                .append("; Url: ")
                .append(contact.getUrl())
                .append("; Address: ")
                .append(contact.getAddress())
                .append("; Tags: ");
        contact.getTags().forEach(builder::append);
        return builder.toString();
    }

}
