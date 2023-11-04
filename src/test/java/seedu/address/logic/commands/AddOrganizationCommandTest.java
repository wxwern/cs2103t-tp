package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalContacts.NUS;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.contact.Organization;
import seedu.address.testutil.OrganizationBuilder;

public class AddOrganizationCommandTest extends AddCommandTest {

    @Test
    public void constructor_nullCompulsoryFields_throwsNullPointerException() {
        // null name field
        assertThrows(NullPointerException.class, () -> new AddOrganizationCommand(
                null, NUS.getId(),
                NUS.getPhone().orElse(null), NUS.getEmail().orElse(null),
                NUS.getUrl().orElse(null), NUS.getAddress().orElse(null),
                NUS.getTags(),
                NUS.getStatus().orElse(null), NUS.getPosition().orElse(null)));

        // null id field
        assertThrows(NullPointerException.class, () -> new AddOrganizationCommand(
                NUS.getName(), null,
                NUS.getPhone().orElse(null), NUS.getEmail().orElse(null),
                NUS.getUrl().orElse(null), NUS.getAddress().orElse(null),
                NUS.getTags(),
                NUS.getStatus().orElse(null), NUS.getPosition().orElse(null)));

        // null tags fields
        assertThrows(NullPointerException.class, () -> new AddOrganizationCommand(
                NUS.getName(), NUS.getId(),
                NUS.getPhone().orElse(null), NUS.getEmail().orElse(null),
                NUS.getUrl().orElse(null), NUS.getAddress().orElse(null),
                null,
                NUS.getStatus().orElse(null), NUS.getPosition().orElse(null)));
    }

    @Test
    public void execute_organizationAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingContactAdded modelStub = new ModelStubAcceptingContactAdded();
        Organization validOrganization = new OrganizationBuilder().build();

        AddOrganizationCommand addCommand = new AddOrganizationCommand(
                validOrganization.getName(),
                validOrganization.getId(),
                validOrganization.getPhone().orElse(null),
                validOrganization.getEmail().orElse(null),
                validOrganization.getUrl().orElse(null),
                validOrganization.getAddress().orElse(null),
                validOrganization.getTags(),
                validOrganization.getStatus().orElse(null),
                validOrganization.getPosition().orElse(null)
        );

        CommandResult commandResult = addCommand.execute(modelStub);

        assertEquals(String.format(AddOrganizationCommand.MESSAGE_SUCCESS,
                        validOrganization.getType(), Messages.format(validOrganization)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validOrganization), modelStub.contactsAdded);
    }

    @Test
    public void execute_duplicateOrganization_throwsCommandException() {
        Organization validOrganization = new OrganizationBuilder().build();
        AddOrganizationCommand addCommand = new AddOrganizationCommand(
                validOrganization.getName(),
                validOrganization.getId(),
                validOrganization.getPhone().orElse(null),
                validOrganization.getEmail().orElse(null),
                validOrganization.getUrl().orElse(null),
                validOrganization.getAddress().orElse(null),
                validOrganization.getTags(),
                validOrganization.getStatus().orElse(null),
                validOrganization.getPosition().orElse(null)
        );
        ModelStub modelStub = new ModelStubWithContact(validOrganization);

        assertThrows(CommandException.class,
                AddOrganizationCommand.MESSAGE_DUPLICATE_CONTACT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Organization nus = new OrganizationBuilder().withName("NUS").build();
        Organization ntu = new OrganizationBuilder().withName("NTU").build();
        AddOrganizationCommand addNusCommand = new AddOrganizationCommand(
                nus.getName(),
                nus.getId(),
                nus.getPhone().orElse(null),
                nus.getEmail().orElse(null),
                nus.getUrl().orElse(null),
                nus.getAddress().orElse(null),
                nus.getTags(),
                nus.getStatus().orElse(null),
                nus.getPosition().orElse(null)
        );
        AddOrganizationCommand addNtuCommand = new AddOrganizationCommand(
                ntu.getName(),
                ntu.getId(),
                ntu.getPhone().orElse(null),
                ntu.getEmail().orElse(null),
                ntu.getUrl().orElse(null),
                ntu.getAddress().orElse(null),
                ntu.getTags(),
                ntu.getStatus().orElse(null),
                ntu.getPosition().orElse(null)
        );

        // same object -> returns true
        assertTrue(addNusCommand.equals(addNusCommand));

        // same values -> returns true
        AddOrganizationCommand addNusCommandCopy = new AddOrganizationCommand(
                nus.getName(),
                nus.getId(),
                nus.getPhone().orElse(null),
                nus.getEmail().orElse(null),
                nus.getUrl().orElse(null),
                nus.getAddress().orElse(null),
                nus.getTags(),
                nus.getStatus().orElse(null),
                nus.getPosition().orElse(null)
        );
        assertTrue(addNusCommand.equals(addNusCommandCopy));

        // different types -> returns false
        assertFalse(addNusCommand.equals(1));

        // null -> returns false
        assertFalse(addNusCommand.equals(null));

        // different contact -> returns false
        assertFalse(addNusCommand.equals(addNtuCommand));
    }

    @Test
    public void toStringMethod() {
        AddOrganizationCommand addCommand = new AddOrganizationCommand(
                NUS.getName(),
                NUS.getId(),
                NUS.getPhone().orElse(null),
                NUS.getEmail().orElse(null),
                NUS.getUrl().orElse(null),
                NUS.getAddress().orElse(null),
                NUS.getTags(),
                NUS.getStatus().orElse(null),
                NUS.getPosition().orElse(null)
        );
        String expected = AddOrganizationCommand.class.getCanonicalName()
                + "{name=" + NUS.getName()
                + ", id=" + NUS.getId()
                + ", phone=" + NUS.getPhone().orElse(null)
                + ", email=" + NUS.getEmail().orElse(null)
                + ", url=" + NUS.getUrl().orElse(null)
                + ", address=" + NUS.getAddress().orElse(null)
                + ", tags=" + NUS.getTags() + "}";
        assertEquals(expected, addCommand.toString());
    }
}
