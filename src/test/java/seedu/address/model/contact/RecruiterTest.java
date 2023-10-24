package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.AMY;
import static seedu.address.testutil.TypicalContacts.RYAN;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.RecruiterBuilder;

class RecruiterTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Recruiter recruiter = new RecruiterBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> recruiter.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(RYAN.isSameContact(RYAN));

        // null -> returns false
        assertFalse(RYAN.isSameContact(null));

        // same id, all other attributes different -> returns true
        Recruiter editedRyan = new RecruiterBuilder(RYAN).withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withUrl(VALID_URL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withOid(VALID_OID_AMY).build();
        assertTrue(RYAN.isSameContact(editedRyan));

        // different id, all other attributes same -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withId(VALID_ID_AMY).build();
        assertFalse(RYAN.isSameContact(editedRyan));

        // id differs in case, all other attributes same -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withId(RYAN.getId().value.toUpperCase()).build();
        assertFalse(RYAN.isSameContact(editedRyan));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Recruiter ryanCopy = new RecruiterBuilder(RYAN).build();
        assertTrue(RYAN.equals(ryanCopy));

        // same object -> returns true
        assertTrue(RYAN.equals(RYAN));

        // null -> returns false
        assertFalse(RYAN.equals(null));

        // different type -> returns false
        assertFalse(RYAN.equals(5));

        // different contact -> returns false
        assertFalse(RYAN.equals(AMY));

        // different name -> returns false
        Contact editedRyan = new RecruiterBuilder(RYAN).withName(VALID_NAME_AMY).build();
        assertFalse(RYAN.equals(editedRyan));

        // different phone -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withPhone(VALID_PHONE_AMY).build();
        assertFalse(RYAN.equals(editedRyan));

        // different email -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withEmail(VALID_EMAIL_AMY).build();
        assertFalse(RYAN.equals(editedRyan));

        // different url -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withUrl(VALID_URL_AMY).build();
        assertFalse(RYAN.equals(editedRyan));

        // different address -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withAddress(VALID_ADDRESS_AMY).build();
        assertFalse(RYAN.equals(editedRyan));

        // different tags -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withTags(VALID_TAG_FRIEND).build();
        assertFalse(RYAN.equals(editedRyan));

        // different oid -> returns false
        editedRyan = new RecruiterBuilder(RYAN).withOid(VALID_OID_AMY).build();
        assertFalse(RYAN.equals(editedRyan));
    }

    @Test
    public void toStringMethod() {
        String expected = Recruiter.class.getCanonicalName()
                + "{name=" + RYAN.getName()
                + ", type=" + RYAN.getType()
                + ", id=" + RYAN.getId()
                + ", phone=" + RYAN.getPhone()
                + ", email=" + RYAN.getEmail()
                + ", url=" + RYAN.getUrl()
                + ", address=" + RYAN.getAddress()
                + ", tags=" + RYAN.getTags()
                + ", oid=" + RYAN.getOrganizationId() + "}";
        assertEquals(expected, RYAN.toString());
    }
}
