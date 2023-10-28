package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BENSON;
import static seedu.address.testutil.TypicalContacts.NUS;
import static seedu.address.testutil.TypicalContacts.RYAN;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Type;


public class JsonAdaptedContactTest {
    private static final String INVALID_TYPE = "asdf";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ID = "_12@-abc";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_URL = " ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_STATUS = "C@O";
    private static final String INVALID_OID = "_12@-abc";

    private static final String VALID_TYPE_ORG = Type.ORGANIZATION.toString();
    private static final String VALID_TYPE_REC = Type.RECRUITER.toString();
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_STATUS = "";
    private static final String VALID_POSITION = "";
    private static final String VALID_ID = "f8a36caf-6d42-4fd2-a017-7f6a92fa3155";
    private static final String VALID_PHONE = BENSON.getPhone().get().value;
    private static final String VALID_EMAIL = BENSON.getEmail().get().value;
    private static final String VALID_URL = BENSON.getUrl().get().value;
    private static final String VALID_ADDRESS = BENSON.getAddress().get().value;
    private static final String VALID_OID = NUS.getId().value;
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<JsonAdaptedId> VALID_RIDS = NUS.getRecruiterIds().stream()
            .map(JsonAdaptedId::new)
            .collect(Collectors.toList());

    private static final Organization TEST_ORGANIZATION = NUS;
    private static final Recruiter TEST_RECRUITER = RYAN;

    @Test
    public void toModelType_validOrganizationDetails_returnsContact() throws Exception {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact organization = new JsonAdaptedContact(TEST_ORGANIZATION);
        assertEquals(TEST_ORGANIZATION, organization.toModelType(addressBook));
    }

    @Test
    public void toModelType_validRecruiterDetails_returnsContact() throws Exception {
        AddressBook addressBook = new AddressBook();
        addressBook.addContact(TEST_ORGANIZATION);
        JsonAdaptedContact organization = new JsonAdaptedContact(TEST_RECRUITER);
        assertEquals(TEST_RECRUITER, organization.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, INVALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, null, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                INVALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullPhone_doesNotThrowException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                null, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, new ArrayList<>()
        );
        assertDoesNotThrow(() -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, INVALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullEmail_doesNotThrowException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, null, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        assertDoesNotThrow(() -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, INVALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullAddress_doesNotThrowException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, null, VALID_STATUS, VALID_POSITION,
                VALID_OID, VALID_TAGS, null
        );
        assertDoesNotThrow(() -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                VALID_OID, invalidTags, null
        );
        assertThrows(IllegalValueException.class, () -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_nullOid_doesNotThrowException() {
        AddressBook addressBook = new AddressBook();
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_REC, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                null, VALID_TAGS, null
        );
        assertDoesNotThrow(() -> contact.toModelType(addressBook));
    }

    @Test
    public void toModelType_invalidOid_throwsIllegalValueException() {
        AddressBook addressBook = new AddressBook();
        addressBook.addContact(TEST_ORGANIZATION);
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_TYPE_REC, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_URL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION,
                INVALID_OID, VALID_TAGS, null
        );
        assertThrows(IllegalValueException.class, () -> contact.toModelType(addressBook));
    }
}
