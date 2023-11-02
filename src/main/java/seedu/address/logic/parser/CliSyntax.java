package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Flag definitions */
    public static final Flag FLAG_ID = new Flag("id");
    public static final Flag FLAG_ORGANIZATION = new Flag("org");
    public static final Flag FLAG_RECRUITER = new Flag("rec");
    public static final Flag FLAG_APPLICATION = new Flag("application");

    public static final Flag FLAG_NAME = new Flag("name");
    public static final Flag FLAG_PHONE = new Flag("phone");
    public static final Flag FLAG_EMAIL = new Flag("email");
    public static final Flag FLAG_ADDRESS = new Flag("address");
    public static final Flag FLAG_TAG = new Flag("tag");
    public static final Flag FLAG_URL = new Flag("url");
    public static final Flag FLAG_STATUS = new Flag("status");
    public static final Flag FLAG_RECURSIVE = new Flag("recursive");
    public static final Flag FLAG_ORGANIZATION_ID = new Flag("oid");
    public static final Flag FLAG_RECRUITER_ID = new Flag("rid");
    public static final Flag FLAG_TITLE = new Flag("title");
    public static final Flag FLAG_DEADLINE = new Flag("by");
    public static final Flag FLAG_STAGE = new Flag("stage");
    public static final Flag FLAG_DESCRIPTION = new Flag("description");
    public static final Flag FLAG_NOT_APPLIED = new Flag("toapply");
    public static final Flag FLAG_NONE = new Flag("none");
    public static final Flag FLAG_ASCENDING = new Flag("ascending");
    public static final Flag FLAG_DESCENDING = new Flag("descending");
    public static final Flag FLAG_STALE = new Flag("stale");
    public static final Flag FLAG_EARLIEST = new Flag("earliest");
    public static final Flag FLAG_LATEST = new Flag("latest");


}
