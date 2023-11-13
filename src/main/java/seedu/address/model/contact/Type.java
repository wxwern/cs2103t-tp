package seedu.address.model.contact;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.EnumUtil;

/**
 * Represents the type of {@code Contact} instances.
 */
public enum Type {

    ORGANIZATION("organization"),
    RECRUITER("recruiter");

    public static final String MESSAGE_CONSTRAINTS = "Contact type must be 'organization' or 'recruiter'.";

    private final String textRepresentation;

    Type(String textRepresentation) {
        requireNonNull(textRepresentation);
        this.textRepresentation = textRepresentation;
    }

    /**
     * Returns the {@code ContactType} enum as a string representation. This is reversible, i.e., the string
     * representation here can be used to re-obtain the {@code ContactType} enum by using {@link #fromString}.
     *
     * @return A string representation of the contact type.
     */
    @Override
    public String toString() {
        return this.textRepresentation;
    }

    /**
     * Returns a corresponding {@code ContactType} enum value matching the given text representation of it.
     *
     * @param textRepresentation The text representation of the {@code ContactType}.
     * @return The corresponding {@code ContactType}.
     * @throws IllegalArgumentException if the given input does not represent any known {@code ContactType}.
     */
    public static Type fromString(String textRepresentation) throws IllegalArgumentException {
        return EnumUtil.lookupByToString(Type.class, textRepresentation);
    }

    /**
     * Verifies if the given input is a valid contact type.
     *
     * @param textRepresentation The text representation of the {@code Type}.
     * @return Whether the contact type matches a known value.
     */
    public static boolean isValidType(String textRepresentation) {
        return EnumUtil.hasMatchingToString(Type.class, textRepresentation);
    }

}
