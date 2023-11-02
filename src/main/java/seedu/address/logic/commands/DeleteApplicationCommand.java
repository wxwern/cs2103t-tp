package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.FLAG_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_RECURSIVE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.jobapplication.JobApplication;

/**
 * Delete command but for application.
 */
public class DeleteApplicationCommand extends DeleteCommand {

    public static final String MESSAGE_DELETE_APPLICATION_SUCCESS = "Deleted application: %1$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the job application identified by the index number used in the displayed application list.\n"
            + "Parameters: "
            + FLAG_APPLICATION + " INDEX "
            + "\n"
            + "Example 1: " + COMMAND_WORD + " --application 1\n";

    private final Index index;

    /**
     * Creates new DeleteCommand with the index of the application.
     */
    public DeleteApplicationCommand(Index targetIndex) {
        super(targetIndex);
        this.index = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<JobApplication> applications = model.getDisplayedApplicationList();

        if (index.getZeroBased() >= applications.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }
        JobApplication application = applications.get(index.getZeroBased());
        try {
            model.deleteApplication(application);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }
        return new CommandResult(String.format(MESSAGE_DELETE_APPLICATION_SUCCESS, application));
    }
}
