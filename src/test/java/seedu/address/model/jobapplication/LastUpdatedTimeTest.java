package seedu.address.model.jobapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class LastUpdatedTimeTest {

    @Test
    public void equals_self_givesTrue() {
        LastUpdatedTime lastUpdatedTime = new LastUpdatedTime();
        assertEquals(lastUpdatedTime, lastUpdatedTime);
    }

    @Test
    public void equals_otherWithDifferentDateTime_givesFalse() {
        LocalDateTime firstDateTime = LocalDateTime.of(2023, 11, 11, 11, 11, 11);
        LocalDateTime secondDateTime = LocalDateTime.of(2023, 11, 11, 11, 11, 10);
        LastUpdatedTime lastUpdatedTime = new LastUpdatedTime(firstDateTime);
        LastUpdatedTime nextUpdatedTime = new LastUpdatedTime(secondDateTime);
        assertNotEquals(lastUpdatedTime, nextUpdatedTime);
    }

    @Test
    public void equals_notLastUpdatedTime_givesFalse() {
        LocalDateTime firstDateTime = LocalDateTime.of(2023, 11, 11, 11, 11, 11);
        assertNotEquals(firstDateTime, null);
    }
}
