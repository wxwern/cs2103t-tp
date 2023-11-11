package seedu.address.logic.commands;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.LastUpdatedTime;

/**
 * Edit command but for application.
 */
public class EditApplicationCommand extends EditCommand {

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited job application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Index targetIndex;
    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * Creates a command based on the index of the application in the list and the description given.
     * @param index of the application in the list
     * @param editApplicationDescriptor of the application to edit
     */
    public EditApplicationCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        super(index, new EditContactDescriptor()); // dummy things to make the compiler happy.
        this.targetIndex = index;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!editApplicationDescriptor.isAnyFieldEdited()) {
            throw new CommandException(MESSAGE_NOT_EDITED);
        }
        List<JobApplication> lastShownList = model.getDisplayedApplicationList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }

        JobApplication jobApplication = lastShownList.get(targetIndex.getZeroBased());
        JobApplication newApplication = createApplication(jobApplication, editApplicationDescriptor);

        try {
            model.replaceApplication(jobApplication, newApplication);
        } catch (IllegalValueException e) {
            throw new CommandException(e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, newApplication));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EditApplicationCommand)) {
            return false;
        }
        EditApplicationCommand cmd = (EditApplicationCommand) other;
        return this.targetIndex.equals(cmd.targetIndex)
                && this.editApplicationDescriptor.equals(cmd.editApplicationDescriptor);
    }

    private static JobApplication createApplication(JobApplication jobApplication,
                                                    EditApplicationDescriptor editApplicationDescriptor) {
        Objects.requireNonNull(jobApplication);
        Objects.requireNonNull(editApplicationDescriptor);

        Id oid = jobApplication.getOrganizationId();
        Name name = jobApplication.getOrgName();
        JobTitle jobTitle = editApplicationDescriptor.getTitle().orElse(jobApplication.getJobTitle());
        JobDescription jobDescription =
                editApplicationDescriptor.getDescription().orElse(jobApplication.getJobDescription()
                .orElse(null));
        JobStatus status = editApplicationDescriptor.getStatus().orElse(jobApplication.getStatus());
        ApplicationStage stage = editApplicationDescriptor.getStage().orElse(jobApplication.getApplicationStage());
        Deadline deadline = editApplicationDescriptor.getDeadline().orElse(jobApplication.getDeadline());

        return new JobApplication(oid, name, jobTitle, jobDescription, deadline, status, stage, new LastUpdatedTime());
    }

    /**
     * Class to store information on details to edit.
     */
    public static class EditApplicationDescriptor {


        private JobTitle jobTitle;

        private JobDescription jobDescription;

        private Deadline deadline;

        private JobStatus status;

        private ApplicationStage applicationStage;

        public EditApplicationDescriptor() {}

        /**
         * Creates a shallow copy of {@code EditApplicationDescriptor}
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setDeadline(toCopy.deadline);
            setApplicationStage(toCopy.applicationStage);
            setJobDescription(toCopy.jobDescription);
            setJobTitle(toCopy.jobTitle);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(deadline, applicationStage, jobDescription, jobTitle,
                    status);
        }

        public void setStatus(JobStatus status) {
            this.status = status;
        }

        public Optional<JobStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setJobTitle(JobTitle title) {
            this.jobTitle = title;
        }

        public Optional<JobTitle> getTitle() {
            return Optional.ofNullable(jobTitle);
        }

        public void setJobDescription(JobDescription description) {
            this.jobDescription = description;
        }

        public Optional<JobDescription> getDescription() {
            return Optional.ofNullable(jobDescription);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setApplicationStage(ApplicationStage applicationStage) {
            this.applicationStage = applicationStage;
        }

        public Optional<ApplicationStage> getStage() {
            return Optional.ofNullable(applicationStage);
        }


        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("jobTitle", jobTitle)
                    .add("description", jobDescription)
                    .add("status", status)
                    .add("stage", applicationStage)
                    .toString();
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            EditApplicationDescriptor editDescriptor = (EditApplicationDescriptor) other;
            return this.jobDescription.equals(editDescriptor.jobDescription)
                    && this.applicationStage.equals(editDescriptor.applicationStage)
                    && this.deadline.equals(editDescriptor.deadline)
                    && this.jobTitle.equals(editDescriptor.jobTitle)
                    && this.status.equals(editDescriptor.status);
        }
    }
}
