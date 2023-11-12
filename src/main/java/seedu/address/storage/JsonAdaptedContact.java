package seedu.address.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Type;
import seedu.address.model.contact.Url;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.tag.Tag;


/**
 * Jackson-friendly version of {@link Contact}.
 */
class JsonAdaptedContact implements Comparable<JsonAdaptedContact> {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contact's %s field is missing!";

    private final String type;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String id;
    private final String url;
    private String oid;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final List<JsonAdaptedApplication> applications = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedContact} with the given contact details.
     */
    @JsonCreator
    public JsonAdaptedContact(@JsonProperty("type") String type,
                              @JsonProperty("name") String name, @JsonProperty("id") String id,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("url") String url, @JsonProperty("address") String address,
                              @JsonProperty("oid") String oid, @JsonProperty("tags") List<JsonAdaptedTag> tags,
                              @JsonProperty("applications") List<JsonAdaptedApplication> applications) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.url = url;
        this.address = address;
        this.oid = oid;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        if (applications != null) {
            this.applications.addAll(applications);
        }
    }

    /**
     * Converts a given {@code Contact} into this class for Jackson use.
     */
    public JsonAdaptedContact(Contact source) {
        if (source.getType() == Type.ORGANIZATION) {
            oid = null;
        } else if (source.getType() == Type.RECRUITER) {
            Recruiter recruiter = (Recruiter) source;
            oid = recruiter.getOrganizationId()
                    .map(oid -> oid.value)
                    .orElse(null);
        }

        type = source.getType().toString();
        name = source.getName().fullName;
        id = source.getId().value;
        phone = source.getPhone().map(phone -> phone.value).orElse(null);
        email = source.getEmail().map(email -> email.value).orElse(null);
        url = source.getUrl().map(url -> url.value).orElse(null);
        address = source.getAddress().map(address -> address.value).orElse(null);
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        if (source.getType() == Type.ORGANIZATION) {
            Organization org = (Organization) source;
            List<JobApplication> applicationList = Arrays.asList(org.getJobApplications());
            applications.addAll(applicationList.stream()
                    .map(JsonAdaptedApplication::new)
                    .collect(Collectors.toList()));
        }
    }

    /**
     * Returns the id string stored in this {@code JsonAdaptedContact}
     */
    public String getId() {
        return this.id;
    }

    /**
     * Converts this Jackson-friendly adapted contact object into the model's {@code Contact} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contact.
     */
    public Contact toModelType(ReadOnlyAddressBook reference) throws IllegalValueException {
        final List<Tag> contactTags = new ArrayList<>();
        final List<JobApplication> jobApplications = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            contactTags.add(tag.toModelType());
        }
        for (JsonAdaptedApplication application: applications) {
            jobApplications.add(application.toModelType(id, name));
        }

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

        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = phone == null ? null : new Phone(phone);

        if (email != null && !Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = email == null ? null : new Email(email);

        if (url != null && !Url.isValidUrl(url)) {
            throw new IllegalValueException(Url.MESSAGE_CONSTRAINTS);
        }
        final Url modelUrl = url == null ? null : new Url(url);

        if (address != null && !Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = address == null ? null : new Address(address);

        final Set<Tag> modelTags = new HashSet<>(contactTags);

        if (type == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Type.class.getSimpleName()));
        }
        if (!Type.isValidType(type)) {
            throw new IllegalValueException(Type.MESSAGE_CONSTRAINTS);
        }

        final Type modelType = Type.fromString(type);

        switch (modelType) {
        case ORGANIZATION: {
            return new Organization(
                    modelName, modelId, modelPhone, modelEmail, modelUrl, modelAddress,
                    modelTags, jobApplications
            );
        }
        case RECRUITER: {
            if (oid != null && !Id.isValidId(oid)) {
                throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS);
            }
            final Id modelOid = oid == null ? null : new Id(oid);
            final Organization modelOrg;
            if (modelOid == null) {
                modelOrg = null;
            } else {
                Contact contact = reference.getContactById(modelOid);
                if (contact == null || contact.getType() != Type.ORGANIZATION) {
                    throw new IllegalValueException(Recruiter.MESSAGE_INVALID_ORGANIZATION);
                }
                modelOrg = (Organization) contact;
                assert modelOrg.getId().equals(modelOid);
            }

            return new Recruiter(
                    modelName, modelId, modelPhone, modelEmail, modelUrl, modelAddress,
                    modelTags, modelOrg
            );
        }
        default:
            assert false : "We should not reach this stage - there is a developer error and the contact type "
                    + modelType + "is not handled!";

            throw new IllegalStateException();
        }
    }

    @Override
    public int compareTo(JsonAdaptedContact o) {
        return Type.fromString(this.type).compareTo(Type.fromString(o.type));
    }
}
