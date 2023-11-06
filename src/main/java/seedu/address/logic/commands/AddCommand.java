package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
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

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteConstraint;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Type;
import seedu.address.model.contact.Url;
import seedu.address.model.tag.Tag;

/**
 * Adds a contact to the address book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteDataSet.oneAmongAllOf(
                    FLAG_ORGANIZATION, FLAG_RECRUITER
            ).addDependents(
                    AutocompleteDataSet.onceForEachOf(
                            FLAG_NAME, FLAG_ID,
                            FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_URL,
                            FLAG_ORGANIZATION_ID
                    ),
                    AutocompleteDataSet.anyNumberOf(FLAG_TAG)
            ).addConstraints(List.of(
                    AutocompleteConstraint.where(FLAG_RECRUITER)
                            .isPrerequisiteFor(FLAG_ORGANIZATION_ID)
            ))
    ).configureValueMap(map -> {
        // Add value autocompletion for:
        map.put(FLAG_ORGANIZATION_ID, (command, model) -> model.getAddressBook()
                .getContactList()
                .stream()
                .filter(c -> c.getType() == Type.ORGANIZATION)
                .map(o -> o.getId().value)
        );

        // Disable value autocompletion for:
        map.put(null /* preamble */, null);
        map.put(FLAG_ORGANIZATION, null);
        map.put(FLAG_RECRUITER, null);
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
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + FLAG_ORGANIZATION + " "
            + FLAG_NAME + " JobsInc "
            + FLAG_ID + " id_12345-1 "
            + FLAG_PHONE + " 98765432 "
            + FLAG_EMAIL + " jobsInc@example.com "
            + FLAG_URL + " www.jobsinc.com "
            + FLAG_ADDRESS + " 311, Clementi Ave 2, #02-25 "
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

    public static final String MESSAGE_SUCCESS = "New %s added: %s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book";

    private static final Logger logger = LogsCenter.getLogger(AddCommand.class);

    // Identity fields
    protected final Name name;
    protected final Id id;
    protected final Phone phone;
    protected final Email email;

    // Data fields
    protected final Url url;
    protected final Address address;
    protected final Set<Tag> tags = new HashSet<>();

    /**
     * Creates an AddCommand to add a {@code Contact} to the address book with the given parameters.
     *
     * <p>
     * TODO: This class should be made abstract.
     * </p>
     */
    @Deprecated
    public AddCommand(Name name, Id id, Phone phone, Email email, Url url, Address address, Set<Tag> tags) {
        requireAllNonNull(name, id, tags);
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
        this.tags.addAll(tags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Contact toAdd = createContact();
        logger.fine(String.format("Adding contact: %s", toAdd));

        requireNonNull(model);

        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        model.addContact(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getType(), Messages.format(toAdd)));
    }

    protected abstract Contact createContact();

    @Override
    public abstract boolean equals(Object other);

    protected ToStringBuilder toStringBuilder() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("id", id)
                .add("phone", phone)
                .add("email", email)
                .add("url", url)
                .add("address", address)
                .add("tags", tags);
    }

    @Override
    public String toString() {
        return toStringBuilder().toString();
    }
}
