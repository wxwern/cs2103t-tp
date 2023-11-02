package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Url;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        Organization alexYeohInc = new Organization(
                new Name("Alex Yeoh Inc"),
                new Id("alex_yeoh_inc"), new Phone("87438807"),
                new Email("contact@alexyeoh.example.com"), new Url("alexyeoh.example.com"),
                null, getTagSet("parttime"), null, null, Set.of()
        );

        Organization google = new Organization(
                new Name("Google"), new Id("google"), new Phone("65218000"), null,
                new Url("careers.google.com"),
                new Address("70 Pasir Panjang Road, #03-71, "
                        + "Mapletree Business City, "
                        + "Singapore 117371"),
                getTagSet("bigtech", "internship", "competitive"),
                null, null, Set.of()
        );

        Organization jobSeekerPlus = new Organization(
                new Name("Job Seeker Plus"), new Id("job_seeker_plus"), new Phone("93210283"),
                new Email("jobseekerplus@example.com"), null,
                new Address("Blk 16 Real Street 128, #08-04"),
                getTagSet("startup", "internship"), null, null, Set.of()
        );

        alexYeohInc.addJobApplication(new JobApplication(alexYeohInc, new JobTitle("AI Engineer"),
                null, new Deadline(LocalDate.now().plusDays(42)),
                JobStatus.PENDING, ApplicationStage.RESUME));
        alexYeohInc.addJobApplication(new JobApplication(alexYeohInc, new JobTitle("Marketing Advisor"),
                null, new Deadline(LocalDate.now().minusDays(3)),
                JobStatus.TURNED_DOWN, ApplicationStage.ONLINE_ASSESSMENT));
        google.addJobApplication(new JobApplication(google, new JobTitle("Full-Stack Developer"),
                null, new Deadline(LocalDate.now().plusDays(5)),
                JobStatus.PENDING, ApplicationStage.INTERVIEW));
        jobSeekerPlus.addJobApplication(new JobApplication(jobSeekerPlus, new JobTitle("Job Seeking Pro"),
                null, new Deadline(LocalDate.now().minusDays(17)),
                JobStatus.REJECTED, ApplicationStage.RESUME));

        return new Contact[] {
            alexYeohInc, google, jobSeekerPlus,
            new Recruiter(new Name("David Li"), new Id("david_li"), new Phone("91031282"),
                    new Email("davidli@alexyeoh.example.com"), null,
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("direct", "network"), alexYeohInc),
            new Recruiter(new Name("Roy Balakrishnan"), new Id("roy_balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"), new Url("www.nus.edu.sg"),
                    null, getTagSet("friendly"), null)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Contact sampleContact : getSampleContacts()) {
            sampleAb.addContact(sampleContact);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a id set containing the list of strings given.
     */
    public static Set<Id> getIdSet(String... strings) {
        return Arrays.stream(strings)
                .map(Id::new)
                .collect(Collectors.toSet());
    }

}
