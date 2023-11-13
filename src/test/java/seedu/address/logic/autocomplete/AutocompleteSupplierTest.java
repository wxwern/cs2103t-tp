package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.autocomplete.components.AutocompleteConstraint;
import seedu.address.logic.autocomplete.components.AutocompleteItemSet;
import seedu.address.logic.autocomplete.components.PartitionedCommand;
import seedu.address.logic.parser.Flag;

public class AutocompleteSupplierTest {

    private static final Flag FLAG_A = new Flag("a");
    private static final Flag FLAG_B = new Flag("b");
    private static final Flag FLAG_C = new Flag("c");
    private static final Flag FLAG_D = new Flag("d");
    private static final Flag FLAG_E = new Flag("e");
    private static final Flag FLAG_F = new Flag("f");

    private static final List<String> LIST_A = List.of("a");
    private static final List<String> LIST_B = List.of("b", "b");
    private static final List<String> LIST_C = List.of("c", "c", "c");
    private static final List<String> LIST_EMPTY = List.of();

    @Test
    public void getAllPossibleFlags() {
        var supplier = AutocompleteSupplier.fromUniqueFlags(FLAG_A, FLAG_B);
        assertEquals(Set.of(FLAG_A, FLAG_B), supplier.getAllPossibleFlags());

        supplier = AutocompleteSupplier.fromRepeatableFlags(FLAG_A, FLAG_B);
        assertEquals(Set.of(FLAG_A, FLAG_B), supplier.getAllPossibleFlags());

        supplier = AutocompleteSupplier.from(
                AutocompleteItemSet.onceForEachOf(FLAG_A, FLAG_B),
                AutocompleteItemSet.anyNumberOf(FLAG_C, FLAG_D)
        );
        assertEquals(Set.of(FLAG_A, FLAG_B, FLAG_C, FLAG_D), supplier.getAllPossibleFlags());
    }

    @Test
    public void getOtherPossibleFlagsAsideFromFlagsPresent() {
        // Unique flags only
        var supplier = AutocompleteSupplier.fromUniqueFlags(FLAG_A, FLAG_B);
        assertEquals(Set.of(FLAG_A, FLAG_B), supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of()));
        assertEquals(
                Set.of(FLAG_A),
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_B))
        );

        // Repeatable flags only
        supplier = AutocompleteSupplier.fromRepeatableFlags(FLAG_A, FLAG_B);
        assertEquals(Set.of(FLAG_A, FLAG_B), supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of()));
        assertEquals(
                Set.of(FLAG_A, FLAG_B),
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_A, FLAG_B))
        );

        // Mixed flags
        supplier = AutocompleteSupplier.from(
                AutocompleteItemSet.onceForEachOf(FLAG_A, FLAG_B),
                AutocompleteItemSet.anyNumberOf(FLAG_C, FLAG_D)
        );
        assertEquals(
                Set.of(FLAG_A, FLAG_B, FLAG_C, FLAG_D),
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of())
        );
        assertEquals(
                Set.of(FLAG_A, FLAG_C, FLAG_D),
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_B, FLAG_D))
        );

        // Mixed advanced combination.
        supplier = AutocompleteSupplier.from(
                AutocompleteItemSet.concat(
                        AutocompleteItemSet.onceForEachOf(FLAG_A, FLAG_B),
                        AutocompleteItemSet.anyNumberOf(FLAG_C, FLAG_D)
                ).addConstraints(List.of(
                        AutocompleteConstraint.oneAmongAllOf(FLAG_A, FLAG_B), // A & B cannot coexist
                        AutocompleteConstraint.oneAmongAllOf(FLAG_B, FLAG_C) // B & C cannot coexist
                ))
        );

        assertEquals(
                Set.of(FLAG_C, FLAG_D), // A is present -> A, B cannot be present again
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_A))
        );
        assertEquals(
                Set.of(FLAG_A, FLAG_D), // C is present -> B, C cannot be present again
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_C))
        );
        assertEquals(
                Set.of(FLAG_A, FLAG_B, FLAG_C, FLAG_D), // D is present -> no impact
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_D))
        );
        assertEquals(
                Set.of(FLAG_D), // B is present -> A, B, C cannot be present again;
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_D, FLAG_B))
        );
        assertEquals(
                Set.of(FLAG_D), // All present somehow -> only D can be repeated.
                supplier.getOtherPossibleFlagsAsideFromFlagsPresent(Set.of(FLAG_A, FLAG_B, FLAG_C, FLAG_D))
        );
    }

    @Test
    public void getValidValues() {
        var supplier = new AutocompleteSupplier(
                AutocompleteItemSet.concat(
                        AutocompleteItemSet.onceForEachOf(FLAG_A, FLAG_B, FLAG_C),
                        AutocompleteItemSet.anyNumberOf(FLAG_D)
                ).addConstraint(
                        AutocompleteConstraint.oneAmongAllOf(FLAG_A, FLAG_B) // A & B cannot coexist
                ),
                Map.of(
                        FLAG_A, (c, m) -> LIST_A.stream(),
                        FLAG_B, (c, m) -> LIST_B.stream(),
                        FLAG_C, (c, m) -> LIST_C.stream(),
                        FLAG_D, (c, m) -> LIST_EMPTY.stream(),
                        FLAG_F, (c, m) -> Stream.of(m.toString())
                )
        );

        var emptyCommand = new PartitionedCommand("");

        // Should use the lambda's values
        assertEquals(LIST_A, supplier.getValidValuesForFlag(FLAG_A, emptyCommand, null)
                .get().collect(Collectors.toList()));
        assertEquals(LIST_B, supplier.getValidValuesForFlag(FLAG_B, emptyCommand, null)
                .get().collect(Collectors.toList()));
        assertEquals(LIST_C, supplier.getValidValuesForFlag(FLAG_C, emptyCommand, null)
                .get().collect(Collectors.toList()));
        assertEquals(LIST_EMPTY, supplier.getValidValuesForFlag(FLAG_D, emptyCommand, null)
                .get().collect(Collectors.toList()));
        assertEquals(LIST_EMPTY, supplier.getValidValuesForFlag(FLAG_E, emptyCommand, null)
                .get().collect(Collectors.toList()));

        // NPEs should be caught if the lambda does not handle it
        assertEquals(LIST_EMPTY, supplier.getValidValuesForFlag(FLAG_F, emptyCommand, null)
                .get().collect(Collectors.toList()));
    }

}
