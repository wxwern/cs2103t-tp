package seedu.address.model.jobapplication;


import java.util.Collection;

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

    /**
     * Sets application at the specified index.
     */
    public void set(int index, JobApplication jobApplication) {
        applications.set(index, jobApplication);
    }

    /**
     * Gets the application at the specified index.
     */
    public JobApplication get(int index) {
        return applications.get(index);
    }

    /**
     * Gets the index of the application in the list. Returns -1 if not found.
     */
    public int indexOf(JobApplication application) {
        return applications.indexOf(application);
    }

    /**
     * Replaces all the applications in the list with the new applications
     */
    public void setAll(Collection<JobApplication> applications) {
        this.applications.setAll(applications);
    }
}
