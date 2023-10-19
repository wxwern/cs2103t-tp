package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Id;

/**
 * Jackson-friendly version of {@link Id}.
 */
class JsonAdaptedId {

    private final String idName;

    /**
     * Constructs a {@code JsonAdaptedId} with the given {@code idName}.
     */
    @JsonCreator
    public JsonAdaptedId(String idName) {
        this.idName = idName;
    }

    /**
     * Converts a given {@code Id} into this class for Jackson use.
     */
    public JsonAdaptedId(Id source) {
        idName = source.value;
    }

    @JsonValue
    public String getIdName() {
        return idName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Id} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Id toModelType() throws IllegalValueException {
        if (!Id.isValidId(idName)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        return new Id(idName);
    }

}

