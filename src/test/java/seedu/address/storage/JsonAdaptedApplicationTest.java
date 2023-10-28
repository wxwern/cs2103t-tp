package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.jobapplication.ApplicationStage;
import seedu.address.model.jobapplication.Deadline;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobDescription;
import seedu.address.model.jobapplication.JobStatus;
import seedu.address.model.jobapplication.JobTitle;
import seedu.address.model.jobapplication.LastUpdatedTime;

class JsonAdaptedApplicationTest {

    private static final String INVALID_OID = "    ";
    private static final String INVALID_ORG_NAME = "@@@@";
    private static final String INVALID_TITLE = "@D";
    private static final String INVALID_DESCRIPTION = "    ";
    private static final String INVALID_DEADLINE = "22-22-2222";
    private static final String INVALID_STATUS = "PEN";
    private static final String INVALID_STAGE = "res";
    private static final String INVALID_UPDATED_TIME = "abcde";

    private static final String VALID_OID = "a1_234a";
    private static final String VALID_ORG_NAME = "Google";
    private static final String VALID_TITLE = "SWE";
    private static final String VALID_DESCRIPTION = "Pay: $100";
    private static final String VALID_DEADLINE = "22-11-2022";
    private static final String VALID_STATUS = "pending";
    private static final String VALID_STAGE = "resume";
    private static final String VALID_UPDATED_TIME = new LastUpdatedTime().toString();

    @Test
    void toModelType_invalidFields_throwsIllegalValueException() {

        // invalid id
        JsonAdaptedApplication application1 = new JsonAdaptedApplication(VALID_TITLE, VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class, Id.MESSAGE_CONSTRAINTS,
                () -> application1.toModelType(INVALID_OID, VALID_ORG_NAME));
        // invalid title
        JsonAdaptedApplication application2 = new JsonAdaptedApplication(INVALID_TITLE, VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class, JobTitle.MESSAGE_CONSTRAINTS,
                () -> application2.toModelType(VALID_OID, VALID_ORG_NAME));

        // invalid description
        JsonAdaptedApplication application3 = new JsonAdaptedApplication(VALID_TITLE,
                INVALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class, JobDescription.MESSAGE_CONSTRAINTS,
                () -> application3.toModelType(VALID_OID, VALID_ORG_NAME));

        // invalid last update time
        JsonAdaptedApplication application4 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                INVALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class,
                () -> application4.toModelType(VALID_OID, VALID_ORG_NAME));

        // invalid deadline
        JsonAdaptedApplication application5 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, INVALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class, Deadline.MESSAGE_CONSTRAINTS,
                () -> application5.toModelType(VALID_OID, VALID_ORG_NAME));

        // invalid status
        JsonAdaptedApplication application6 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, INVALID_STATUS, VALID_STAGE
        );
        assertThrows(IllegalValueException.class, JobStatus.MESSAGE_CONSTRAINTS,
                () -> application6.toModelType(VALID_OID, VALID_ORG_NAME));

        // invalid stage
        JsonAdaptedApplication application7 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, INVALID_STAGE
        );
        assertThrows(
                IllegalValueException.class, ApplicationStage.MESSAGE_CONSTRAINTS,
                () -> application7.toModelType(VALID_OID, VALID_ORG_NAME)
        );

        // invalid organization name
        JsonAdaptedApplication application8 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertThrows(
                IllegalValueException.class, Name.MESSAGE_CONSTRAINTS,
                () -> application8.toModelType(VALID_OID, INVALID_ORG_NAME)
        );
    }

    @Test
    void toModelType_validFields_doesNotThrow() {
        // all fields
        JsonAdaptedApplication application1 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );

        // no description
        JsonAdaptedApplication application2 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );

        // no updated time
        JsonAdaptedApplication application3 = new JsonAdaptedApplication(VALID_TITLE,
                VALID_DESCRIPTION,
                VALID_UPDATED_TIME, VALID_DEADLINE, VALID_STATUS, VALID_STAGE
        );
        assertDoesNotThrow(() -> application1.toModelType(VALID_OID, VALID_ORG_NAME));
        assertDoesNotThrow(() -> application2.toModelType(VALID_OID, VALID_ORG_NAME));
        assertDoesNotThrow(() -> application2.toModelType(VALID_OID, VALID_ORG_NAME));

    }

    @Test
    public void toModelType_contactGiven_ableToConvertBackToSameContact() throws Exception {
        JobApplication jobApplication = new JobApplication(
                new Id(VALID_OID),
                new Name(VALID_ORG_NAME),
                new JobTitle(VALID_TITLE),
                new JobDescription(VALID_DESCRIPTION),
                new Deadline(VALID_DEADLINE),
                JobStatus.fromString(VALID_STATUS),
                ApplicationStage.fromString(VALID_STAGE),
                new LastUpdatedTime()
        );

        JsonAdaptedApplication jsonAdaptedApplication = new JsonAdaptedApplication(jobApplication);

        assertEquals(jobApplication, jsonAdaptedApplication.toModelType(jobApplication.getOrganizationId().value,
                jobApplication.getOrgName().fullName));
    }
}
