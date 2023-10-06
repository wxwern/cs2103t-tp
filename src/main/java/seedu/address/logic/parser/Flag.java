package seedu.address.logic.parser;

/**
 * A flag is an argument in and of itself. It functions as a option specifier, or as a marker for the beginning of an
 * command argument.
 * E.g. 't/' in 'add James t/ friend'.
 */
public class Flag {
    private final String name;

    public Flag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Flag)) {
            return false;
        }

        Flag otherFlag = (Flag) other;
        return name.equals(otherFlag.name);
    }
}
