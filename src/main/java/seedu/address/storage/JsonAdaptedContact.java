package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Email;
import seedu.address.model.person.Id;
import seedu.address.model.person.Name;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Recruiter;
import seedu.address.model.person.Status;
import seedu.address.model.person.Type;
import seedu.address.model.person.Url;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String type;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private String status;
    private String position;
    private final String id;
    private final String url;
    private String oid;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedId> rids = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("type") String type,
                              @JsonProperty("name") String name, @JsonProperty("id") String id,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("url") String url, @JsonProperty("address") String address,
                              @JsonProperty("status") String status, @JsonProperty("position") String position,
                              @JsonProperty("oid") String oid, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("rid") List<JsonAdaptedId> rids) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
        this.status = status;
        this.position = position;
        this.oid = oid;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (rids != null) {
            this.rids.addAll(rids);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        if (source.getType() == Type.ORGANIZATION) {
            status = ((Organization) source).getStatus().applicationStatus;
            position = ((Organization) source).getPosition().jobPosition;
            oid = "";
            rids.addAll(((Organization) source).getRids().stream()
                    .map(JsonAdaptedId::new)
                    .collect(Collectors.toList()));
        } else if (source.getType() == Type.RECRUITER) {
            status = "";
            position = "";
            Id tmp = ((Recruiter) source).getOrganizationId();
            oid = tmp == null ? null : tmp.value;
        }

        type = source.getType().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        url = source.getUrl().value;
        address = source.getAddress().value;
        id = source.getId().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType() throws IllegalValueException {
        final List<Tag> contactTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            contactTags.add(tag.toModelType());
        }

        final List<Id> contactRids = new ArrayList<>();
        for (JsonAdaptedId rid : rids) {
            contactRids.add(rid.toModelType());
        }

        // Type#fromString implicitly returns UNKNOWN if type is null. May change if UNKNOWN is removed in the future.
        final Type modelType = Type.fromString(type);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Id.class.getSimpleName()));
        }
        if (!Id.isValidId(id)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
        }
        final Id modelId = new Id(id);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (url == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(url)) {
            throw new IllegalValueException(Url.MESSAGE_CONSTRAINTS);
        }
        final Url modelUrl = new Url(url);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(contactTags);

        final Set<Id> modelRids = new HashSet<>(contactRids);

        switch (modelType) {
        case ORGANIZATION: {

            final Status modelStatus = status == null ? new Status() : new Status(status);

            final Position modelPosition = position == null ? new Position() : new Position(position);

            return new Organization(
                    modelName, modelId, modelPhone, modelEmail, modelUrl, modelAddress,
                    modelTags, modelStatus, modelPosition, modelRids
            );
        }
        case RECRUITER: {
            final Id modelOid = oid == null ? null : new Id(oid);

            return new Recruiter(
                    modelName, modelId, modelPhone, modelEmail, modelUrl, modelAddress,
                    modelTags, modelOid
            );
        }
        default:
            return new Contact(modelName, modelId, modelPhone, modelEmail, modelUrl, modelAddress, modelTags);
        }
    }

}
