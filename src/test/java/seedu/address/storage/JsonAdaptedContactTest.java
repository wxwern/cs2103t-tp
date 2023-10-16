package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;


public class JsonAdaptedContactTest {
    private static final String INVALID_TYPE = "asdf";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_ID = "_12@-abc";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_STATUS = "C@O";

    private static final String VALID_TYPE_ORG = "organization";
    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_STATUS = "";
    private static final String VALID_POSITION = "";
    private static final String VALID_ID = "78a36caf-6d42-4fd2-a017-7f6a92fa3155";
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validContactDetails_returnsContact() throws Exception {
        JsonAdaptedContact person = new JsonAdaptedContact(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, INVALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, null, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                null, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, null, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, null, VALID_STATUS, VALID_POSITION, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact person = new JsonAdaptedContact(VALID_TYPE_ORG, VALID_NAME, VALID_ID,
                VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_STATUS, VALID_POSITION, invalidTags);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

}
