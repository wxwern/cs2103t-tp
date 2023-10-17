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
import seedu.address.model.person.Phone;
import seedu.address.model.person.Url;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Contact[] getSampleContacts() {
        return new Contact[] {
            new Contact(new Name("Alex Yeoh"), new Id("test_1"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"), new Url("www.google.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Contact(new Name("Bernice Yu"), new Id("test_2"), new Phone("99272758"),
                    new Email("berniceyu@example.com"), new Url("www.facebook.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Contact(new Name("Charlotte Oliveiro"), new Id("test_3"), new Phone("93210283"),
                    new Email("charlotte@example.com"), new Url("www.linkedin.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Contact(new Name("David Li"), new Id("test_4"), new Phone("91031282"),
                    new Email("lidavid@example.com"), new Url("www.nus.edu.sg"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Contact(new Name("Irfan Ibrahim"), new Id("test_5"), new Phone("92492021"),
                    new Email("irfan@example.com"), new Url("www.google.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Contact(new Name("Roy Balakrishnan"), new Id("test_6"), new Phone("92624417"),
                    new Email("royb@example.com"), new Url("www.luminus.nus.edu.sg"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
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

}
