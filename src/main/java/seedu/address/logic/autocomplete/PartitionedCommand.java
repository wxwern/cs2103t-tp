package seedu.address.logic.autocomplete;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Flag;

/**
 * A wrapper around a command string as partitions, useful for computing results for autocompletion.
 */
public class PartitionedCommand {
    private final String name;
    private final String middleText;
    private final String trailingText;

    private final boolean hasFlagSyntaxPrefixInTrailingText;
    private final List<String> confirmedFlagStrings;

    /**
     * Initializes and prepares the given command as distinct partitions.
     */
    PartitionedCommand(String partialCommand) {
        List<String> words = List.of(partialCommand.split(" ", -1)); // -1 stops stripping adjacent spaces.

        if (words.size() <= 1) {
            this.name = "";
            this.middleText = "";
            this.trailingText = words.isEmpty() ? "" : words.get(0);
            this.hasFlagSyntaxPrefixInTrailingText = false;
            this.confirmedFlagStrings = List.of();
            return;
        }

        // Compute whether the trailing term is likely a flag part.
        this.hasFlagSyntaxPrefixInTrailingText =
                words.get(words.size() - 1).startsWith(Flag.DEFAULT_PREFIX)
                        || words.get(words.size() - 1).startsWith(Flag.DEFAULT_ALIAS_PREFIX);

        // Compute all matched flags.
        this.confirmedFlagStrings = words.subList(1, words.size() - 1)
                .stream()
                .filter(Flag::isFlagSyntax)
                .collect(Collectors.toUnmodifiableList());

        // Compute rightmost index of flag.
        int lastKnownFlagIndex = words.size() - 1;
        while (lastKnownFlagIndex > 0) {
            if (Flag.isFlagSyntax(words.get(lastKnownFlagIndex))) {
                break;
            }
            lastKnownFlagIndex--;
        }

        // Compute trailing text's start index. It must be at least 1 (i.e., after the command name).
        //
        // - if the rightmost term is possibly a new flag, the trailing text should be that one.
        //     e.g., "name --flag1 abc def --fl" should be split to ("name", "--flag abc def", "--fl")
        //
        // - otherwise, it should be the text beyond the last known flag location.
        //     e.g., "name --flag1 abc def" should be split to ("name", "--flag", "abc def")
        int trailingTextStartIndex = Math.max(
                1,
                this.hasFlagSyntaxPrefixInTrailingText
                        ? words.size() - 1
                        : lastKnownFlagIndex + 1
        );

        // Compute the name, middle, trailing parts by splicing the appropriate ranges.
        this.name = words.get(0);
        this.middleText = String.join(" ", words.subList(1, trailingTextStartIndex));
        this.trailingText = String.join(" ", words.subList(trailingTextStartIndex, words.size()));
    }

    /**
     * Gets the command name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the leading text.
     * It is the front part of the command that should not be modified as part of autocompletion.
     */
    public String getLeadingText() {
        return String.join(" ", List.of(name, middleText));
    }

    /**
     * Gets the middle text.
     * It is the part after <code>name</code> but before <code>leadingText</code>.
     */
    public String getMiddleText() {
        return middleText;
    }

    /**
     * Gets the leading text.
     * It is the part of the text that may be autocompleted, either by extending or replacing it.
     */
    public String getTrailingText() {
        return trailingText;
    }

    /**
     * Gets the trailing part of the text that should be replaced with an autocompletion value.
     * Equivalent to {@link #getTrailingText}.
     */
    public String getAutocompletableText() {
        return getTrailingText();
    }

    /**
     * Gets the rightmost flag string detected in this command.
     */
    public Optional<String> getLastConfirmedFlagString() {
        return confirmedFlagStrings.isEmpty()
                ? Optional.empty()
                : Optional.of(confirmedFlagStrings.get(confirmedFlagStrings.size() - 1));
    }

    /**
     * Gets all flags detected in this command.
     */
    public List<String> getConfirmedFlagStrings() {
        return this.confirmedFlagStrings;
    }

    /**
     * Returns true if the trailing text seems to have signs of a flag prefix, false otherwise.
     */
    public boolean hasFlagSyntaxPrefixInTrailingText() {
        return this.hasFlagSyntaxPrefixInTrailingText;
    }

    /**
     * Returns the current command as a string, but with the trailing term replaced.
     * This is useful to generate an autocompleted result.
     */
    public String toStringWithNewTrailingTerm(String newTrailingTerm) {
        return Stream.of(name, middleText, newTrailingTerm)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "))
                .trim();
    }

    /**
     * Returns the current command as a string.
     * This is usually equivalent to the original input string.
     */
    @Override
    public String toString() {
        return Stream.of(name, middleText, trailingText)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(" "))
                .trim();
    }

    /**
     * Returns true if the command partitions for this and the other command are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        seedu.address.logic.autocomplete.PartitionedCommand
                other = (seedu.address.logic.autocomplete.PartitionedCommand) o;
        return Objects.equals(name, other.name)
                && Objects.equals(middleText, other.middleText)
                && Objects.equals(trailingText, other.trailingText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, middleText, trailingText);
    }
}
