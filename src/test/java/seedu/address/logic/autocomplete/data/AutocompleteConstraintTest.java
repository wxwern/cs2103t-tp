package seedu.address.logic.autocomplete.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class AutocompleteConstraintTest {

    @Test
    public void onceForEachOf() {

        // Allowed if the single arg itself is not yet added
        assertTrue(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(0, Set.of(2, 3)));

        assertTrue(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(1, Set.of(2, 3)));

        // Disallowed if the single arg itself is already present
        assertFalse(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(3, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(0, Set.of()));

        assertTrue(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(0, Set.of(0)));

        assertTrue(AutocompleteConstraint.onceForEachOf(1, 2, 3)
                .isAllowed(0, Set.of(1, 2)));
    }

    @Test
    public void oneAmongAllOf() {
        // Allowed if none of the arguments are added yet
        assertTrue(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(1, Set.of(3)));

        // Disallowed if at least one of the arguments is already present
        assertFalse(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(1, Set.of(2, 3)));

        assertFalse(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(2, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(0, Set.of()));

        assertTrue(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(0, Set.of(0)));

        assertTrue(AutocompleteConstraint.oneAmongAllOf(1, 2)
                .isAllowed(0, Set.of(2, 3)));
    }

    @Test
    public void isPrerequisiteFor() {
        // Prerequisite is always allowed
        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(1, Set.of()));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(1, Set.of(0)));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(1, Set.of(0, 1)));

        // Dependents are conditionally allowed
        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(2, Set.of(1)));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(2, Set.of(1, 2, 3)));

        assertFalse(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(2, Set.of(3)));

        // Existing dependents don't impact prerequisites
        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(1, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(0, Set.of()));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(0, Set.of(0)));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(0, Set.of(1)));

        assertTrue(AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3)
                .isAllowed(0, Set.of(2, 3)));
    }

}
