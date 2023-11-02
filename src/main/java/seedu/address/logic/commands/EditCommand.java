package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_APPLICATION;
import static seedu.address.logic.parser.CliSyntax.FLAG_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.FLAG_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_ORGANIZATION_ID;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_POSITION;
import static seedu.address.logic.parser.CliSyntax.FLAG_STAGE;
import static seedu.address.logic.parser.CliSyntax.FLAG_STATUS;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.logic.parser.CliSyntax.FLAG_TITLE;
import static seedu.address.logic.parser.CliSyntax.FLAG_URL;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.autocomplete.AutocompleteSupplier;
import seedu.address.logic.autocomplete.data.AutocompleteConstraint;
import seedu.address.logic.autocomplete.data.AutocompleteDataSet;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Flag;
import seedu.address.model.Model;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Id;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Organization;
import seedu.address.model.contact.Phone;
import seedu.address.model.contact.Position;
import seedu.address.model.contact.Recruiter;
import seedu.address.model.contact.Status;
import seedu.address.model.contact.Type;
import seedu.address.model.contact.Url;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    private static final AutocompleteDataSet<Flag> AUTOCOMPLETE_SET_STANDARD = AutocompleteDataSet.concat(
            AutocompleteDataSet.onceForEachOf(
                    FLAG_NAME, FLAG_ID,
                    FLAG_PHONE, FLAG_EMAIL, FLAG_ADDRESS, FLAG_URL,
                    FLAG_STATUS, FLAG_POSITION,
                    FLAG_ORGANIZATION_ID
            ),
            AutocompleteDataSet.anyNumberOf(FLAG_TAG)
    );

    public static final AutocompleteDataSet<Flag> AUTOCOMPLETE_SET_APPLICATION = AutocompleteDataSet
            .onceForEachOf(FLAG_APPLICATION)
            .addDependents(
                    AutocompleteDataSet.onceForEachOf(
                            FLAG_TITLE, FLAG_DESCRIPTION, FLAG_DEADLINE, FLAG_STAGE
                    ))
            .addConstraint(
                    AutocompleteConstraint.where(FLAG_APPLICATION).cannotExistAlongsideAnyOf(
                            AUTOCOMPLETE_SET_STANDARD.getElements().toArray(Flag[]::new)
                    )
            );

    public static final AutocompleteSupplier AUTOCOMPLETE_SUPPLIER = AutocompleteSupplier.from(
            AUTOCOMPLETE_SET_STANDARD,
            AUTOCOMPLETE_SET_APPLICATION
    ).configureValueMap(map -> {
        // Add value autocompletion data for:
        map.put(null /* preamble*/, (command, model) -> {

            String partialText = command.getAutocompletableText();
            if (partialText.isEmpty() || StringUtil.isNonZeroUnsignedInteger(partialText)) {
                // Preamble is likely of type Index
                return Stream.empty();

            } else {
                // Preamble is likely of type Id
                return model.getAddressBook()
                        .getContactList()
                        .stream()
                        .map(o -> o.getId().value);
            }
        });
        map.put(FLAG_ORGANIZATION_ID, (command, model) -> model.getAddressBook()
                .getContactList()
                .stream()
                .filter(c -> c.getType() == Type.ORGANIZATION)
                .map(o -> o.getId().value)
        );
    });

    public static final String MESSAGE_ORGANIZATION_USAGE = "Edits an organization.\n"
            + "Parameters: INDEX/ID "
            + "[" + FLAG_NAME + " NAME] "
            + "[" + FLAG_ID + " ID] "
            + "[" + FLAG_PHONE + " PHONE] "
            + "[" + FLAG_EMAIL + " EMAIL] "
            + "[" + FLAG_URL + " URL] "
            + "[" + FLAG_ADDRESS + " ADDRESS] "
            + "[" + FLAG_STATUS + " STATUS] "
            + "[" + FLAG_POSITION + " POSITION] "
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_RECRUITER_USAGE = "Edits a recruiter.\n"
            + "Parameters: INDEX/ID "
            + "[" + FLAG_NAME + " NAME] "
            + "[" + FLAG_ID + " ID] "
            + "[" + FLAG_PHONE + " PHONE] "
            + "[" + FLAG_EMAIL + " EMAIL] "
            + "[" + FLAG_URL + " URL] "
            + "[" + FLAG_ADDRESS + " ADDRESS] "
            + "[" + FLAG_ORGANIZATION_ID + " OID] "
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + FLAG_PHONE + " 91234567 "
            + FLAG_EMAIL + " rexrecruiter@example.com";

    public static final String MESSAGE_APPLICATION_USAGE = "Edits a job application.\n"
            + "Parameters: "
            + FLAG_APPLICATION + " INDEX "
            + "[" + FLAG_TITLE + " TITLE] "
            + "[" + FLAG_DESCRIPTION + " DESCRIPTION] "
            + "[" + FLAG_DEADLINE + " DEADLINE] "
            + "[" + FLAG_STAGE + " STAGE] "
            + "[" + FLAG_STATUS + " STATUS] \n"
            + "Example: " + COMMAND_WORD + " " + FLAG_APPLICATION + " 1 " + FLAG_TITLE + " SWE";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the contact of the class type Organization or Recruiter,"
            + " identified by its index in the displayed contact list or its id."
            + " Note that existing values will be overwritten by the input values."
            + " Also can edit job applications in the list identified by its index"
            + " The input format varies depending on the class:\n\n"
            + MESSAGE_ORGANIZATION_USAGE + "\n\n"
            + MESSAGE_RECRUITER_USAGE + "\n\n"
            + MESSAGE_APPLICATION_USAGE;

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited %s: %s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";
    public static final String MESSAGE_INVALID_ORGANIZATION =
            "The organization id you supplied does not match any organization in the address book.";

    private Index index;

    private Id targetId = null;
    private EditContactDescriptor editContactDescriptor;

    /**
     * @param index of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditCommand(Index index, EditContactDescriptor editContactDescriptor) {
        requireNonNull(index);
        requireNonNull(editContactDescriptor);

        this.index = index;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    /**
     * @param targetId of the contact to be editted
     */
    public EditCommand(Id targetId, EditContactDescriptor editContactDescriptor) {
        this.targetId = targetId;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.targetId != null) {
            Contact contactToEdit = model.getContactById(targetId);
            return getCommandResult(model, contactToEdit);
        }

        List<Contact> lastShownList = model.getDisplayedContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        return getCommandResult(model, contactToEdit);
    }

    // TODO: Tech debt - Method name does not really reflect what it does
    private CommandResult getCommandResult(Model model, Contact contactToEdit) throws CommandException {
        Contact editedContact = createEditedContact(model, contactToEdit, editContactDescriptor);
        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }

        if (editedContact.getType() == Type.ORGANIZATION) {
            updateLinkedRecruiters(model, (Organization) contactToEdit, (Organization) editedContact);
        }
        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS,
                editedContact.getType(), Messages.format(editedContact)));
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Model model, Contact contactToEdit,
                                               EditContactDescriptor editContactDescriptor) throws CommandException {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName()
            .orElse(contactToEdit.getName());
        Id updatedId = editContactDescriptor.getId()
            .orElse(contactToEdit.getId());
        Phone updatedPhone = editContactDescriptor.getPhone()
            .orElse(contactToEdit.getPhone().orElse(null));
        Email updatedEmail = editContactDescriptor.getEmail()
            .orElse(contactToEdit.getEmail().orElse(null));
        Url updatedUrl = editContactDescriptor.getUrl()
            .orElse(contactToEdit.getUrl().orElse(null));
        Address updatedAddress = editContactDescriptor.getAddress()
            .orElse(contactToEdit.getAddress().orElse(null));
        Set<Tag> updatedTags = editContactDescriptor.getTags()
            .orElse(contactToEdit.getTags());

        // TODO: Refactor into two methods to handle the two cases.
        if (contactToEdit.getType() == Type.ORGANIZATION) {
            Status updatedStatus = editContactDescriptor.getStatus()
                    .orElse(((Organization) contactToEdit).getStatus().orElse(null));

            Position updatedPosition = editContactDescriptor.getPosition()
                    .orElse(((Organization) contactToEdit).getPosition().orElse(null));

            return new Organization(updatedName, updatedId, updatedPhone, updatedEmail,
                    updatedUrl, updatedAddress, updatedTags, updatedStatus, updatedPosition, null);

        } else if (contactToEdit.getType() == Type.RECRUITER) {
            Optional<Id> updatedOid = editContactDescriptor
                    .getOrganizationId()
                    .or(() -> ((Recruiter) contactToEdit).getOrganizationId());

            Organization linkedOrganization = (Organization) updatedOid.map(model::getContactById)
                    .filter(c -> c.getType() == Type.ORGANIZATION)
                    .orElse(null);

            if (updatedOid.isPresent() && linkedOrganization == null) {
                throw new CommandException(MESSAGE_INVALID_ORGANIZATION);
            }

            return new Recruiter(updatedName, updatedId, updatedPhone, updatedEmail, updatedUrl,
                    updatedAddress, updatedTags, linkedOrganization);
        }

        return new Contact(updatedName, updatedId, updatedPhone, updatedEmail, updatedUrl, updatedAddress,
                updatedTags, null);
    }

    /**
     * Updates all recruiters linked to the {@code oldOrganization} to link to the {@code updatedOrganization}.
     */
    private static void updateLinkedRecruiters(Model model,
                                               Organization oldOrganization,
                                               Organization updatedOrganization) {
        // Updates all recruiters linked to the old organization to link to the updated one.
        List<Contact> childrenContacts = oldOrganization.getChildren(model);
        for (Contact child : childrenContacts) {
            assert child.getType() == Type.RECRUITER;
            Recruiter updatedRecruiter = new Recruiter(
                    child.getName(), child.getId(), child.getPhone().orElse(null),
                    child.getEmail().orElse(null), child.getUrl().orElse(null),
                    child.getAddress().orElse(null), child.getTags(), updatedOrganization
            );
            model.setContact(child, updatedRecruiter);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return index.equals(otherEditCommand.index)
                && editContactDescriptor.equals(otherEditCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Name name;
        private Id id;
        private Phone phone;
        private Email email;
        private Url url;
        private Address address;
        private Set<Tag> tags;
        private Status status;
        private Position position;

        private Id oid;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setName(toCopy.name);
            setId(toCopy.id);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setUrl(toCopy.url);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setStatus(toCopy.status);
            setPosition(toCopy.position);
            setOid(toCopy.oid);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, id, url, status, position, oid);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Id> getOrganizationId() {
            return Optional.ofNullable(oid);
        }

        public void setOid(Id id) {
            this.oid = id;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Id> getId() {
            return Optional.ofNullable(id);
        }

        public void setId(Id id) {
            this.id = id;
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<Url> getUrl() {
            return Optional.ofNullable(url);
        }

        public void setUrl(Url url) {
            this.url = url;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public Optional<Position> getPosition() {
            return Optional.ofNullable(position);
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            EditContactDescriptor otherEditContactDescriptor = (EditContactDescriptor) other;
            return Objects.equals(name, otherEditContactDescriptor.name)
                    && Objects.equals(phone, otherEditContactDescriptor.phone)
                    && Objects.equals(email, otherEditContactDescriptor.email)
                    && Objects.equals(address, otherEditContactDescriptor.address)
                    && Objects.equals(tags, otherEditContactDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .add("url", url)
                    .add("status", status)
                    .add("position", position)
                    .toString();
        }
    }
}
