package seedu.address.logic.autocomplete.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A set of items that can be used for autocompletion with any corresponding constraints on them.
 *
 * <p>
 * Suppose a command already has specific items, and some items have restrictions such as being only able to
 * be used once. Then, one may specify the constraints when setting up this set, and then any time determine
 * what items are still available for autocompletion by passing items already used into
 * {@link #getElementsAfterConsuming(Set)}.
 * </p>
 *
 * <p>
 * The convenience factory methods {@link #onceForEachOf}, {@link #oneAmongAllOf} and {@link #anyNumberOf}
 * may be useful to quickly create a set of items with these common constraints, while convenience methods like
 * {@link #concat}, {@link #addDependents}, {@link #addConstraints} can be used for chaining and composing rules.
 * </p>
 *
 * <p>
 * This is a subclass of {@link LinkedHashSet}, so it inherits all its properties like its set functions and
 * preservation of insertion order.
 * </p>
 */
public final class AutocompleteDataSet<T> extends LinkedHashSet<T> {

    private final Set<AutocompleteConstraint<? super T>> constraints = new LinkedHashSet<>();

    /**
     * Creates an empty {@link AutocompleteDataSet}.
     */
    public AutocompleteDataSet() {
        super();
    }

    /**
     * Creates a {@link AutocompleteDataSet} with the given elements and constraints.
     *
     * <p>
     * This is mainly useful if your rules are complex. Otherwise, the convenience factory
     * methods {@link #onceForEachOf}, {@link #oneAmongAllOf} and {@link #anyNumberOf}
     * may be useful to quickly create an instance with these common constraints.
     * </p>
     *
     * @param collection The collection of items.
     * @param constraints The constraints for the given items.
     *
     * @see #onceForEachOf
     * @see #oneAmongAllOf
     * @see #anyNumberOf
     */
    private AutocompleteDataSet(
            Collection<? extends T> collection,
            Collection<AutocompleteConstraint<? super T>> constraints
    ) {
        super(collection);
        this.constraints.addAll(constraints);
    }

    /**
     * Creates an {@link AutocompleteDataSet} with the given elements,
     * and the constraint that each given element may exist at most once in a command.
     */
    @SafeVarargs
    public static <T> AutocompleteDataSet<T> onceForEachOf(T... items) {
        return new AutocompleteDataSet<T>(
            List.of(items),
            List.of(AutocompleteConstraint.onceForEachOf(items))
        );
    }

    /**
     * Creates an {@link AutocompleteDataSet} with the given elements,
     * and the constraint that only one of the given elements may exist in a command.
     */
    @SafeVarargs
    public static <T> AutocompleteDataSet<T> oneAmongAllOf(T... items) {
        return new AutocompleteDataSet<T>(
                List.of(items),
                List.of(AutocompleteConstraint.oneAmongAllOf(items))
        );
    }

    /**
     * Creates an {@link AutocompleteDataSet} with the given elements,
     * and the constraint that all given elements may exist any number of times in a command.
     */
    @SafeVarargs
    public static <T> AutocompleteDataSet<T> anyNumberOf(T... items) {
        return new AutocompleteDataSet<T>(
                List.of(items),
                List.of()
        );
    }

    /**
     * Concatenates all provided {@link AutocompleteDataSet}s.
     */
    @SafeVarargs
    public static <T> AutocompleteDataSet<T> concat(AutocompleteDataSet<T>... sets) {
        return new AutocompleteDataSet<T>(
                Arrays.stream(sets).flatMap(Collection::stream).collect(Collectors.toList()),
                Arrays.stream(sets).flatMap(s -> s.constraints.stream()).collect(Collectors.toList())
        );
    }

    /**
     * Returns a copy of the current instance.
     */
    public AutocompleteDataSet<T> copy() {
        return new AutocompleteDataSet<T>(this, constraints);
    }



    /**
     * Updates the current set to include {@code dependencies}, with the condition that
     * "some element in {@code this} current set exists" is a prerequisite for
     * elements in {@code dependencies} to exist in a command.
     */
    @SafeVarargs
    public final AutocompleteDataSet<T> addDependents(AutocompleteDataSet<T>... dependencies) {

        AutocompleteDataSet<T> mergedDependencies = AutocompleteDataSet.concat(dependencies);

        // Create a dependency array
        // - The unchecked cast is required for generics since generic arrays cannot be made.
        @SuppressWarnings("unchecked")
        T[] dependencyArray = mergedDependencies.toArray((T[]) new Object[mergedDependencies.size()]);

        // Add the constraints to enforce dependency relationship
        this.addConstraint(AutocompleteConstraint.anyOf(this.stream()
                .map(item -> AutocompleteConstraint.where(item).isPrerequisiteFor(dependencyArray))
                .collect(Collectors.toList())
        ));

        // Once done, add all elements and constraints, ordered after existing elements in the set.
        this.addElements(mergedDependencies.getElements());
        this.addConstraints(mergedDependencies.getConstraints());

        return this;
    }


    /**
     * Adds a given constraint to the evaluation rules.
     *
     * @param constraint The constraint to add.
     * @return A reference to {@code this} instance, useful for chaining.
     */
    public AutocompleteDataSet<T> addConstraint(AutocompleteConstraint<? super T> constraint) {
        this.constraints.add(constraint);
        return this;
    }

    /**
     * Removes a given constraint from the evaluation rules.
     *
     * @param constraint The constraint to remove.
     * @return A reference to {@code this} instance, useful for chaining.
     */
    public AutocompleteDataSet<T> removeConstraint(AutocompleteConstraint<? super T> constraint) {
        this.constraints.remove(constraint);
        return this;
    }

    /**
     * Adds a given collection of constraints to the evaluation rules.
     *
     * @param constraints The constraints to add.
     * @return A reference to {@code this} instance, useful for chaining.
     */
    public AutocompleteDataSet<T> addConstraints(Collection<AutocompleteConstraint<? super T>> constraints) {
        this.constraints.addAll(constraints);
        return this;
    }

    /**
     * Removes a given collection of constraints from the evaluation rules.
     *
     * @param constraints The constraints to remove.
     * @return A reference to {@code this} instance, useful for chaining.
     */
    public AutocompleteDataSet<T> removeConstraints(Collection<AutocompleteConstraint<? super T>> constraints) {
        this.constraints.removeAll(constraints);
        return this;
    }

    /**
     * Returns the current set of constraints applied for evaluating possible auto-completions.
     */
    public Set<AutocompleteConstraint<? super T>> getConstraints() {
        return Collections.unmodifiableSet(constraints);
    }



    /**
     * Adds the item to the set.
     * Equivalent to {@link #add}, but returns {@code this}, so is useful for chaining.
     */
    public AutocompleteDataSet<T> addElement(T e) {
        this.add(e);
        return this;
    }

    /**
     * Removes the item from the set.
     * Equivalent to {@link #remove}, but returns {@code this}, so is useful for chaining.
     */
    public AutocompleteDataSet<T> removeElement(T e) {
        this.remove(e);
        return this;
    }

    /**
     * Adds the items to the set.
     * Equivalent to {@link #addAll}, but returns {@code this}, so is useful for chaining.
     */
    public AutocompleteDataSet<T> addElements(Collection<? extends T> e) {
        this.addAll(e);
        return this;
    }

    /**
     * Removes the items from the set.
     * Equivalent to {@link #removeAll}, but returns {@code this}, so is useful for chaining.
     */
    public AutocompleteDataSet<T> removeElements(Collection<? extends T> e) {
        this.removeAll(e);
        return this;
    }

    /**
     * Returns the elements in this instance in a new {@link Set} instance.
     * Properties like iteration order are preserved.
     */
    public Set<T> getElements() {
        return new LinkedHashSet<>(this);
    }


    /**
     * Returns the elements remaining by supposing {@code existingElements} are present and applying all
     * configured {@link AutocompleteConstraint}s, as a new {@link Set} instance.
     */
    public Set<T> getElementsAfterConsuming(Set<? extends T> existingElements) {
        Set<T> resultsSet = new LinkedHashSet<>();
        for (T item: this) {
            if (constraints.stream().allMatch(c -> c.isAllowed(item, existingElements))) {
                resultsSet.add(item);
            }
        }
        return resultsSet;
    }


    @Override
    public boolean equals(Object o) {

        // instanceof checks null implicitly.
        if (!(o instanceof AutocompleteDataSet)) {
            return false;
        }

        AutocompleteDataSet<?> otherSet = (AutocompleteDataSet<?>) o;

        return super.equals(o) && this.constraints.equals(otherSet.constraints);
    }
}
