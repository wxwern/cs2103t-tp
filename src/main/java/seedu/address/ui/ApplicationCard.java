package seedu.address.ui;

import java.util.function.Supplier;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.jobapplication.JobApplication;
import seedu.address.model.jobapplication.JobDescription;

/**
 * A UI component that displays information of a {@code JobApplication}.
 */
public class ApplicationCard extends UiPart<Region> {
    private static final String FXML = "ApplicationListCard.fxml";
    private final JobApplication jobApplication;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox cardPaneInnerVbox;
    @FXML
    private Label index;
    @FXML
    private Label linkedParentOrganization;
    @FXML
    private Label title;
    @FXML
    private Label description;
    @FXML
    private Label status;
    @FXML
    private Label deadline;
    @FXML
    private Label stage;
    @FXML
    private Label lastUpdatedTime;

    /**
     * Creates a {@code ApplicationCard} with the given {@code JobApplication} and index to display.
     */
    public ApplicationCard(JobApplication application, int displayedIndex) {
        super(FXML);
        this.jobApplication = application;
        index.setText(String.format("%d. ", displayedIndex));
        linkedParentOrganization.setText(application.getOrgName().fullName);
        title.setText(application.getJobTitle().title);
        status.setText(application.getStatus().toString());
        deadline.setText(application.getDeadline().toString());
        stage.setText(application.getApplicationStage().toString());
        lastUpdatedTime.setText(application.getLastUpdatedTime().toString());
        setVboxInnerLabelText(
                description, () -> application.getJobDescription().map(JobDescription::toString).orElse(null));
    }

    /**
     * Configures the inner label contained within the vbox container to show the given string, or remove the label
     * entirely if the string is empty or null.
     *
     * @param label         The label to set the text to.
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
