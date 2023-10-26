package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class JobStatusTest {

    @Test
    public void toString_validEnums_givesCorrectStrings() {
        assertEquals(JobStatus.OFFERED.toString(), "offered");
        assertEquals(JobStatus.TURNED_DOWN.toString(), "turned down");
    }

    @Test
    public void fromString_validStrings_givesCorrectStatuses() {
        assertEquals(JobStatus.fromString("accepted"), JobStatus.ACCEPTED);
        assertEquals(JobStatus.fromString("pending"), JobStatus.PENDING);
    }

    @Test
    public void fromString_invalidStrings_givesUnknown() {
        assertEquals(JobStatus.fromString(""), JobStatus.UNKNOWN);
        assertEquals(JobStatus.fromString("test"), JobStatus.UNKNOWN);
        assertEquals(JobStatus.fromString("pening"), JobStatus.UNKNOWN);
    }

    @Test
    public void equals() {
        assertEquals(JobStatus.REJECTED, JobStatus.REJECTED);
    }
}
