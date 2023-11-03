package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSITION_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BOB;
import static seedu.address.testutil.TypicalContacts.NUS;

import org.junit.jupiter.api.Test;

import seedu.address.model.jobapplication.JobApplicationTest;
import seedu.address.testutil.OrganizationBuilder;

public class OrganizationTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Organization organization = new OrganizationBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> organization.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(NUS.isSameContact(NUS));

        // null -> returns false
        assertFalse(NUS.isSameContact(null));

        // same id, all other attributes different -> returns true
        Organization editedNus = new OrganizationBuilder(NUS).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withUrl(VALID_URL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withStatus(VALID_STATUS_BOB).withPosition(VALID_POSITION_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(NUS.isSameContact(editedNus));

        // different id, all other attributes same -> returns false
        editedNus = new OrganizationBuilder(NUS).withId(VALID_ID_BOB).build();
        assertFalse(NUS.isSameContact(editedNus));

        // id differs in case, all other attributes same -> returns false
        editedNus = new OrganizationBuilder(NUS)
                .withId(NUS.getId().value.toUpperCase()).build();
        assertFalse(NUS.isSameContact(editedNus));
    }

    @Test
    public void getJobApplications_any_correctAmount() {
        Organization newNus = new OrganizationBuilder(NUS)
                .withId(NUS.getId().value.toUpperCase()).build();
        assertEquals(0, newNus.getJobApplications().length);
        newNus.addJobApplication(JobApplicationTest.SAMPLE_JOB_APPLICATION);
        assertEquals(1, newNus.getJobApplications().length);
        assertEquals(newNus.getJobApplications()[0], JobApplicationTest.SAMPLE_JOB_APPLICATION);
    }

    @Test
    public void equals() {
        // same values -> returns true
        Organization nusCopy = new OrganizationBuilder(NUS).build();
        assertTrue(NUS.equals(nusCopy));

        // same object -> returns true
        assertTrue(NUS.equals(NUS));

        // null -> returns false
        assertFalse(NUS.equals(null));

        // different type -> returns false
        assertFalse(NUS.equals(5));

        // different contact -> returns false
        assertFalse(NUS.equals(BOB));

        // different name -> returns false
        Organization editedNus = new OrganizationBuilder(NUS).withName(VALID_NAME_BOB).build();
        assertFalse(NUS.equals(editedNus));

        // different phone -> returns false
        editedNus = new OrganizationBuilder(NUS).withPhone(VALID_PHONE_BOB).build();
        assertFalse(NUS.equals(editedNus));

        // different email -> returns false
        editedNus = new OrganizationBuilder(NUS).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(NUS.equals(editedNus));

        // different url -> returns false
        editedNus = new OrganizationBuilder(NUS).withUrl(VALID_URL_BOB).build();
        assertFalse(NUS.equals(editedNus));

        // different address -> returns false
        editedNus = new OrganizationBuilder(NUS).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(NUS.equals(editedNus));

        // different tags -> returns false
        editedNus = new OrganizationBuilder(NUS).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(NUS.equals(editedNus));
    }

    @Test
    public void toStringMethod() {
        String expected = Organization.class.getCanonicalName()
                + "{name=" + NUS.getName()
                + ", type=" + NUS.getType()
                + ", id=" + NUS.getId()
                + ", phone=" + NUS.getPhone()
                + ", email=" + NUS.getEmail()
                + ", url=" + NUS.getUrl()
                + ", address=" + NUS.getAddress()
                + ", tags=" + NUS.getTags() + "}";
        assertEquals(expected, NUS.toString());
    }
}
