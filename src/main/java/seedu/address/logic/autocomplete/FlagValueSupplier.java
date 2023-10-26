package seedu.address.logic.autocomplete;


import java.util.function.Function;
import java.util.stream.Stream;

import seedu.address.model.Model;

/**
 * Supplies a list of values for auto-completing a flag's value, given the model.
 *
 * <p>
 * Both the input and output {@link Model} may be null.
 * </p>
 * <ul>
 *     <li>If the model is available, use the available information to best compute a stream of values.</li>
 *     <li>If the model is null, make do with the available information to return the result.</li>
 * </ul>
 * <ul>
 *     <li>If there are no known values to complete, return an empty list.</li>
 *     <li>If there are no values to consider at all (e.g., flag doesn't accept values), return null.</li>
 * </ul>
 * */
public interface FlagValueSupplier extends Function<Model, Stream<String>> {
    // Inherited implementation.
}
