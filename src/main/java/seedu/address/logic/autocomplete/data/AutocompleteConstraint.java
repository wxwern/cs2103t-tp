package seedu.address.logic.autocomplete.data;

import java.util.Collection;
import java.util.Set;

/**
 * Constraints autocompletion to a certain restriction.
 * This functional interface takes in an input and a {@link Set} of existing elements,
 * and determines whether the input should be allowed to be added among the existing elements.
 */
@FunctionalInterface
public interface AutocompleteConstraint<T> {

    boolean isAllowed(T input, Set<? extends T> existingElements);

    // Constraint operators

    /**
     * Creates a constraint that returns true as long as any given constraints return true.
     */
    static <T> AutocompleteConstraint<T> anyOf(Collection<AutocompleteConstraint<? super T>> constraints) {
        return (input, existingElements) -> {
            for (var c : constraints) {
                if (c.isAllowed(input, existingElements)) {
                    return true;
                }
            }
            return false;
        };
    }

    /**
     * Creates a constraint that returns true as long as all given constraints return true.
     */
    static <T> AutocompleteConstraint<T> allOf(Collection<AutocompleteConstraint<? super T>> constraints) {
        return (input, existingElements) -> {
            for (var c : constraints) {
                if (!c.isAllowed(input, existingElements)) {
                    return false;
                }
            }
            return true;
        };
    }

    // Simple constraint templates

    /**
     * Creates a constraint that enforces all provided {@code items} <i>each</i> may exist at most once.
     */
    @SafeVarargs
    static <T> AutocompleteConstraint<T> onceForEachOf(T... items) {
        Set<T> itemsSet = Set.of(items);

        return (input, existingElements) -> {
            if (!itemsSet.contains(input)) {
                // Not part of consideration. True by default.
                return true;
            }
            return !existingElements.contains(input); // Input does not exists <--> input can exist.
        };
    }

    /**
     * Creates a constraint that enforces at most one item <i>within the entire</i> {@code items} may exist at a time.
     */
    @SafeVarargs
    static <T> AutocompleteConstraint<T> oneAmongAllOf(T... items) {
        Set<T> itemsSet = Set.of(items);

        return (input, existingElements) -> {
            if (!itemsSet.contains(input)) {
                // Not part of consideration. True by default.
                return true;
            }

            for (T item : items) {
                if (existingElements.contains(item)) {
                    // Some set element is already present -> no more allowed.
                    return false;
                }
            }

            return true;
        };
    }

    // Advanced relational constraint templates

    /**
     * Represents an item that will be part of the constraint.
     * This exists to improve readability of relational factory methods,
     * along the lines of {@code .where(x).isSomethingTo(y...)}
     */
    class Item<T> {

        final T item;

        private Item(T item) {
            this.item = item;
        }

        /**
         * Creates a constraint that enforces that {@code prerequisite}, i.e. this item,
         * must be present before any of {@code dependents} may exist.
         */
        @SafeVarargs
        public final AutocompleteConstraint<T> isPrerequisiteFor(T... dependents) {
            T prerequisite = this.item;

            Set<T> dependentsSet = Set.of(dependents);

            return (input, existingElements) -> {
                if (!dependentsSet.contains(input)) {
                    // Not part of dependents. True by default.
                    return true;
                }
                return existingElements.contains(prerequisite); // Prerequisite exists <--> dependents can exist.
            };
        }

        /**
         * Creates a constraint that enforces that this current item
         * cannot be present if any of {@code incompatibleItems} exist.
         */
        @SafeVarargs
        public final AutocompleteConstraint<T> cannotExistAlongsideAnyOf(T... incompatibleItems) {
            T currentItem = this.item;

            Set<T> incompatibleItemsSet = Set.of(incompatibleItems);

            return (input, existingElements) -> {
                if (!currentItem.equals(input)) {
                    // Not the current item.

                    // If current item is already in existing elements,
                    // ensure no incompatible elements pass through.
                    return !(existingElements.contains(currentItem)
                            && incompatibleItemsSet.contains(input));
                }

                // Is thh current item.

                // No items in incompatible set exists in existing set --> current item may exist.
                return incompatibleItemsSet.stream().noneMatch(existingElements::contains);
            };
        }
    }

    /**
     * Represents an item that is a part of a constraint relationship.
     * This should be immediately followed by the relevant constraint methods,
     * along the lines of {@code .where(x).isSomethingTo(y...)}
     */
    static <T> Item<T> where(T item) {
        return new Item<T>(item);
    }


}
