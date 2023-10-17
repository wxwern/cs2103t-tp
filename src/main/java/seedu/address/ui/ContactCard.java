package seedu.address.ui;

import java.util.Comparator;
import java.util.function.Supplier;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Contact;
import seedu.address.model.person.Id;
import seedu.address.model.person.Organization;
import seedu.address.model.person.Recruiter;

/**
 * An UI component that displays information of a {@code Contact}.
 */
public class ContactCard extends UiPart<Region> {

    private static final String FXML = "ContactListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Contact contact;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardPaneInnerVbox;
    @FXML
    private Label name;
    @FXML
    private Label index;
    @FXML
    private Label id;
    @FXML
    private Label linkedParentOrganization;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private Label position;
    @FXML
    private Label url;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Contact} and index to display.
     */
    public ContactCard(Contact contact, int displayedIndex) {
        super(FXML);
        this.contact = contact;

        index.setText(String.format("%d. ", displayedIndex));
        id.setText(contact.getId().value);
        name.setText(contact.getName().fullName);

        final Label typeLabel = new Label(contact.getType().toString());
        typeLabel.setId("type");
        tags.getChildren().add(typeLabel); // add it to the front of tags

        setVboxInnerLabelText(phone, () -> contact.getPhone().value);
        setVboxInnerLabelText(address, () -> contact.getAddress().value);
        setVboxInnerLabelText(email, () -> contact.getEmail().value);
        setVboxInnerLabelText(url, () -> contact.getUrl().value);

        switch (contact.getType()) {
        case ORGANIZATION: {
            Organization organization = (Organization) contact;
            final String statusString = organization.getStatus().applicationStatus;
            final String positionString = organization.getPosition().jobPosition;

            setVboxInnerLabelText(
                    status, () -> StringUtil.formatWithNullFallback("Application Status: %s", statusString)
            );
            setVboxInnerLabelText(
                    position, () -> StringUtil.formatWithNullFallback("Application Position: %s", positionString)
            );
            cardPaneInnerVbox.getChildren().remove(linkedParentOrganization);
            break;
        }
        case RECRUITER: {
            Recruiter recruiter = (Recruiter) contact;

            final Id linkedOrgId = recruiter.getOrganizationId();

            setVboxInnerLabelText(
                    linkedParentOrganization, () -> linkedOrgId == null
                            ? null
                            : String.format(
                                    "from %s (%s)", "organization" /* TODO: Use org name instead */, linkedOrgId.value
                            )
            );
            cardPaneInnerVbox.getChildren().removeAll(status, position);
            break;
        }
        default:
            cardPaneInnerVbox.getChildren().removeAll(status, position, linkedParentOrganization);
            break;
        }

        contact.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Configures the inner label contained within the vbox container to show the given string,
     * or remove the label entirely if the string is empty or null.
     *
     * @param label The label to set the text to.
     * @param valueSupplier The string value supplier. This may be expressed as a lambda function.
     */
    private void setVboxInnerLabelText(Label label, Supplier<String> valueSupplier) {
        if (label == null) {
            return;
        }

        String value = valueSupplier.get();
        if (value == null || value.isBlank()) {
            label.setText(null);
            cardPaneInnerVbox.getChildren().remove(label);
        } else {
            label.setText(value);
        }
    }
}
