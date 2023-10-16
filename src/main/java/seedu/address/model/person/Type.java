package seedu.address.model.person;

import seedu.address.model.person.exceptions.InvalidContactTypeStringException;

/**
 * Represents the type of {@code Contact} instances.
 */
public enum Type {
    ORGANIZATION("organization"),
    UNKNOWN("unknown");


    private final String textRepresentation;

    Type(String textRepresentation) {
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
     * Returns a corresponding {@code ContactType} enum value matching the given string representation of it.
     *
     * @param textRepresentation The text representation of the {@code ContactType}.
     * @return The corresponding {@code ContactType}.
     * @throws InvalidContactTypeStringException if the given input does not represent any known {@code ContactType}.
     */
    public static Type fromString(String textRepresentation) {
        for (Type type : Type.values()) {
            if (type.textRepresentation.equalsIgnoreCase(textRepresentation)) {
                return type;
            }
        }
        return UNKNOWN;
        // TODO: We should throw an exception instead like the below. We are using UNKNOWN for now for compatibility.
        // throw new InvalidContactTypeStringException(textRepresentation);
    }

}
