package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.FLAG_EMAIL;
import static seedu.address.logic.parser.CliSyntax.FLAG_NAME;
import static seedu.address.logic.parser.CliSyntax.FLAG_PHONE;
import static seedu.address.logic.parser.CliSyntax.FLAG_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the contact identified "
            + "by the index number used in the displayed contact list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + FLAG_NAME + " NAME] "
            + "[" + FLAG_PHONE + " PHONE] "
            + "[" + FLAG_EMAIL + " EMAIL] "
            + "[" + FLAG_ADDRESS + " ADDRESS] "
            + "[" + FLAG_TAG + " TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + FLAG_PHONE + " 91234567 "
            + FLAG_EMAIL + " johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";

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

        List<Contact> lastShownList = model.getFilteredContactList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX);
        }

        Contact contactToEdit = lastShownList.get(index.getZeroBased());
        return getCommandResult(model, contactToEdit);
    }


    public CommandResult getCommandResult(Model model, Contact contactToEdit) throws CommandException {
        Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);
        if (!contactToEdit.isSameContact(editedContact) && model.hasContact(editedContact)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }
        model.setContact(contactToEdit, editedContact);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact)));
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


        if (contactToEdit.getType() == Type.ORGANIZATION) {
            Status updatedStatus = editContactDescriptor.getStatus()
                    .orElse(((Organization) contactToEdit).getStatus().orElse(null));

            Position updatedPosition = editContactDescriptor.getPosition()
                    .orElse(((Organization) contactToEdit).getPosition().orElse(null));
            return new Organization(updatedName, updatedId, updatedPhone, updatedEmail, updatedUrl,
                    updatedAddress, updatedTags, updatedStatus, updatedPosition, null);
        } else if (contactToEdit.getType() == Type.RECRUITER) {
            Optional<Id> updatedOid = editContactDescriptor
                    .getOrganizationId()
                    .or(() -> ((Recruiter) contactToEdit).getOrganizationId());

            Organization updatedOrganization = (Organization) updatedOid.map(model::getContactById)
                    .filter(c -> c.getType() == Type.ORGANIZATION)
                    .orElse(null);

            if (updatedOid.isPresent() && updatedOrganization == null) {
                throw new CommandException(MESSAGE_INVALID_ORGANIZATION);
            }
            return new Recruiter(updatedName, updatedId, updatedPhone, updatedEmail, updatedUrl,
                    updatedAddress, updatedTags, updatedOid);
        }

        return new Contact(updatedName, updatedId, updatedPhone, updatedEmail, updatedUrl, updatedAddress, updatedTags);
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
