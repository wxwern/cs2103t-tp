package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Flag;

public class AutocompleteEngineTest {

    @Test
    public void generateCompletions_usingGivenExpectedCommands_correctResult() {
        assertEquals(
                Set.of(
                        "ad free",
                        "add",
                        "add milk",
                        "add coffee"
                ),
                AutocompleteEngine.generateCompletions("ad", List.of(
                        "abacus",
                        "ad free",
                        "add",
                        "add milk",
                        "add coffee",
                        "almond",
                        "ate cake",
                        "bake cake",
                        "cupcake"
                ).toArray(String[]::new))
        );
    }

    @Test
    public void generateCompletions_usingAutocompleteSupplier_correctResult() {
        // Assumption: Default format is --flag (full), -f (alias).

        Flag flagA1 = new Flag("aaa", "a");
        Flag flagA2 = new Flag("abc");
        Flag flagA3 = new Flag("adg");
        Flag flagB = new Flag("book", "b");
        Flag flagC1 = new Flag("cde", "c");
        Flag flagC2 = new Flag("code");

        AutocompleteSupplier supplier = new AutocompleteSupplier(
                Set.of(
                    Set.of(flagA1, flagA2),
                    Set.of(flagA3)
                ),
                Set.of(flagB, flagC1, flagC2),
                Map.of(
                        flagA3, m -> List.of("apple", "banana", "car")
                )
        );

        // autocomplete: -a
        assertEquals(
                Set.of(
                        "cmd --aaa",
                        "cmd --adg",
                        "cmd --abc"
                ),
                AutocompleteEngine.generateCompletions("cmd -a", supplier, null)
        );

        // autocomplete: -b
        assertEquals(
                Set.of(
                        "cmd --book",
                        "cmd --abc"
                ),
                AutocompleteEngine.generateCompletions("cmd -b", supplier, null)
        );
        assertEquals(
                Set.of(
                        "cmd --aaa --book"
                        // --abc no longer suggested when --aaa is present
                ),
                AutocompleteEngine.generateCompletions("cmd --aaa -b", supplier, null)
        );
        assertEquals(
                Set.of(), // leading space yields no results since it's suggesting the <value> part
                AutocompleteEngine.generateCompletions("cmd --adg -b ", supplier, null)
        );

        // autocomplete: --adg <value>
        assertEquals(
                Set.of(
                        "cmd -b --adg apple",
                        "cmd -b --adg banana",
                        "cmd -b --adg car"
                ),
                AutocompleteEngine.generateCompletions("cmd -b --adg ", supplier, null)
        );
        assertEquals(
                Set.of("cmd -b --adg banana"),
                AutocompleteEngine.generateCompletions("cmd -b --adg anna", supplier, null)
        );

        // autocomplete: --cd
        assertEquals(
                Set.of(
                        "cmd -a x y --cde",
                        "cmd -a x y --code"
                ),
                AutocompleteEngine.generateCompletions("cmd -a x y --cd", supplier, null)
        );

        // autocomplete: -o
        assertEquals(
                Set.of(
                        "cmd -a x y --code z --book",
                        "cmd -a x y --code z --code" // --code can be repeated
                        // --abc not suggested when -a (alias for --aaa) is present
                ),
                AutocompleteEngine.generateCompletions("cmd -a x y --code z -o", supplier, null)
        );

    }
}
