package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TITLE;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Type;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;

/**
 * Adds a job application to the address book.
 */
public class ApplyCommand extends Command {

    //TODO: Make the command msg consistent with the add command format - use [] for optionals
    public static final String COMMAND_WORD = "apply";

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AutocompleteDataSet.onceForEachOf(
                    FLAG_TITLE, FLAG_DESCRIPTION,
                    FLAG_DEADLINE, FLAG_STAGE, FLAG_STATUS
            )
    ).configureValueMap(map -> {
        // Add value autocompletion data for:
        map.put(null /* preamble */, (command, model) -> {

            String partialText = command.getAutocompletableText();
            if (partialText.isEmpty() || StringUtil.isNonZeroUnsignedInteger(partialText)) {
                // Preamble is likely of type Index
                return Stream.empty();

            } else {
                // Preamble is likely of type Id
                // * Constraint: Applications must be towards organizations
                return model.getAddressBook()
                        .getContactList()
                        .stream()
                        .filter(c -> c.getType() == Type.ORGANIZATION)
                        .map(o -> o.getId().value);
            }
        });

        map.put(FLAG_STAGE, (command, model)
                -> Arrays.stream(ApplicationStage.values())
                .map(ApplicationStage::toString));

        map.put(FLAG_STATUS, (command, model)
                -> Arrays.stream(JobStatus.values())
                .map(JobStatus::toString));
    });

    public static final String MESSAGE_USAGE = "Adds a new job application.\n"
            + "Parameters: "
            + "INDEX/ID "
            + FLAG_TITLE + " TITLE "
            + "[" + FLAG_DESCRIPTION + " DESCRIPTION] "
            + "[" + FLAG_DEADLINE + " DEADLINE: DD-MM-YYYY] "
            + "[" + FLAG_STAGE + " APPLICATION STAGE: resume/online assessment/interview] "
            + "[" + FLAG_STATUS + " STATUS: pending/offered/accepted/turned down] ";

    public static final String MESSAGE_APPLY_SUCCESS = "Added application: %1$s to %2$s";
    public static final String MESSAGE_ATTEMPT_TO_ADD_TO_NON_ORG = "Attempted to apply to a non-organization: %1$s";

    private static final Logger logger = LogsCenter.getLogger(ApplyCommand.class);

    private final Id oid;

    private final Index index;

    private final JobTitle title;

    private final JobDescription description;

    private final Deadline deadline;

    private final JobStatus status;

    private final ApplicationStage applicationStage;

    /**
     * Creates an executable ApplyCommand to add {@code JobApplication}
     */
    public ApplyCommand(Id oid, Index index, JobTitle title, JobDescription description, Deadline deadline,
                        JobStatus status, ApplicationStage applicationStage) {
        this.oid = oid;
        this.index = index;
        this.title = title;
        this.description = description; // optional. No default value
        this.deadline = deadline; // optional with default value
        this.applicationStage = applicationStage; // optional with default value
        this.status = status; // optional with default value
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Organization org = getOrganization(index, oid, model);
        JobApplication ja = new JobApplication(org, title, description, deadline, status, applicationStage);
        org.addJobApplication(ja);
        model.addApplication(ja);
        return new CommandResult(String.format(MESSAGE_APPLY_SUCCESS, ja, org));
    }

    private static Organization getOrganization(Index index, Id id, Model model) throws CommandException {
        Contact org;
        try {
            org = model.getContactByIdXorIndex(id, index);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
        if (org.getType() != Type.ORGANIZATION) {
            throw new CommandException(String.format(MESSAGE_ATTEMPT_TO_ADD_TO_NON_ORG, org));
        }
        return (Organization) org;
    }
}
