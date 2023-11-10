package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalOperationException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Type;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobApplicationList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> displayedContacts;
    private final FilteredList<Contact> filteredContacts;
    private final SortedList<Contact> sortedContacts;
    private final JobApplicationList jobApplicationList;
    private final SortedList<JobApplication> sortedApplications;
    private final FilteredList<JobApplication> filteredApplications;
    private final FilteredList<JobApplication> displayedApplications;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.sortedContacts = new SortedList<>(this.addressBook.getContactList());
        this.filteredContacts = new FilteredList<>(sortedContacts);
        this.displayedContacts = filteredContacts;
        ObservableList<JobApplication> applicationList =
                FXCollections.observableArrayList(extractApplicationsFromContacts(filteredContacts));
        this.jobApplicationList = new JobApplicationList(applicationList);
        this.sortedApplications = new SortedList<>(applicationList);
        this.filteredApplications = new FilteredList<>(this.sortedApplications, s->true);
        this.displayedApplications = filteredApplications;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        this.jobApplicationList.setAll(extractApplicationsFromContacts(addressBook.getContactList()));
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
        if (target.getType() == Type.ORGANIZATION) {
            for (JobApplication i: ((Organization) target).getJobApplications()) {
                jobApplicationList.remove(i);
            }
        }
    }

    @Override
    public void addContact(Contact contact) {
        addressBook.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);

        if (target.getType() != Type.ORGANIZATION || editedContact.getType() != Type.ORGANIZATION) {
            // guard clause
            return;
        }
        if (target.getName().equals(editedContact.getName()) && target.getId().equals(editedContact.getId())) {
            // guard clause
            return;
        }

        updateApplicationNames((Organization) editedContact, (Organization) target);

    }

    @Override
    public Contact getContactById(Id id) {
        return addressBook.getContactById(id);
    }

    @Override
    public Contact getContactByIdXorIndex(Id id, Index index) throws IllegalValueException {
        Contact contact;
        if (id == null && index == null) {
            throw new IllegalValueException("No contact specified");
        }
        if (id != null && index != null) {
            throw new IllegalValueException(
                    Messages.MESSAGE_SIMULTANEOUS_USE_DISALLOWED_FIELDS + "INDEX, ID");
        }
        if (id != null) {
            contact = getContactById(id);
        } else { // else index is not null instead

            List<Contact> lastShownList = this.getDisplayedContactList();
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new IllegalValueException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
            }
            contact = lastShownList.get(index.getZeroBased());
        }

        if (contact == null) {
            throw new IllegalValueException(Messages.MESSAGE_NO_SUCH_CONTACT);
        }
        return contact;
    }

    @Override
    public void replaceApplication(JobApplication oldApplication,
                                   JobApplication newApplication) throws IllegalValueException {

        Contact contact = getContactById(newApplication.getOrganizationId());
        if (contact == null || contact.getType() != Type.ORGANIZATION) {
            throw new IllegalValueException("Id field is invalid!"); // TODO: Should I change this?
        }

        int index = jobApplicationList.indexOf(oldApplication);

        if (index < 0) {
            throw new IllegalValueException("Job application does not exist."); // should never reach here.
        }

        Organization organization = (Organization) contact;
        try {
            organization.replaceJobApplication(oldApplication, newApplication);
        } catch (IllegalOperationException e) {
            throw new IllegalValueException(e.getMessage());
        }

        jobApplicationList.set(index, newApplication);

    }

    @Override
    public void deleteApplication(JobApplication application) throws IllegalValueException {
        Contact contact = getContactById(application.getOrganizationId());
        if (contact == null || contact.getType() != Type.ORGANIZATION) {
            throw new IllegalValueException("Id field is invalid!");
        }
        Organization org = (Organization) contact;
        this.jobApplicationList.remove(application);
        org.deleteJobApplication(application);
    }

    @Override
    public void addApplication(JobApplication application) {
        jobApplicationList.add(application);
        // TODO: Tech debt - need separate declaration for the predicates
        filteredApplications.setPredicate(c -> true);
    }

    //=========== Filtered Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getDisplayedContactList() {
        return this.displayedContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
        // TODO: Tech debt - inefficient?
        filteredApplications.setPredicate(a -> predicate.test(getContactById(a.getOrganizationId())));
    }

    @Override
    public void updateSortedContactList(Comparator<Contact> comparator) {
        this.sortedContacts.setComparator(comparator);
    }

    @Override
    public ObservableList<JobApplication> getDisplayedApplicationList() {
        return displayedApplications;
    }

    @Override
    public void updateSortedApplicationList(Comparator<JobApplication> comparator) {
        sortedApplications.setComparator(comparator);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredContacts.equals(otherModelManager.filteredContacts);
    }

    private void updateApplicationNames(Organization newOrg, Organization oldOrg) {
        List<JobApplication> oldApplications = List.of(oldOrg.getJobApplications());
        List<JobApplication> newApplications = List.of(newOrg.getJobApplications());

        for (JobApplication newApplication: newApplications) {
            for (JobApplication oldApplication: oldApplications) {
                if (!newApplication.looseEquals(oldApplication)) {
                    continue;
                }
                int index = jobApplicationList.indexOf(oldApplication);
                jobApplicationList.set(index, newApplication);
            }
        }
    }

    private List<JobApplication> extractApplicationsFromContacts(List<Contact> contacts) {
        return contacts.stream()
                .filter(c -> c.getType() == Type.ORGANIZATION)
                .flatMap(c -> Arrays.stream(((Organization) c).getJobApplications()))
                .sorted(JobApplication.LAST_UPDATED_COMPARATOR)
                .collect(Collectors.toList());
    }

}
