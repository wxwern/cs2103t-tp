package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Flag FLAG_NAME = new Flag("n/");
    public static final Flag FLAG_PHONE = new Flag("p/");
    public static final Flag FLAG_EMAIL = new Flag("e/");
    public static final Flag FLAG_ADDRESS = new Flag("a/");
    public static final Flag FLAG_TAG = new Flag("t/");

}
