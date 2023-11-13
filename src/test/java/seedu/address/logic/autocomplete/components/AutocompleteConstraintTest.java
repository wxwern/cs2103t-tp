package seedu.address.logic.autocomplete.components;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class AutocompleteConstraintTest {

    @Test
    public void onceForEachOf() {
        var oneTwoThreeOnlyOnce = AutocompleteConstraint.onceForEachOf(1, 2, 3);

        // Allowed if the single arg itself is not yet added
        assertTrue(oneTwoThreeOnlyOnce.isAllowed(0, Set.of(2, 3)));
        assertTrue(oneTwoThreeOnlyOnce.isAllowed(1, Set.of(2, 3)));

        // Disallowed if the single arg itself is already present
        assertFalse(oneTwoThreeOnlyOnce.isAllowed(3, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(oneTwoThreeOnlyOnce.isAllowed(0, Set.of()));
        assertTrue(oneTwoThreeOnlyOnce.isAllowed(0, Set.of(0)));
        assertTrue(oneTwoThreeOnlyOnce.isAllowed(0, Set.of(1, 2)));
    }

    @Test
    public void oneAmongAllOf() {
        var oneXorTwo = AutocompleteConstraint.oneAmongAllOf(1, 2);

        // Allowed if none of the arguments are added yet
        assertTrue(oneXorTwo.isAllowed(1, Set.of(3)));

        // Disallowed if at least one of the arguments is already present
        assertFalse(oneXorTwo.isAllowed(1, Set.of(2, 3)));
        assertFalse(oneXorTwo.isAllowed(2, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(oneXorTwo.isAllowed(0, Set.of()));
        assertTrue(oneXorTwo.isAllowed(0, Set.of(0)));
        assertTrue(oneXorTwo.isAllowed(0, Set.of(2, 3)));
    }

    @Test
    public void isPrerequisiteFor() {
        var oneIsPrereqForTwoAndThree = AutocompleteConstraint.where(1).isPrerequisiteFor(2, 3);

        // Prerequisite is always allowed
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(1, Set.of()));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(1, Set.of(0)));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(1, Set.of(0, 1)));

        // Dependents are conditionally allowed
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(2, Set.of(1)));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(2, Set.of(1, 2, 3)));
        assertFalse(oneIsPrereqForTwoAndThree.isAllowed(2, Set.of(3)));

        // Existing dependents don't impact prerequisites
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(1, Set.of(2, 3)));

        // Unspecified elements are not impacted and allowed
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(0, Set.of()));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(0, Set.of(0)));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(0, Set.of(1)));
        assertTrue(oneIsPrereqForTwoAndThree.isAllowed(0, Set.of(2, 3)));
    }

}
