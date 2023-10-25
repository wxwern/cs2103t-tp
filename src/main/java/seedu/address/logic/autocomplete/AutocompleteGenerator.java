package seedu.address.logic.autocomplete;

import java.util.function.BiFunction;
import java.util.stream.Stream;

import seedu.address.model.Model;

/**
 * Creates a generator based on the given supplier or reference commands, so as it can generate
 * auto-completions only when requested.
 */
public class AutocompleteGenerator {

    public static final AutocompleteGenerator NO_RESULTS = new AutocompleteGenerator(Stream.empty());

    private final BiFunction<String, Model, Stream<String>> resultEvaluationFunction;

    public AutocompleteGenerator(Stream<String> referenceCommands) {
        resultEvaluationFunction = (c, m) -> AutocompleteUtil.generateCompletions(c, referenceCommands);
    }

    public AutocompleteGenerator(AutocompleteSupplier supplier) {
        resultEvaluationFunction = (c, m) -> AutocompleteUtil.generateCompletions(c, supplier, m);
    }

    public Stream<String> generateCompletions(String command) {
        return resultEvaluationFunction.apply(command, null);
    }

    public Stream<String> generateCompletions(String command, Model model) {
        return resultEvaluationFunction.apply(command, model);
    }
}
