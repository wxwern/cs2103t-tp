package seedu.address.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
 * With reference to a proof-of-concept implementation from:
 * <a href="https://gist.github.com/floralvikings/10290131">floralvikings/AutoCompleteTextBox.java</a>
 * </p>
 */
public class AutocompleteTextField extends TextField {

    /**
     * A functional interface that performs auto-completions based on the given partial inputs.
     */
    @FunctionalInterface
    public interface CompletionGenerator extends Function<String, Stream<String>> { }

    private final ContextMenu autocompletePopup;

    private CompletionGenerator completionGenerator = s -> Stream.empty();
    private int popupLimit = 10;

    /**
     * Constructs a new text field with the ability to perform autocompletion.
     */
    public AutocompleteTextField() {
        super();
        autocompletePopup = new ContextMenu();
        textProperty().addListener(e -> updatePopupState());
        focusedProperty().addListener(e -> autocompletePopup.hide());
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
        var results = completionGenerator.apply(getText()).limit(2).collect(Collectors.toList());
        if (results.size() < 1) {
            return false;
        }

        String result = results.get(0);
        setText(result + " ");
        requestFocus();
        end();
        updatePopupState();

        return true;
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
                    item.setOnAction(e -> {
                        setText(autocompletedString + " ");
                        requestFocus();
                        end();
                        updatePopupState();
                    });
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
}
