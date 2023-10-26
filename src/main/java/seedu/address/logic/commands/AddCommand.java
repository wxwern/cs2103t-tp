package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteConstraint;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Type;

/**
 * Adds a contact to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteDataSet.oneAmongAllOf(
                    FLAG_ORGANIZATION, FLAG_RECRUITER
            ).addDependents(
                    AutocompleteDataSet.onceForEachOf(
                            FLAG_NAME, FLAG_ID,
                            FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_URL,
                            FLAG_STATUS, FLAG_POSITION,
                            FLAG_ORGANIZATION_ID
                    ),
                    AutocompleteDataSet.anyNumberOf(FLAG_TAG)
            ).addConstraints(List.of(
                    AutocompleteConstraint.where(FLAG_ORGANIZATION)
                            .isPrerequisiteFor(FLAG_STATUS, FLAG_POSITION),
                    AutocompleteConstraint.where(FLAG_RECRUITER)
                            .isPrerequisiteFor(FLAG_ORGANIZATION_ID)
            ))
    ).configureValueMap(m -> {
        // Add value autocompletion for:
        m.put(FLAG_ORGANIZATION_ID,
                model -> model.getAddressBook().getContactList().stream()
                        .filter(c -> c.getType() == Type.ORGANIZATION)
                        .map(o -> o.getId().value)
        );

        // Disable value autocompletion for:
        m.put(null /* preamble */, null);
        m.put(FLAG_ORGANIZATION, null);
        m.put(FLAG_RECRUITER, null);
    });


    public static final String MESSAGE_ORGANIZATION_USAGE = "Adds an organization. "
            + "Parameters: "
            + FLAG_ORGANIZATION + " "
            + FLAG_NAME + " NAME "
            + "[" + FLAG_ID + " ID] "
            + "[" + FLAG_PHONE + " PHONE] "
            + "[" + FLAG_EMAIL + " EMAIL] "
            + "[" + FLAG_URL + " URL] "
            + "[" + FLAG_ADDRESS + " ADDRESS] "
            + "[" + FLAG_STATUS + " STATUS] "
            + "[" + FLAG_POSITION + " POSITION] "
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + FLAG_ORGANIZATION + " "
            + FLAG_NAME + " JobsInc "
            + FLAG_ID + " id_12345-1 "
            + FLAG_PHONE + " 98765432 "
            + FLAG_EMAIL + " jobsInc@example.com "
            + FLAG_URL + " www.jobsinc.com "
            + FLAG_ADDRESS + " 311, Clementi Ave 2, #02-25 "
            + FLAG_STATUS + " applied "
            + FLAG_POSITION + " Junior Software Engineer "
            + FLAG_TAG + " softwareEngineering "
            + FLAG_TAG + " competitive ";

    public static final String MESSAGE_RECRUITER_USAGE = "Adds a recruiter. "
            + "Parameters: "
            + FLAG_RECRUITER + " "
            + FLAG_NAME + " NAME "
            + "[" + FLAG_ID + " ID] "
            + "[" + FLAG_ORGANIZATION_ID + " ORG_ID] "
            + "[" + FLAG_PHONE + " PHONE] "
            + "[" + FLAG_EMAIL + " EMAIL] "
            + "[" + FLAG_URL + " URL] "
            + "[" + FLAG_ADDRESS + " ADDRESS] "
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + FLAG_RECRUITER + " "
            + FLAG_NAME + " Steve "
            + FLAG_ID + " id_98765-1 "
            + FLAG_PHONE + " 83452145 "
            + FLAG_EMAIL + " steveJobsInc@example.com "
            + FLAG_URL + " www.linkedin.com/in/steve/ "
            + FLAG_ADDRESS + " 311 W Coast Walk, #02-30 "
            + FLAG_TAG + " friendly ";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a contact to the address book of the class type Organization or Recruiter."
            + " The input format varies depending on the class:\n\n"
            + MESSAGE_ORGANIZATION_USAGE + "\n\n"
            + MESSAGE_RECRUITER_USAGE;

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    private final Contact toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Contact}
     */
    public AddCommand(Contact contact) {
        requireNonNull(contact);
        toAdd = contact;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.fine(String.format("Adding contact: %s", toAdd));

        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
