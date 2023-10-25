package seedu.address.logic.autocomplete;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import seedu.address.model.Model;

/**
 * Creates a generator based on the given supplier or reference commands, so as it can generate
 * auto-completions only when requested.
 */
public class AutocompleteGenerator {

    /** An autocompletion generator that generates no results. */
    public static final AutocompleteGenerator NO_RESULTS = new AutocompleteGenerator(Stream.empty());

    private final BiFunction<String, Model, Stream<String>> resultEvaluationFunction;

    /**
     * Constructs an autocomplete generator based on the given set of reference full command strings.
     */
    public AutocompleteGenerator(Stream<String> referenceCommands) {
        resultEvaluationFunction = (c, m) -> AutocompleteUtil.generateCompletions(c, referenceCommands);
    }

    /**
     * Constructs an autocomplete generator based on the given supplier.
     */
    public AutocompleteGenerator(AutocompleteSupplier supplier) {
        resultEvaluationFunction = (c, m) -> AutocompleteUtil.generateCompletions(c, supplier, m);
    }

    /**
     * Generates a stream of completions based on the partial command given and no model.
     * Note that omitting the model may limit the ability to return useful results.
     */
    public Stream<String> generateCompletions(String command) {
        return resultEvaluationFunction.apply(command, null);
    }

    /**
     * Generates a stream of completions based on the partial command given and the model.
     */
    public Stream<String> generateCompletions(String command, Model model) {
        return resultEvaluationFunction.apply(command, model);
    }
}
