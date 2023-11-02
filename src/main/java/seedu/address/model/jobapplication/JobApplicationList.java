package seedu.address.model.jobapplication;


import javafx.collections.ObservableList;

/**
 * A wrapper for ObservableList of {@link JobApplication}
 */
public class JobApplicationList {

    private ObservableList<JobApplication> applications;

    /**
     * Creates a wrapper for the observable list.
     */
    public JobApplicationList(ObservableList<JobApplication> applications) {
        this.applications = applications;
    }

    /**
     * Gives the list of applications.
     */
    public ObservableList<JobApplication> getApplications() {
        return applications;
    }

    /**
     * Removes application from the list.
     */
    public void remove(JobApplication application) {
        applications.remove(application);
    }

    /**
     * Adds application to list.
     */
    public void add(JobApplication jobApplication) {
        applications.add(jobApplication);
    }
}
