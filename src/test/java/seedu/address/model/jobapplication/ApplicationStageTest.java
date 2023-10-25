package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ApplicationStageTest {

    @Test
    public void toString_validEnum_givesCorrectStrings() {
        assertEquals(ApplicationStage.RESUME.toString(), "resume");
        assertEquals(ApplicationStage.ONLINE_ASSESSMENT.toString(), "online assessment");
    }

    @Test
    public void fromString_validStrings_givesCorrectStages() {
        assertEquals(ApplicationStage.RESUME, ApplicationStage.fromString("resume"));
        assertEquals(
                ApplicationStage.ONLINE_ASSESSMENT,
                ApplicationStage.fromString("online assessment"));
    }

    @Test
    public void fromString_invalidStrings_givesUnknown() {
        assertEquals(ApplicationStage.UNKNOWN, ApplicationStage.fromString(""));
        assertEquals(ApplicationStage.UNKNOWN, ApplicationStage.fromString("    "));
        assertEquals(ApplicationStage.UNKNOWN, ApplicationStage.fromString("resumee"));
    }
}
