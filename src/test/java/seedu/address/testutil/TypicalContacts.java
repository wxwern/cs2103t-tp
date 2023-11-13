package seedu.address.testutil;

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

    public static final Organization NUS = new OrganizationBuilder()
            .withName("NUS SoC")
            .withId("nus-soc_sg")
            .withPhone("65162727")
            .withEmail("socug@comp.nus.edu.sg")
            .withUrl("www.nus.edu.sg")
            .withAddress("Computing 1, 13 Computing Drive")
            .withTags("computing", "worldClass")
            .withApplications(TypicalJobApplications.JOB_APPLICATIONS)
            .build();

    public static final Organization NTU = new OrganizationBuilder()
            .withName("NTU SoC")
            .withId("ntu-soc_sg")
            .withPhone("67905786")
            .withEmail("scse-undergrad@ntu.edu.sg")
            .withUrl("www.ntu.edu.sg")
            .withAddress("Block N4 #02a-32, Nanyang Avenue")
            .withTags("computing", "researchOriented")
            .build();

    public static final Organization SMU = new OrganizationBuilder()
            .withName("SMU Info Sys")
            .withId("smu-is_sg")
            .withPhone("68087960")
            .withEmail("scis_ugrad@smu.edu.sg")
            .withUrl("www.smu.edu.sg")
            .withAddress("80 Stamford Rd")
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
    public static final Organization TIKTOK = new OrganizationBuilder()
            .withName("TikTok")
            .withId("tiktok-bytedance")
            .withPhone("69504420")
            .withEmail("tiktok@example.com")
            .withAddress("One Raffles Quay")
            .build();
    public static final Recruiter IDA = new RecruiterBuilder()
            .withName("Ida Mueller")
            .withId("ida-tiktok_rec")
            .withPhone("8482131")
            .withEmail("idamueller@example.com")
            .withAddress("Two Raffles Quay")
            .withOrganization(TIKTOK)
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
        return new ArrayList<>(Arrays.asList(NUS, NTU, SMU, RYAN, RACHEL, REX, RICHARD));
    }
}
