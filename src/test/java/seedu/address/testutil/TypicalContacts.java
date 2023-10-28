package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Recruiter;

/**
 * A utility class containing a list of {@code Contact} objects to be used in tests.
 */
public class TypicalContacts {

    public static final Contact ALICE = new ContactBuilder()
            .withName("Alice Pauline")
            .withId("test_1-123")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Contact BENSON = new ContactBuilder()
            .withName("Benson Meier")
            .withId("test_2-123")
            .withUrl("www.google.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Contact CARL = new ContactBuilder()
            .withName("Carl Kurz")
            .withId("test_3-123")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street").build();
    public static final Contact DANIEL = new ContactBuilder()
            .withName("Daniel Meier")
            .withId("test_4-123")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withTags("friends").build();
    public static final Contact ELLE = new ContactBuilder()
            .withName("Elle Meyer")
            .withId("test_5-123")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave").build();
    public static final Contact FIONA = new ContactBuilder()
            .withName("Fiona Kunz")
            .withId("test_6-123")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo").build();
    public static final Contact GEORGE = new ContactBuilder()
            .withName("George Best")
            .withId("test_7-123")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street").build();

    public static final Organization NUS = new OrganizationBuilder()
            .withName("NUS SoC")
            .withId("nus-soc_sg")
            .withPhone("65162727")
            .withEmail("socug@comp.nus.edu.sg")
            .withUrl("www.nus.edu.sg")
            .withAddress("Computing 1, 13 Computing Drive")
            .withTags("computing", "worldClass")
            .withStatus("Applied")
            .withPosition("Research Assistant")
            .withRids("soc-rec_ryan")
            .build();

    public static final Organization NTU = new OrganizationBuilder()
            .withName("NTU SoC")
            .withId("ntu-soc_sg")
            .withPhone("67905786")
            .withEmail("scse-undergrad@ntu.edu.sg")
            .withUrl("www.ntu.edu.sg")
            .withAddress("Block N4 #02a-32, Nanyang Avenue")
            .withTags("computing", "researchOriented")
            .withStatus("Rejected")
            .withPosition("Teaching Assistant")
            .build();

    public static final Organization SMU = new OrganizationBuilder()
            .withName("SMU Info Sys")
            .withId("smu-is_sg")
            .withPhone("68087960")
            .withEmail("scis_ugrad@smu.edu.sg")
            .withUrl("www.smu.edu.sg")
            .withAddress("80 Stamford Rd")
            .withStatus("Interested")
            .withPosition("Web Developer")
            .build();

    public static final Recruiter RYAN = new RecruiterBuilder()
            .withName("Ryan Lau")
            .withId("soc-rec_ryan")
            .withPhone("82930129")
            .withEmail("ryanlau@comp.nus.edu.sg")
            .withUrl("www.ryan_is_cool.com")
            .withAddress("Computing 1, 13 Computing Drive")
            .withTags("friendly")
            .withOrganization(NUS)
            .build();

    public static final Recruiter RACHEL = new RecruiterBuilder()
            .withName("Rachel Lam")
            .withId("soc-rec_rachel")
            .withPhone("62834678")
            .withEmail("rachelam@ntu.edu.sg")
            .withUrl("www.rachel_cs_legend.com")
            .withAddress("Block N4 #02a-32, Nanyang Avenue")
            .withTags("strict", "helpful")
            .withOrganization(NTU)
            .build();

    public static final Recruiter REX = new RecruiterBuilder()
            .withName("Rex Lee")
            .withId("is-rec_rex")
            .withPhone("83428451")
            .withEmail("rexlee@smu.edu.sg")
            .withUrl("www.rex.com")
            .withAddress("80 Stamford Rd")
            .withTags("cool", "resourceful")
            .withOrganization(NTU)
            .build();

    public static final Recruiter RICHARD = new RecruiterBuilder()
            .withName("Richard Lim")
            .withId("is-rec_richard")
            .withPhone("83228311")
            .withEmail("richlee@smu.edu.sg")
            .withUrl("www.richard_lion_heart.com")
            .withAddress("80 Stamford Rd")
            .withOrganization(null)
            .build();

    // Manually added
    public static final Contact HOON = new ContactBuilder()
            .withName("Hoon Meier")
            .withId("test_8-123")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .build();
    public static final Contact IDA = new ContactBuilder()
            .withName("Ida Mueller")
            .withId("test_9-123")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .build();

    // Manually added - Contact's details found in {@code CommandTestUtil}
    public static final Contact AMY = new ContactBuilder()
            .withId(VALID_ID_AMY)
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withUrl(VALID_URL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Contact BOB = new ContactBuilder()
            .withId(VALID_ID_BOB)
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withUrl(VALID_URL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical contacts.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Contact contact : getTypicalContacts()) {
            ab.addContact(contact);
        }
        return ab;
    }

    public static List<Contact> getTypicalContacts() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, NUS, NTU, SMU, RYAN, RACHEL, REX, RICHARD));
    }
}
