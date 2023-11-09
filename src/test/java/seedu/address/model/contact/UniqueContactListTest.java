package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.NTU;
import static seedu.address.testutil.TypicalContacts.NUS;
import static seedu.address.testutil.TypicalContacts.RYAN;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.exceptions.DuplicatePersonException;
import seedu.address.model.contact.exceptions.PersonNotFoundException;
import seedu.address.testutil.OrganizationBuilder;
import seedu.address.testutil.RecruiterBuilder;

public class UniqueContactListTest {

    private final UniqueContactList uniqueContactList = new UniqueContactList();

    private final Organization testOrganization = NUS;
    private final Recruiter testRecruiter = RYAN;

    @Test
    public void contains_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.contains(null));
    }

    @Test
    public void contains_contactNotInList_returnsFalse() {
        assertFalse(uniqueContactList.contains(testOrganization));
    }

    @Test
    public void contains_organizationInList_returnsTrue() {
        uniqueContactList.add(testOrganization);
        assertTrue(uniqueContactList.contains(testOrganization));
    }

    @Test
    public void contains_recruiterInList_returnsTrue() {
        uniqueContactList.add(testRecruiter);
        assertTrue(uniqueContactList.contains(testRecruiter));
    }

    @Test
    public void contains_organizationWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactList.add(testOrganization);
        Organization editedOrganization = new OrganizationBuilder(testOrganization)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueContactList.contains(editedOrganization));
    }

    @Test
    public void contains_recruiterWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContactList.add(testRecruiter);
        Recruiter editedRecruiter = new RecruiterBuilder(testRecruiter)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(uniqueContactList.contains(editedRecruiter));
    }

    @Test
    public void add_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.add(null));
    }

    @Test
    public void add_duplicateOrganization_throwsDuplicateContactException() {
        uniqueContactList.add(testOrganization);
        assertThrows(DuplicatePersonException.class, () -> uniqueContactList.add(testOrganization));
    }

    @Test
    public void add_duplicateRecruiter_throwsDuplicateContactException() {
        uniqueContactList.add(testRecruiter);
        assertThrows(DuplicatePersonException.class, () -> uniqueContactList.add(testRecruiter));
    }

    @Test
    public void setContact_nullTargetContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueContactList.setContact(null, testOrganization));
    }

    @Test
    public void setContact_nullEditedContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueContactList.setContact(testOrganization, null));
    }

    @Test
    public void setContact_targetContactNotInList_throwsContactNotFoundException() {
        assertThrows(PersonNotFoundException.class, () ->
                uniqueContactList.setContact(testOrganization, testOrganization));
    }

    @Test
    public void setContact_editedContactIsSameContact_success() {
        uniqueContactList.add(testOrganization);
        uniqueContactList.setContact(testOrganization, testOrganization);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(testOrganization);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasSameIdentity_success() {
        uniqueContactList.add(testOrganization);
        Organization editedOrganization = new OrganizationBuilder(testOrganization)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        uniqueContactList.setContact(testOrganization, editedOrganization);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(editedOrganization);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasDifferentIdentity_success() {
        uniqueContactList.add(testOrganization);
        uniqueContactList.setContact(testOrganization, NTU);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(NTU);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContact_editedContactHasNonUniqueIdentity_throwsDuplicateContactException() {
        uniqueContactList.add(testOrganization);
        uniqueContactList.add(NTU);
        assertThrows(DuplicatePersonException.class, () ->
                uniqueContactList.setContact(testOrganization, NTU));
    }

    @Test
    public void remove_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.remove(null));
    }

    @Test
    public void remove_contactDoesNotExist_throwsContactNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueContactList.remove(testOrganization));
    }

    @Test
    public void remove_existingContact_removesContact() {
        uniqueContactList.add(testOrganization);
        uniqueContactList.remove(testOrganization);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullUniqueContactList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((UniqueContactList) null));
    }

    @Test
    public void setContacts_uniqueContactList_replacesOwnListWithProvidedUniqueContactList() {
        uniqueContactList.add(testOrganization);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(NTU);
        uniqueContactList.setContacts(expectedUniqueContactList);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContactList.setContacts((List<Contact>) null));
    }

    @Test
    public void setContacts_list_replacesOwnListWithProvidedList() {
        uniqueContactList.add(testOrganization);
        List<Contact> contactList = Collections.singletonList(NTU);
        uniqueContactList.setContacts(contactList);
        UniqueContactList expectedUniqueContactList = new UniqueContactList();
        expectedUniqueContactList.add(NTU);
        assertEquals(expectedUniqueContactList, uniqueContactList);
    }

    @Test
    public void setContacts_listWithDuplicateContacts_throwsDuplicateContactException() {
        List<Contact> listWithDuplicateContacts = Arrays.asList(testOrganization, testOrganization);
        assertThrows(DuplicatePersonException.class, () -> uniqueContactList.setContacts(listWithDuplicateContacts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueContactList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueContactList.asUnmodifiableObservableList().toString(), uniqueContactList.toString());
    }
}
