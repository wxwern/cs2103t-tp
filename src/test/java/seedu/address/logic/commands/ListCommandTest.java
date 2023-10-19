package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_RECRUITERS;
import static seedu.address.testutil.TypicalContacts.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_CONTACTS), model,
                ListCommand.MESSAGE_SUCCESS_ALL_CONTACTS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showContactAtIndex(model, INDEX_FIRST_CONTACT);
        assertCommandSuccess(new ListCommand(PREDICATE_SHOW_ALL_CONTACTS),
                model, ListCommand.MESSAGE_SUCCESS_ALL_CONTACTS, expectedModel);
    }

    @Test
    public void equals() {
        ListCommand showAllListCommand = new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
        ListCommand organizationListCommand = new ListCommand(PREDICATE_SHOW_ONLY_ORGANIZATIONS);
        ListCommand recruiterListCommand = new ListCommand(PREDICATE_SHOW_ONLY_RECRUITERS);

        // same object -> returns true
        assertTrue(showAllListCommand.equals(showAllListCommand));
        assertTrue(organizationListCommand.equals(organizationListCommand));
        assertTrue(recruiterListCommand.equals(recruiterListCommand));

        // same predicate -> returns true
        ListCommand showAllListCommandCopy = new ListCommand(PREDICATE_SHOW_ALL_CONTACTS);
        assertTrue(showAllListCommand.equals(showAllListCommandCopy));

        // different types -> returns false
        assertFalse(showAllListCommand.equals(1));

        // null -> returns false
        assertFalse(showAllListCommand.equals(null));

        // different predicate -> returns false
        assertFalse(showAllListCommand.equals(organizationListCommand));
        assertFalse(showAllListCommand.equals(recruiterListCommand));
        assertFalse(organizationListCommand.equals(recruiterListCommand));
    }
}
