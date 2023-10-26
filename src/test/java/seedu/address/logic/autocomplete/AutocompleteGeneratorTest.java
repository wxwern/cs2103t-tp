package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Flag;

public class AutocompleteGeneratorTest {

    @Test
    public void generateCompletions_usingGivenExpectedCommands_correctResult() {
        List<String> sourceList = List.of(
                "abacus",
                "ad free",
                "add",
                "add milk",
                "add coffee",
                "almond",
                "ate cake",
                "bake cake",
                "cadence",
                "cupcake"
        );
        List<String> resultList = List.of(
                "ad free",
                "add",
                "add milk",
                "add coffee",
                "cadence",
                "almond"
        );

        assertEquals(
                resultList,
                new AutocompleteGenerator(sourceList.stream())
                        .generateCompletions("ad")
                        .collect(Collectors.toList())
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
                List.of(
                    Set.of(flagA1, flagA2),
                    Set.of(flagA3)
                ),
                List.of(flagB, flagC1, flagC2),
                Map.of(
                        flagA3, m -> List.of("apple", "banana", "car")
                )
        );

        // autocomplete: -a
        assertEquals(
                List.of(
                        "cmd --aaa",
                        "cmd --abc",
                        "cmd --adg"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -a")
                        .collect(Collectors.toList())
        );

        // autocomplete: -b
        assertEquals(
                List.of(
                        "cmd --book",
                        "cmd --abc"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -b")
                        .collect(Collectors.toList())
        );
        assertEquals(
                List.of(
                        "cmd --aaa --book"
                        // --abc no longer suggested when --aaa is present
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --aaa -b")
                        .collect(Collectors.toList())
        );
        assertEquals(
                List.of(), // leading space yields no results since it's suggesting the <value> part
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --adg -b ")
                        .collect(Collectors.toList())
        );

        // autocomplete: --adg <value>
        assertEquals(
                List.of(
                        "cmd -b --adg apple",
                        "cmd -b --adg banana",
                        "cmd -b --adg car"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -b --adg ")
                        .collect(Collectors.toList())
        );
        assertEquals(
                List.of("cmd -b --adg banana"),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -b --adg anna")
                        .collect(Collectors.toList())
        );

        // autocomplete: --cd
        assertEquals(
                List.of(
                        "cmd -a x y --cde",
                        "cmd -a x y --code"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -a x y --cd")
                        .collect(Collectors.toList())
        );

        // autocomplete: -o
        assertEquals(
                List.of(
                        "cmd -a x y --code z --book",
                        "cmd -a x y --code z --code" // --code can be repeated
                        // --abc not suggested when -a (alias for --aaa) is present
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -a x y --code z -o")
                        .collect(Collectors.toList())
        );

    }
}
