package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.LastUpdatedTime;

public class EditApplicationCommand extends Command {

    private final Index targetIndex;
    private final EditApplicationDescriptor editApplicationDescriptor;

    public EditApplicationCommand(Index index, EditApplicationDescriptor editApplicationDescriptor) {
        this.targetIndex = index;
        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor)
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    public static class EditApplicationDescriptor {
        private Id oid;
        private Name orgName;

        private JobTitle jobTitle;

        private JobDescription jobDescription;

        private LastUpdatedTime lastUpdatedTime = new LastUpdatedTime();

        private Deadline deadline;

        private JobStatus status;

        private ApplicationStage applicationStage;

        public EditApplicationDescriptor() {}

        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setDeadline(toCopy.deadline);
            setApplicationStage(toCopy.applicationStage);
            setJobDescription(toCopy.jobDescription);
            setJobTitle(toCopy.jobTitle);
            setOrgName(toCopy.orgName);
            setOid(toCopy.oid);
            setLastUpdatedTime(toCopy.lastUpdatedTime);
            setStatus(toCopy.status);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(oid, orgName, deadline, applicationStage, jobDescription, jobTitle,
                    status);
        }

        public void setOid(Id oid) {
            this.oid = oid;
        }

        public Optional<Id> getOid() {
            return Optional.ofNullable(oid);
        }

        public void setStatus(JobStatus status) {
            this.status = status;
        }

        public Optional<JobStatus> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setOrgName(Name name) {
            this.orgName = name;
        }

        public Optional<Name> getOrgName() {
            return Optional.ofNullable(orgName);
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

        private void setLastUpdatedTime(LastUpdatedTime lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public Optional<LastUpdatedTime> getLastUpdatedTime() {
            return Optional.ofNullable(lastUpdatedTime);
        }


        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("orgname", orgName)
                    .add("jobTitle", jobTitle)
                    .add("description", jobDescription)
                    .add("status", status)
                    .add("stage", applicationStage)
                    .toString();
        }
    }
}
