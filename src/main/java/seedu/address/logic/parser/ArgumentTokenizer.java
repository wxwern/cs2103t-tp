package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * Tokenizes arguments string of the form: {@code preamble <flag> value <flag> value ...}<br>
 *     e.g. {@code some preamble text t/ 11.00 t/ 12.00 k/ m/ July}  where flag are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ArgumentTokenizer {

    /**
     * Tokenizes an arguments string and returns an {@code ArgumentMultimap} object that maps flag to their
     * respective argument values. Only the given flag will be recognized in the arguments string.
     *
     * @param argsString Arguments string of the form: {@code preamble <flag> value <flag> value ...}
     * @param flags      Flags to prioritize tokenizing the arguments string with
     * @return           ArgumentMultimap object that maps flag to their arguments
     */
    public static ArgumentMultimap tokenize(String argsString, Flag... flags) {
        String[] words = splitByWords(argsString);
        return extractArguments(words, flags);
    }


    /**
     * Splits an arguments string into individual words, separated by space.
     *
     * @param argsString Arguments string of the form: {@code preamble <flag> value <flag> value ...}
     * @return The terms formed after splitting the arguments string by the space character.
     */
    private static String[] splitByWords(String argsString) {
        return argsString.split(" ");
    }

    /**
     * Locate all the locations in the words list that represent flags.
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

        // Define a "segment" to be the end of a value or preamble.
        // We prepare a list that marks the upper bound for a segment via an *exclusive* index.
        // In other words, if the list has [3, 5], it means there are two segments with ranges [0,3) and [3,5).
        List<Integer> endOfBoundsIndices = new ArrayList<>();

        endOfBoundsIndices.addAll(findFlagIndices(words, targetedFlags));
        endOfBoundsIndices.add(words.length);

        // Search through the ranges and map flag to their argument values (if any)
        ArgumentMultimap argMultimap = new ArgumentMultimap();
        for (int i = 0; i < endOfBoundsIndices.size(); i++) {

            // Note that the bounds are [start, end), i.e., start <= x < end.
            int start = i == 0 ? 0 : endOfBoundsIndices.get(i - 1);
            int end = endOfBoundsIndices.get(i);

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
