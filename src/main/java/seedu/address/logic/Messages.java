package seedu.address.logic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Flag;
import seedu.address.model.contact.Contact;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_NO_SUCH_CONTACT = "No such contact";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
            "Multiple values specified for the following single-valued option(s): ";
    public static final String MESSAGE_EXTRA_FIELDS =
            "Extra irrelevant options found in the command: ";
    public static final String MESSAGE_UNEXPECTED_NON_EMPTY_FIELDS =
            "The following options may not have any value: ";
    public static final String MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS =
            "The following options conflict and cannot be set together: ";

    public static final String MESSAGE_INVALID_FIELD =
            "The term '%s' is not a valid option!";

    /**
     * Returns an error message indicating the duplicate flags.
     */
    public static String getErrorMessageForDuplicateFlags(Flag... duplicateFlags) {
        assert duplicateFlags.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicateFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(", ", duplicateFields);
    }

    /**
     * Returns an error message indicating the extraneous flags.
     */
    public static String getErrorMessageForExtraneousFlags(Flag... extraneousFlags) {
        assert extraneousFlags.length > 0;

        Set<String> extraneousFields =
                Stream.of(extraneousFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_EXTRA_FIELDS + String.join(", ", extraneousFields);
    }

    /**
     * Returns an error message indicating the flags have unexpected values.
     */
    public static String getErrorMessageForNonEmptyValuedFlags(Flag... nonEmptyValuedFlags) {
        assert nonEmptyValuedFlags.length > 0;

        Set<String> nonEmptyValuedFields =
                Stream.of(nonEmptyValuedFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_UNEXPECTED_NON_EMPTY_FIELDS + String.join(", ", nonEmptyValuedFields);
    }

    /**
     * Returns an error message indicating the flags should not exist in the same command together.
     */
    public static String getErrorMessageForSimultaneousUseDisallowedFlags(Flag... conflictingFlags) {
        assert conflictingFlags.length > 1;

        Set<String> conflictingFields =
                Stream.of(conflictingFlags).map(Flag::toString).collect(Collectors.toSet());

        return MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS + String.join(", ", conflictingFields);
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
                .append(contact.getPhone().map(p -> p.value).orElse("(none)"))
                .append("; Email: ")
                .append(contact.getEmail().map(e -> e.value).orElse("(none)"))
                .append("; Url: ")
                .append(contact.getUrl().map(u -> u.value).orElse("(none)"))
                .append("; Address: ")
                .append(contact.getAddress().map(a -> a.value).orElse("(none)"))
                .append("; Tags: ");
        contact.getTags().forEach(builder::append);
        return builder.toString();
    }

    public static String formatChildren(List<Contact> childrenContacts) {
        return childrenContacts.stream()
                .map(c -> Messages.format(c) + "\n")
                .reduce((c1, c2) -> c1 + c2)
                .orElse("No other contacts found");
    }

}
