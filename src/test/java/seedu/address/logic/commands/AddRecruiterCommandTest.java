package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.RYAN;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.contact.Recruiter;
import seedu.address.testutil.RecruiterBuilder;

public class AddRecruiterCommandTest extends AddCommandTest {

    @Test
    public void constructor_nullCompulsoryFields_throwsNullPointerException() {
        // null name field
        assertThrows(NullPointerException.class, () -> new AddRecruiterCommand(
                null, RYAN.getId(),
                RYAN.getPhone().orElse(null), RYAN.getEmail().orElse(null),
                RYAN.getUrl().orElse(null), RYAN.getAddress().orElse(null),
                RYAN.getTags(),
                RYAN.getOrganizationId().orElse(null)));

        // null id field
        assertThrows(NullPointerException.class, () -> new AddRecruiterCommand(
                RYAN.getName(), null,
                RYAN.getPhone().orElse(null), RYAN.getEmail().orElse(null),
                RYAN.getUrl().orElse(null), RYAN.getAddress().orElse(null),
                RYAN.getTags(),
                RYAN.getOrganizationId().orElse(null)));

        // null tags fields
        assertThrows(NullPointerException.class, () -> new AddRecruiterCommand(
                RYAN.getName(), RYAN.getId(),
                RYAN.getPhone().orElse(null), RYAN.getEmail().orElse(null),
                RYAN.getUrl().orElse(null), RYAN.getAddress().orElse(null),
                null,
                RYAN.getOrganizationId().orElse(null)));
    }

    @Test
    public void execute_recruiterAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Recruiter validRecruiter = new RecruiterBuilder().build();

        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                validRecruiter.getName(),
                validRecruiter.getId(),
                validRecruiter.getPhone().orElse(null),
                validRecruiter.getEmail().orElse(null),
                validRecruiter.getUrl().orElse(null),
                validRecruiter.getAddress().orElse(null),
                validRecruiter.getTags(),
                validRecruiter.getOrganizationId().orElse(null)
        );

        CommandResult commandResult = addCommand.execute(modelStub);

        assertEquals(String.format(AddRecruiterCommand.MESSAGE_SUCCESS,
                        validRecruiter.getType(), Messages.format(validRecruiter)),
                commandResult.getFeedbackToUser());

        assertEquals(Arrays.asList(validRecruiter), modelStub.contactsAdded);
    }

    @Test
    public void execute_duplicateRecruiter_throwsCommandException() {
        Recruiter validRecruiter = new RecruiterBuilder().build();
        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                validRecruiter.getName(),
                validRecruiter.getId(),
                validRecruiter.getPhone().orElse(null),
                validRecruiter.getEmail().orElse(null),
                validRecruiter.getUrl().orElse(null),
                validRecruiter.getAddress().orElse(null),
                validRecruiter.getTags(),
                validRecruiter.getOrganizationId().orElse(null)
        );
        ModelStub modelStub = new ModelStubWithContact(validRecruiter);

        assertThrows(CommandException.class,
                AddRecruiterCommand.MESSAGE_DUPLICATE_CONTACT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Recruiter ryan = new RecruiterBuilder().withName("RYAN").build();
        Recruiter rachel = new RecruiterBuilder().withName("RACHEL").build();
        AddRecruiterCommand addRyanCommand = new AddRecruiterCommand(
                ryan.getName(),
                ryan.getId(),
                ryan.getPhone().orElse(null),
                ryan.getEmail().orElse(null),
                ryan.getUrl().orElse(null),
                ryan.getAddress().orElse(null),
                ryan.getTags(),
                ryan.getOrganizationId().orElse(null)
        );
        AddRecruiterCommand addRachelCommand = new AddRecruiterCommand(
                rachel.getName(),
                rachel.getId(),
                rachel.getPhone().orElse(null),
                rachel.getEmail().orElse(null),
                rachel.getUrl().orElse(null),
                rachel.getAddress().orElse(null),
                rachel.getTags(),
                rachel.getOrganizationId().orElse(null)
        );

        // same object -> returns true
        assertTrue(addRyanCommand.equals(addRyanCommand));

        // same values -> returns true
        AddRecruiterCommand addRyanCommandCopy = new AddRecruiterCommand(
                ryan.getName(),
                ryan.getId(),
                ryan.getPhone().orElse(null),
                ryan.getEmail().orElse(null),
                ryan.getUrl().orElse(null),
                ryan.getAddress().orElse(null),
                ryan.getTags(),
                ryan.getOrganizationId().orElse(null)
        );
        assertTrue(addRyanCommand.equals(addRyanCommandCopy));

        // different types -> returns false
        assertFalse(addRyanCommand.equals(1));

        // null -> returns false
        assertFalse(addRyanCommand.equals(null));

        // different recruiters -> returns false
        assertFalse(addRyanCommand.equals(addRachelCommand));
    }

    @Test
    public void toStringMethod() {
        AddRecruiterCommand addCommand = new AddRecruiterCommand(
                RYAN.getName(),
                RYAN.getId(),
                RYAN.getPhone().orElse(null),
                RYAN.getEmail().orElse(null),
                RYAN.getUrl().orElse(null),
                RYAN.getAddress().orElse(null),
                RYAN.getTags(),
                RYAN.getOrganizationId().orElse(null)
        );
        String base = AddRecruiterCommand.class.getCanonicalName()
                + "{name=" + RYAN.getName()
                + ", id=" + RYAN.getId()
                + ", phone=" + RYAN.getPhone().orElse(null)
                + ", email=" + RYAN.getEmail().orElse(null)
                + ", url=" + RYAN.getUrl().orElse(null)
                + ", address=" + RYAN.getAddress().orElse(null)
                + ", tags=" + RYAN.getTags()
                + ", oid=" + RYAN.getOrganizationId().orElse(null);

        String expectedBefore = base + ", organization=" + null + "}";
        assertEquals(expectedBefore, addCommand.toString());

        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        assertDoesNotThrow(() -> addCommand.execute(modelStub));

        String expectedAfter = base + ", organization=" + RYAN.getOrganization().orElse(null) + "}";
        assertEquals(expectedAfter, addCommand.toString());
    }
}
