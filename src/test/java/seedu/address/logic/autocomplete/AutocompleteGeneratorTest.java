package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.autocomplete.components.AutocompleteConstraint;
import seedu.address.logic.autocomplete.components.AutocompleteItemSet;
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

        // Generates, in the correct order, the correct set of completions from the source list and the given input,
        // by subsequence matching.
        assertEquals(
                resultList,
                new AutocompleteGenerator(sourceList::stream)
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

        AutocompleteSupplier supplier = AutocompleteSupplier.from(
                AutocompleteItemSet.concat(
                        AutocompleteItemSet.onceForEachOf(flagA1, flagA2, flagA3),
                        AutocompleteItemSet.anyNumberOf(flagB, flagC1, flagC2)
                ).addConstraint(
                        AutocompleteConstraint.oneAmongAllOf(flagA1, flagA2)
                )
        ).configureValueMap(map -> {
            map.put(flagA3, (c, m) -> Stream.of("apple", "banana", "car"));
            map.put(flagC1, (c, m) -> null);
            map.put(flagC2, null);
        });

        // autocomplete: -a
        // - flag subsequence matching (same priority results returned in original order)
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
        // - flag subsequence matching (prefers results closer to prefix)
        assertEquals(
                List.of(
                        "cmd --book",
                        "cmd --abc"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -b")
                        .collect(Collectors.toList())
        );
        // - flag subsequence matching (performs constraint validation)
        assertEquals(
                List.of(
                        "cmd --aaa --book"
                        // --abc no longer suggested when --aaa is present
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --aaa -b")
                        .collect(Collectors.toList())
        );

        // autocomplete: -b <value>
        // - flag value result instant generation (none provided by supplier)
        assertEquals(
                List.of(), // leading space yields no results since it's suggesting the <value> part
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --adg -b ")
                        .collect(Collectors.toList())
        );

        // autocomplete: --adg <value>
        // - flag value result instant generation (some provided by supplier, immediately given after finishing flag)
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
        // - flag value result matching (performs subsequence match, works with values before it)
        assertEquals(
                List.of("cmd -b --adg banana"),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd -b --adg anna")
                        .collect(Collectors.toList())
        );

        // autocomplete: --cd
        // - flag subsequence generation (performs subsequence match, works with values before it)
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
        // - flag subsequence generation (performs subsequence match, constraint validation)
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

        // autocomplete: --cde <flag>
        // - flag suggestion instant generation (immediately supply flags after a part with no value) (null type 1)
        assertEquals(
                List.of(
                        // Rationale: --cde does not accept values (FlagValueSupplier *returns* null),
                        // so a next flag is immediately suggested
                        "cmd --cde --aaa",
                        "cmd --cde --abc",
                        "cmd --cde --adg",
                        "cmd --cde --book",
                        "cmd --cde --cde",
                        "cmd --cde --code"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --cde ")
                        .collect(Collectors.toList())
        );

        // autocomplete: --code <flag>
        // - flag suggestion instant generation (immediately supply flags after a part with no value) (null type 2)
        assertEquals(
                List.of(
                        // Rationale: --code does not accept values (FlagValueSupplier *is* null),
                        // so a next flag is immediately suggested
                        "cmd --code --aaa",
                        "cmd --code --abc",
                        "cmd --code --adg",
                        "cmd --code --book",
                        "cmd --code --cde",
                        "cmd --code --code"
                ),
                new AutocompleteGenerator(supplier)
                        .generateCompletions("cmd --code ")
                        .collect(Collectors.toList())
        );
    }
}
