package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tokenizes arguments string of the form: {@code preamble <flag> value <flag> value ...}<br>
 *     e.g. {@code some preamble text -t 11.00 -t 12.00 -k -m July} where flags are {@code -t -k -m}.<br>
 *
 * <ol>
 *     <li>
 *         An argument's (flag's) value can be an empty string, e.g., the value of {@code -k}
 *         in the above example.
 *     </li>
 *     <li>
 *         Leading and trailing whitespaces of an argument value will be discarded.
 *     </li>
 *     <li>
 *         Flags must be surrounded by whitespace on both sides to be tokenized as flags.
 *     </li>
 *     <li>
 *         An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *         in the above example.
 *     </li>
 * </ol>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps flag to their
     * respective argument values. Only the given flags will be tokenized and added to the multimap, while
     * extraneous flags found that match the expected syntax (see: {@link Flag#isFlagSyntax}}
     * will throw an exception.
     *
     * <p>
     * Unlike {@link #autoTokenize(String, Flag...)}, this <b>will throw an error</b> when unspecified flags
     * are found. This means you must provide all the necessary flags you intend to use via the {@code flags}
     * parameter.
     * </p>
     *
     * <p>
     * Calling this method is equivalent to using the results from
     * {@link ArgumentTokenizer#autoTokenize(String, Flag...)} and verifying there exists no extraneous flags with
     * {@link ArgumentMultimap#verifyNoExtraneousFlagsOnTopOf(Flag...)}.
     * </p>
     *
     * @param argsString Arguments string of the form: {@code preamble <flag> value <flag> value ...}.
     * @param flags      Flags to tokenize the arguments string with.
     * @return ArgumentMultimap object that maps flag to their arguments.
     * @throws ParseException if there are extraneous flags detected on top of the provided flags.
     *
     * @see #autoTokenize(String, Flag...)
     */
    public static ArgumentMultimap tokenize(String argsString, Flag... flags) throws ParseException {
        ArgumentMultimap multimap = autoTokenize(argsString, flags);
        multimap.verifyNoExtraneousFlagsOnTopOf(flags);
        return multimap;
    }

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps flag to their
     * respective argument. All flags provided via the {@code flags} parameter, and all unknown flags
     * successfully obtained via {@link Flag#parse}, will in both cases be added to the multimap.
     *
     * <p>
     * Unlike {@link #tokenize(String, Flag...)}, this will not throw an error when unspecified flags are
     * found. In other words, an unknown flag not present in {@code mainFlags} is always accepted.
     * </p>
     *
     * @param argsString Arguments string of the form: {@code preamble <flag> value <flag> value ...}.
     * @param mainFlags  Optional set of primary flags to prioritize tokenizing the arguments string with.
     * @return ArgumentMultimap object that maps flag to their arguments.
     *
     * @see #tokenize(String, Flag...)
     */
    public static ArgumentMultimap autoTokenize(String argsString, Flag... mainFlags) {
        String[] words = splitByWords(argsString);
        return extractArguments(words, mainFlags);
    }

    /**
     * Splits an arguments string into individual words, separated by space.
     *
     * @param argsString Arguments string of the form: {@code preamble <flag> value <flag> value ...}.
     * @return The terms formed after splitting the arguments string by the space character.
     */
    private static String[] splitByWords(String argsString) {
        return argsString.split(" ");
    }

    /**
     * Locates all the locations in the words list that represent flags.
     *
     * @param wordsArray An array of words.
     * @param targetedFlags An array of flags should be checked explicitly.
     * @return The list of indices where a flag can be found.
     */
    private static List<Integer> findFlagIndices(String[] wordsArray, Flag[] targetedFlags) {

        List<Integer> flagIndices = new ArrayList<>();
        for (int i = 0; i < wordsArray.length; i++) {
            String word = wordsArray[i];

            if (Flag.isFlagSyntax(word) || Flag.findMatch(word, targetedFlags).isPresent()) {
                flagIndices.add(i);
            }
        }

        return flagIndices;
    }

    /**
     * Extracts flag and their argument values, and returns an {@code ArgumentMultimap} object that maps the
     * extracted flag to their respective arguments. Flags are extracted based on their zero-based positions in
     * {@code argsString}.
     *
     * @param words           An array of words derived from the arguments string.
     * @param targetedFlags   An array of flags should be checked explicitly.
     * @return                ArgumentMultimap object that maps flags to their arguments.
     */
    private static ArgumentMultimap extractArguments(String[] words, Flag[] targetedFlags) {

        List<String> wordsList = List.of(words);

        // Define an "end of range" to be the end of a value or preamble.
        // We prepare a list that marks the end of ranges via *exclusive* indices (i.e., end index + 1).
        // In other words, if the list has [3, 5], it means there are two ranges [0,3) and [3,5).
        List<Integer> endOfRangeIndices = new ArrayList<>();

        endOfRangeIndices.addAll(findFlagIndices(words, targetedFlags));
        endOfRangeIndices.add(words.length);

        // Search through the ranges and map flag to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < endOfRangeIndices.size(); i++) {

            // Note that the bounds are [start, end), i.e., start <= x < end.
            int start = i == 0 ? 0 : endOfRangeIndices.get(i - 1);
            int end = endOfRangeIndices.get(i);

            if (start >= end) {
                continue;
            }

            // Case 1: Preamble (if we reach here in the first loop iteration).
            if (i == 0) {
                String preamble = String.join(" ", wordsList.subList(start, end)).trim();
                argMultimap.putPreamble(preamble);
                continue;
            }

            // Case 2: Flag + Possible Argument Value (if we reach here in 2nd+ loop iterations).
            String flagString = words[start];
            String valueString = String.join(" ", wordsList.subList(start + 1, end)).trim();

            Flag flag = Flag.findMatch(flagString, targetedFlags)
                    .or(() -> Flag.parseOptional(flagString))
                    .orElseThrow(); // We should never get here since the flags are validated in findFlagIndices.

            argMultimap.put(flag, valueString);
        }

        return argMultimap;
    }

}
