package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.jobapplication.JobApplication;

/**
 * A UI component that displays a list of applications.
 */
public class ApplicationListPanel extends UiPart<Region> {
    private static final String FXML = "ApplicationListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ApplicationListPanel.class);

    @FXML
    private ListView<JobApplication> applicationListView;

    /**
     * Creates a {@code ContactListPanel} with the given {@code ObservableList}.
     */
    public ApplicationListPanel(ObservableList<JobApplication> applicationList) {
        super(FXML);
        applicationListView.setItems(applicationList);
        applicationListView.setCellFactory(listView -> new ApplicationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code JobApplication} using a {@code ApplicationCard}.
     * Same as {@link seedu.address.ui.ContactListPanel.ContactListViewCell}
     */
    class ApplicationListViewCell extends ListCell<JobApplication> {
        @Override
        protected void updateItem(JobApplication application, boolean isEmpty) {
            super.updateItem(application, isEmpty);

            if (isEmpty || application == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ApplicationCard(application, getIndex() + 1).getRoot());
            }
        }
    }

}
