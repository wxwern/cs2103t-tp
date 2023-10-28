package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Stream;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * A text field capable of displaying autocomplete details.
 *
 * <p>
 * Inspired by a proof-of-concept implementation from:
 * <a href="https://gist.github.com/floralvikings/10290131">floralvikings/AutoCompleteTextBox.java</a>
 * </p>
 */
public class AutocompleteTextField extends TextField {

    protected class TextChangeSnapshot {
        public final String oldValue;
        public final String newValue;

        public TextChangeSnapshot(String oldValue, String newValue) {
            this.oldValue = oldValue;
            this.newValue = newValue;
        }

        @Override
        public String toString() {
            return "TextChangeSnapshot{'" + oldValue + '\'' + " to '" + newValue + "'}";
        }
    }

    /**
     * A functional interface that performs auto-completions based on the given partial inputs.
     */
    @FunctionalInterface
    public interface CompletionGenerator extends Function<String, Stream<String>> { }

    private final ContextMenu autocompletePopup;

    // Configuration variables
    private CompletionGenerator completionGenerator = s -> Stream.empty();
    private int popupLimit = 10;

    // History tracking for autocomplete undo operations
    private final Stack<TextChangeSnapshot> autocompleteHistory = new Stack<>();

    /**
     * Constructs a new text field with the ability to perform autocompletion.
     */
    public AutocompleteTextField() {
        super();
        this.autocompletePopup = new ContextMenu();

        // Setup autocompletion popup menu UI updates
        this.textProperty().addListener(e -> updatePopupState());
        this.focusedProperty().addListener(e -> updatePopupState());

        // Setup autocompletion undo data cleanup
        this.textProperty().addListener((e, oldValue, newValue) -> updateUndoHistoryState(oldValue, newValue));
    }

    /**
     * Sets the autocompletion generator for this text field.
     */
    public void setCompletionGenerator(CompletionGenerator completionGenerator) {
        this.completionGenerator = completionGenerator == null
                ? s -> Stream.empty()
                : completionGenerator;
    }

    /**
     * Sets the maximum number of entries that can be shown in the popup.
     */
    public void setPopupLimit(int popupLimit) {
        this.popupLimit = popupLimit;
    }

    /**
     * Triggers autocompletion immediately using the first suggested value, if any.
     *
     * @return true if an autocompleted result has been filled in, false otherwise.
     */
    public boolean triggerImmediateAutocompletion() {
        Optional<String> firstSuggestion = completionGenerator.apply(getText())
                .limit(1)
                .findFirst();

        firstSuggestion.ifPresent(this::triggerImmediateAutocompletionUsingResult);

        return firstSuggestion.isPresent();
    }

    /**
     * Triggers autocompletion immediately using the given result.
     */
    private void triggerImmediateAutocompletionUsingResult(String result) {
        String oldText = this.getText();

        // Add data for undoing if there's a change
        if (!Objects.equals(oldText, result)) {
            autocompleteHistory.add(new TextChangeSnapshot(oldText, result));
        }

        // Update the text field
        this.setText(result + " "); // add a new space character to allow for faster continuation

        // Update the view and cursor location
        this.requestFocus();
        this.end();
        this.updatePopupState();
    }

    /**
     * Undoes the last immediate autocompleted result.
     * This only does something when invoked at a stage where the text is the previously autocompleted result.
     */
    public boolean undoLastImmediateAutocompletion() {
        if (autocompleteHistory.isEmpty()) {
            return false;
        }

        TextChangeSnapshot snapshot = autocompleteHistory.peek();

        // Verify that the current text is correctly at the latest snapshot
        if (!this.getText().trim().equals(snapshot.newValue)) {
            return false;
        }

        // Pop the result
        autocompleteHistory.pop();

        // Set the old value back in
        this.setText(snapshot.oldValue + " "); // preserve the extra space previously added

        // Update the view and cursor location
        this.requestFocus();
        this.end();
        this.updatePopupState();
        return true;
    }

    /**
     * Returns true if the autocomplete popup menu is visible, false otherwise.
     */
    public boolean isPopupVisible() {
        return this.autocompletePopup.isShowing();
    }

    /**
     * Updates the state of the popup indicating the autocompletion entries.
     */
    protected void updatePopupState() {
        String text = getText();
        if (text.isEmpty() || !isFocused()) {
            autocompletePopup.hide();
            return;
        }

        List<CustomMenuItem> menuItems = new LinkedList<>();

        completionGenerator.apply(text)
                .limit(popupLimit)
                .forEachOrdered(autocompletedString -> {
                    Label entryLabel = new Label(autocompletedString);
                    CustomMenuItem item = new CustomMenuItem(entryLabel, false);
                    item.setOnAction(e -> triggerImmediateAutocompletionUsingResult(autocompletedString));
                    menuItems.add(item);
                });

        autocompletePopup.getItems().clear();
        autocompletePopup.getItems().addAll(menuItems);

        if (menuItems.size() > 0) {
            if (!autocompletePopup.isShowing()) {
                autocompletePopup.show(AutocompleteTextField.this, Side.BOTTOM, 0, 0);
            }
        } else {
            autocompletePopup.hide();
        }
    }

    /**
     * Update undo history tracked state based on the change for text field old values to new values.
     */
    protected void updateUndoHistoryState(String previousValue, String currentValue) {
        if (autocompleteHistory.isEmpty()) {
            return;
        }

        // Heuristic for clearing history:
        //   IF current value is no longer a prefix of the latest autocomplete result OR is of the undone state
        //   THEN said autocompletion snapshot is no longer applicable.

        while (!currentValue.startsWith(autocompleteHistory.peek().newValue)
                || currentValue.trim().equals(autocompleteHistory.peek().oldValue)) {

            autocompleteHistory.pop();
            if (autocompleteHistory.isEmpty()) {
                break;
            }
        }
    }

}
