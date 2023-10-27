package seedu.address.logic.autocomplete;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import seedu.address.model.Model;

/**
 * Supplies a list of values for auto-completing a flag's value, given the model.
 *
 * <p>
 * Any implementation of {@link FlagValueSupplier} must conform to the specifications noted on the
 * {@link #apply} method.
 * </p>
 */
public interface FlagValueSupplier extends BiFunction<PartitionedCommand, Model, Stream<String>> {


    /**
     * Returns a stream of possible values that a particular flag could take.
     *
     * <p>
     *     This will always return all possible values - any additional conditional filters like prefix matching
     *     should be up to the receiver of the stream to apply. The {@code currentCommand} field is required
     *     in case the supplier wants to provide entirely different set of suggestions based on the initial input
     *     (e.g., omit suggestions when a numeric index is provided, but provide suggestions for standard text).
     * </p>
     *
     * <p>
     *     There are a two forms of possible return results:
     *     <ul>
     *         <li>
     *             If the returned stream is non-empty, it indicates suggestions where
     *             {@code currentValue} could be replaced with.
     *         </li>
     *         <li>If the returned stream is empty, it indicates the lack of suggestions.</li>
     *         <li>If the returned stream given is null, it indicates this flag should not have a value.</li>
     *     </ul>
     * </p>
     *
     * @param currentCommand The current command typed so far. Should never be null.
     * @param model          The model that can be used. May be null.
     *
     * @return A stream of suggestions (can be an empty stream for "nothing to suggest"),
     *         or null indicating to skip suggestions entirely (for "this should not have any value").
     */
    @Override
    Stream<String> apply(PartitionedCommand currentCommand, Model model);
}
