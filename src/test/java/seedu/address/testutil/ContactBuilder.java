package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Status;
import seedu.address.model.person.Url;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_ID = "test_1-123";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_URL = "www.google.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_STATUS = "Applied";

    private Name name;
    private Id id;
    private Phone phone;
    private Email email;
    private Url url;
    private Address address;
    private Status status;
    private Set<Tag> tags;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        id = new Id(DEFAULT_ID);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        url = new Url(DEFAULT_URL);
        address = new Address(DEFAULT_ADDRESS);
        status = new Status(DEFAULT_STATUS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        id = contactToCopy.getId();
        phone = contactToCopy.getPhone();
        email = contactToCopy.getEmail();
        url = contactToCopy.getUrl();
        address = contactToCopy.getAddress();
        tags = new HashSet<>(contactToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code Contact} that we are building.
     */
    public ContactBuilder withId(String id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Contact} that we are building.
     */
    public ContactBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Url} of the {@code Contact} that we are building.
     */
    public ContactBuilder withUrl(String url) {
        this.url = new Url(url);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Contact build() {
        return new Contact(name, id, phone, email, url, address, tags);
    }

}
