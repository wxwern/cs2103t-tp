package seedu.address.ui;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * A text field capable of displaying and performing autocomplete.
 *
 * <p>
 * At a basic level, it extends {@link TextField}
 * to provide an autocomplete result menu configurable via {@link #setCompletionGenerator},
 * the ability to trigger auto-completions via {@link #triggerImmediateAutocompletion()} or the GUI,
 * and the ability to undo the latest auto-completions via {@link #undoLastImmediateAutocompletion()}.
 * </p><br>
 *
 * <p>
 * The base implementation was referenced from a proof-of-concept implementation from
 * <a href="https://gist.github.com/floralvikings/10290131">floralvikings/AutoCompleteTextBox.java</a>.
 * Additional functionality like dynamic completion suppliers and autocomplete history tracking for undo
 * are custom-implemented.
 * </p>
 */
public class AutocompleteTextField extends TextField {

    /** A snapshot of a text-change due to autocompletion. */
    protected static class AutocompleteSnapshot {
        public final String partialValue;
        public final String completedValue;

        public AutocompleteSnapshot(String partialValue, String completedValue) {
            this.partialValue = partialValue;
            this.completedValue = completedValue;
        }

        @Override
        public String toString() {
            return "AutocompleteSnapshot{'" + partialValue + "' to '" + completedValue + "'}";
        }
    }

    /** An enum of execution status. */
    public enum ActionResult {
        EXECUTED, NOT_EXECUTED;
    }

    /**
     * A functional interface that generates a stream of auto-completion results
     * based on the given partial input.
     */
    @FunctionalInterface
    public interface CompletionGenerator extends Function<String, Stream<String>> { }

    /** Internal ID prefix to note the exact menu item per index. */
    private static final String AUTOCOMPLETE_MENU_ITEM_ID_PREFIX = "autocomplete-completion-item-";

    // GUI elements
    private final ContextMenu autocompletePopup;


    // Configuration variables
    private CompletionGenerator completionGenerator = s -> Stream.empty();
    private String autocompleteHintString = "[Select to autocomplete]";
    private int popupLimit = 10;


    // History tracking for autocomplete undo operations
    private final Stack<AutocompleteSnapshot> autocompleteHistory = new Stack<>();

    /**
     * Constructs a new text field with the ability to perform autocompletion.
     */
    public AutocompleteTextField() {
        super();
        this.autocompletePopup = new ContextMenu();

        // Setup default behaviours
        autocompletePopup.setHideOnEscape(true);
        autocompletePopup.setAutoFix(true);
        autocompletePopup.setAutoHide(true);
        autocompletePopup.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.SPACE) {
                // SPACE dismisses the popup if none is selected via tab focus.
                // We disallow this by intercepting it before it does.
                e.consume();
            }

            if (e.getCode() == KeyCode.ENTER) {
                // The default behaviour of ENTER seems buggy.
                // We will intercept it and do our own processing.
                // This uses JavaFX private APIs to locate the focused element, as found here:
                // https://stackoverflow.com/questions/27332981/contextmenu-and-programmatically-selecting-an-item
                e.consume();

                int highlightedIndex = 0;

                Set<Node> items = autocompletePopup.getSkin().getNode().lookupAll(".menu-item");
                for (Node item : items) {
                    if (!item.isFocused() && !item.isHover()) {
                        continue;
                    }

                    if (!item.getId().startsWith(AUTOCOMPLETE_MENU_ITEM_ID_PREFIX)) {
                        continue;
                    }

                    try {
                        String indexPart = item.getId().substring(AUTOCOMPLETE_MENU_ITEM_ID_PREFIX.length());
                        highlightedIndex = Integer.parseInt(indexPart, 10);
                        break;
                    } catch (NumberFormatException exception) {
                        // Ignore...
                    }
                }

                this.triggerImmediateAutocompletion(highlightedIndex);
            }
        });

        // Setup autocompletion popup menu UI updates
        this.textProperty().addListener(e -> refreshPopupState());
        this.focusedProperty().addListener(e -> refreshPopupState());

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
     * Sets the maximum number of entries that can be shown in the autocomplete popup.
     */
    public void setPopupLimit(int popupLimit) {
        this.popupLimit = popupLimit;
    }

    /**
     * Gets the current maximum number of entries that can be shown in the autocomplete popup.
     */
    public int getPopupLimit() {
        return popupLimit;
    }

    /**
     * Triggers autocompletion immediately using the first suggested value, if any.
     */
    public ActionResult triggerImmediateAutocompletion() {
        return triggerImmediateAutocompletion(0);
    }

    /**
     * Triggers autocompletion immediately using the given suggested value index, if any.
     */
    public ActionResult triggerImmediateAutocompletion(int index) {
        ObservableList<MenuItem> menuItems = autocompletePopup.getItems();

        if (!isPopupVisible() || menuItems.size() <= index || index < 0) {
            return ActionResult.NOT_EXECUTED;
        }

        menuItems.get(index).fire();
        return ActionResult.EXECUTED;
    }

    /**
     * Triggers autocompletion immediately using the given result.
     */
    private void triggerImmediateAutocompletionUsingResult(String result) {
        String oldText = this.getText();

        // Add data for undoing if there's a change
        if (!Objects.equals(oldText, result)) {
            autocompleteHistory.add(new AutocompleteSnapshot(oldText, result));
        }

        // Update the text field
        this.setText(result + " "); // add a new space character to allow for faster continuation

        // Update the view and cursor location
        this.requestFocus();
        this.end();
        this.refreshPopupState();
    }

    /**
     * Undoes the last immediate autocompleted result.
     * This only does something when invoked at a stage where the current text matches
     * a previously autocompleted result.
     */
    public ActionResult undoLastImmediateAutocompletion() {
        if (autocompleteHistory.isEmpty()) {
            return ActionResult.NOT_EXECUTED;
        }

        AutocompleteSnapshot snapshot = autocompleteHistory.peek();

        // Verify that the current text correctly matches the latest snapshot
        if (!this.getText().trim().equals(snapshot.completedValue)) {
            return ActionResult.NOT_EXECUTED;
        }

        // Pop the result
        autocompleteHistory.pop();

        // Set the old value back in
        this.setText(snapshot.partialValue);

        // Update the view and cursor location
        this.requestFocus();
        this.end();
        this.refreshPopupState();

        return ActionResult.EXECUTED;
    }

    /**
     * Sets the string used to hint that an option can be autocompleted.
     */
    public void setAutocompleteHintString(String autocompleteHintString) {
        this.autocompleteHintString = autocompleteHintString;
    }

    /**
     * Returns true if the autocomplete popup menu is visible, false otherwise.
     */
    public boolean isPopupVisible() {
        return this.autocompletePopup.isShowing();
    }

    /**
     * Hides the autocomplete popup menu if it is visible.
     * This is temporary and may be shown again if the user types anything,
     * or if {@link #refreshPopupState()} is invoked.
     */
    public void hidePopup() {
        this.autocompletePopup.hide();
    }

    /**
     * Shows the autocomplete popup menu if there are contents to show but it is not yet visible.
     * This is equivalent to calling {@link #refreshPopupState()}, since the popup is visible by default when there
     * are elements.
     */
    public void showPopup() {
        this.refreshPopupState();
    }

    /**
     * Updates the state of the popup indicating the autocompletion entries.
     */
    public void refreshPopupState() {
        String text = getText();
        if ((!isFocused() && !autocompletePopup.isFocused())
                || popupLimit <= 0) {

            autocompletePopup.hide();
            return;
        }

        // Obtain the list of completions
        List<String> completions = completionGenerator.apply(text)
                .limit(popupLimit + 1) // (+ 1 so we can tell if it exceeds the limit)
                .collect(Collectors.toList());

        // Obtain the length of the prefix part of the completion strings that can be truncated
        int hidableCompletionPrefixLength = completions.stream()
                .min(Comparator.comparingInt(String::length))
                .map(String::length)
                .map(l -> Math.max(0, l - 48)) // Keep the last 48 characters in view
                .orElse(0);

        // Populate the menu items
        List<CustomMenuItem> menuItems = new LinkedList<>();
        for (int i = 0; i < completions.size(); i++) {

            // Create the relevant labels
            String completion = completions.get(i);
            String[] completionParts = splitIntoAutocompletionComponents(getText(), completion);

            String prefixMatchPart = truncateFrontWithEllipsis(completionParts[0], hidableCompletionPrefixLength);
            String postfixCompletionPart = completionParts[1];

            Label completionLabelFront = new Label(prefixMatchPart);
            Label completionLabelBack = new Label(postfixCompletionPart);

            completionLabelFront.getStyleClass().add("completion-prefix");
            completionLabelBack.getStyleClass().add("completion-data");

            // Create the horizontal box
            HBox completionBox = new HBox(completionLabelFront, completionLabelBack);
            completionBox.getStyleClass().add("autocomplete-box");
            completionBox.setPadding(new Insets(0, 8, 0, 8));
            completionBox.setAlignment(Pos.BASELINE_LEFT);

            // Create the context menu item
            CustomMenuItem item = new CustomMenuItem(completionBox, false);
            item.setOnAction(e -> triggerImmediateAutocompletionUsingResult(completion));
            item.setId(AUTOCOMPLETE_MENU_ITEM_ID_PREFIX + i);
            menuItems.add(item);

            // Handle special case styling
            if (i == 0) {
                // Special Case 1: First completion option
                Label completionHint = new Label(autocompleteHintString);
                completionHint.getStyleClass().add("completion-hint");
                completionHint.setPadding(new Insets(0, 8, 0, 8));

                completionBox.getStyleClass().add("primary");
                completionBox.getChildren().add(completionHint);

            } else if (i >= popupLimit) {
                // Special Case 2: Options exceeding limit and not first element
                completionLabelFront.setText("... (more options hidden)");
                completionLabelBack.setText(null);
                item.setDisable(true);
                item.setOnAction(e -> {});
                break; // Stop further processing immediately - only one of this should be displayed.

            }
        }

        // Replace the current menu items with the new set
        autocompletePopup.getItems().clear();
        autocompletePopup.getItems().addAll(menuItems);

        // Hide the popup regardless if it should be shown or not.
        //
        // This is done to work around a weird quirk/bug in JavaFX where the text field would steal focus after an
        // autocompletion result has been invoked, leading to the inability to use arrow keys to navigate the
        // autocomplete list.
        autocompletePopup.hide();

        // Show the popup given that we have items in the list to show.
        if (menuItems.size() > 0) {
            autocompletePopup.show(AutocompleteTextField.this, Side.BOTTOM, 0, 0);
        }
    }

    /**
     * Update undo history tracked state based on the change for text field old values to new values.
     */
    protected void updateUndoHistoryState(String previousValue, String currentValue) {

        // Heuristic for clearing history:
        //   IF current value is no longer a prefix of the latest autocomplete result OR is of the undone state
        //   THEN said autocompletion snapshot is no longer applicable.

        while (autocompleteHistory.size() > 0
                && (!currentValue.startsWith(autocompleteHistory.peek().completedValue)
                || currentValue.trim().equals(autocompleteHistory.peek().partialValue))
        ) {
            autocompleteHistory.pop();
        }
    }

    /**
     * Splits the given autocomplete result by the closest prefix matched phrase and the autocompleted result.
     *
     * <p>
     * For example, providing the input {@code "abc def 1234"} with autocompletion {@code "abc def 12345 ghi"}
     * would yield a split of {@code new String[] { "abc def", "12345 ghi" }}
     * </p>
     *
     * @return an array of length 2 that contains the (prefix part, autocompletion part).
     */
    private String[] splitIntoAutocompletionComponents(String input, String autocompletionResult) {
        int secondPartIndex = 0;

        // Step 1: Find the index beyond the prefix match.
        while (secondPartIndex < input.length()
                && secondPartIndex < autocompletionResult.length()
                && input.charAt(secondPartIndex) == autocompletionResult.charAt(secondPartIndex)) {
            secondPartIndex++;
        }

        // Step 2: Backtrack till a space is found.
        while (secondPartIndex - 1 >= 0
                && autocompletionResult.charAt(secondPartIndex - 1) != ' ') {
            secondPartIndex--;
        }

        // Step 3: Split by the given index, if possible.
        if (secondPartIndex >= 0 && secondPartIndex <= autocompletionResult.length()) {
            return new String[] {
                    autocompletionResult.substring(0, secondPartIndex),
                    autocompletionResult.substring(secondPartIndex),
            };
        } else {
            return new String[] { autocompletionResult, "" };
        }
    }

    /**
     * Truncates the given string by the given {@code truncateAmount} of characters at the front, and adds
     * leading ellipsis. It returns just the ellipsis if the number of characters truncated exceeds the string length.
     */
    private String truncateFrontWithEllipsis(String str, int truncateAmount) {
        if (truncateAmount <= 0) {
            return str;
        }
        return truncateAmount >= str.length() ? "..." : "..." + str.substring(truncateAmount);
    }

}
