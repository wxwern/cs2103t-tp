package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Formats the given values with the format string, but return null if any of the given values are null or empty.
     *
     * @param format The format string to use.
     * @param values The values to insert into the format string.
     * @return The formatted string, or null if any of the values are null.
     */
    public static String formatWithNullFallback(String format, Object... values) {
        if (format == null) {
            return null;
        }

        for (Object v : values) {
            if (v == null || v.toString().isBlank()) {
                return null;
            }
        }

        return String.format(format, values);
    }

    /**
     * Returns true if the <code>inputString</code> is a fuzzy match of the <code>targetString</code>,
     * false otherwise.
     *
     * <p>
     * A fuzzy search is an approximate search algorithm. This implementation computes a fuzzy match by determining
     * if there exists a <i>subsequence match</i> in linear time.
     * </p>
     *
     * <p>
     * As an example, <code>"abc"</code> is considered to be a fuzzy match of <code>"aa1b2ccc"</code>, since one may
     * construct the subsequence <code>"abc"</code> by removing extra characters <code>"a1"</code>, <code>"2cc"</code>
     * from <code>aa1b2ccc</code>.
     * </p>
     *
     * @param inputString The partial fuzzy input string.
     * @param targetString The target string to check if the input fuzzily matches with.
     * @return true if the input fuzzy-matches (is fuzzily contained in) the target, false otherwise.
     */
    public static boolean isFuzzyMatch(String inputString, String targetString) {
        if (inputString == null || targetString == null) {
            return inputString == null && targetString == null; // both null => true, otherwise false
        }

        int inputI = 0;
        int targetI = 0;

        while (inputI < inputString.length() && targetI < targetString.length()) {
            char c = inputString.charAt(inputI);
            char t = targetString.charAt(targetI);

            if (c == t) {
                inputI++;
            }
            targetI++;
        }

        return inputI >= inputString.length();
    }

    /**
     * Returns a score representing how close it is to matching characters at the beginning of the target.
     * The higher the value, the better it is.
     *
     * <p>
     * A full prefix match will have the score be the length of the input string, while every gap in the match will
     * subtract 1 from the score.
     * </p>
     */
    public static int getFuzzyMatchScore(String inputString, String targetString) {
        if (inputString == null || targetString == null) {
            return 0;
        }

        int inputI = 0;
        int targetI = 0;

        int errorCount = 0;

        while (inputI < inputString.length() && targetI < targetString.length()) {
            char c = inputString.charAt(inputI);
            char t = targetString.charAt(targetI);

            if (c == t) {
                errorCount += targetI - inputI - errorCount;
                inputI++;
            }
            targetI++;
        }

        if (inputI < inputString.length()) {
            return 0;
        }

        return Math.max(0, inputI - errorCount);
    }
}
