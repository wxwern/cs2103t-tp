package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Recruiter;
import seedu.address.model.person.Status;
import seedu.address.model.person.Url;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Organization(new Name("Alex Yeoh Inc"), new Id("alex_yeoh_inc"), new Phone("87438807"),
                    new Email("contact@alexyeoh.example.com"), new Url("alexyeoh.example.com"),
                    null, getTagSet("parttime"), new Status("interested"), null, Set.of()),
            new Organization(new Name("Google"), new Id("google"), new Phone("65218000"), null,
                    new Url("careers.google.com"),
                    new Address("70 Pasir Panjang Road, #03-71, "
                            + "Mapletree Business City, "
                            + "Singapore 117371"),
                    getTagSet("bigtech", "internship", "competitive"),
                    new Status("applied"), new Position("Software Engineer"), Set.of()),
            new Organization(new Name("Job Seeker Plus"), new Id("job_seeker_plus"), new Phone("93210283"),
                    new Email("jobseekerplus@example.com"), null,
                    new Address("Blk 16 Real Street 128, #08-04"),
                    getTagSet("startup", "internship"), null, null, Set.of()),
            new Recruiter(new Name("David Li"), new Id("david_li"), new Phone("91031282"),
                    new Email("davidli@alexyeoh.example.com"), null,
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("direct", "network"), new Id("alex_yeoh_inc")),
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
