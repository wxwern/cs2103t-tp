package seedu.address.ui;

import java.util.Objects;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private static final Logger logger = LogsCenter.getLogger(CommandBox.class);

    private final CommandExecutor commandExecutor;
    private final AutocompleteTextField.CompletionGenerator completionGenerator;

    @FXML
    private AutocompleteTextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(
            CommandExecutor commandExecutor, AutocompleteTextField.CompletionGenerator completionGenerator
    ) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.completionGenerator = completionGenerator;

        assert commandTextField != null;

        // Setup completion results and hints
        commandTextField.setCompletionGenerator(completionGenerator);
        commandTextField.setAutocompleteHintString("[Press TAB or SPACE to autocomplete]");

        // Setup UI events
        commandTextField.setOnAction(e -> this.handleCommandEntered());

        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyFilter);
        commandTextField.addEventFilter(KeyEvent.KEY_RELEASED, this::handleKeyFilter);
        commandTextField.addEventFilter(KeyEvent.KEY_TYPED, this::handleKeyFilter);

        // Sets the style to default whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }

    /**
     * Handles the key filter of a textbox.
     */
    @FXML
    private void handleKeyFilter(KeyEvent keyEvent) {
        boolean isTextCaretAtEnd =
                this.commandTextField.getCaretPosition() == this.commandTextField.getText().length()
                    && this.commandTextField.getSelectedText().isEmpty();
        boolean isKeyTyped = keyEvent.getEventType() == KeyEvent.KEY_TYPED;
        boolean isKeyPressed = keyEvent.getEventType() == KeyEvent.KEY_PRESSED;

        // Note:
        //   These captures keystrokes at different KeyEvent types, as there some events are captured by
        //   other elements, and hence not all events are delivered in all situations.

        if (isKeyTyped && isTextCaretAtEnd && Objects.equals(keyEvent.getCharacter(), " ")) {
            logger.fine("Intercepted SPACE typed at end");
            this.handleCommandAutocompleted(keyEvent);


        } else if (isKeyPressed && isTextCaretAtEnd && keyEvent.getCode() == KeyCode.BACK_SPACE) {
            logger.fine("Intercepted BACKSPACE typed at end");
            this.handleCommandUndoAutocomplete(keyEvent);


        } else if (isKeyPressed && keyEvent.getCode() == KeyCode.TAB) {
            logger.fine("Intercepted TAB key");

            keyEvent.consume(); // Consume to prevent element focus change.

            // There are two cases for TAB:
            //  - Case 1: Autocomplete popup shown --> Triggers autocomplete result
            //  - Case 2: Autocomplete popup not shown --> Tries to make autocomplete popup visible

            if (commandTextField.isPopupVisible()) {
                this.handleCommandAutocompleted(keyEvent);
            } else {
                commandTextField.showPopup();
            }
        }
    }

    /**
     * Handles the request for finalization of text input.
     */
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();

        if (commandText.isBlank()) {
            // Ignore and reset blank (whitespace-only or empty) inputs
            commandTextField.setText("");
            return;
        }

        try {
            // Process the command
            commandExecutor.execute(commandText);

            // Once successful, reset the text field
            commandTextField.setText("");
            commandTextField.requestFocus();
            commandTextField.hidePopup();

        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
            commandTextField.hidePopup();
        }
    }

    /**
     * Handles the request for autocompletion of text input.
     */
    @FXML
    private void handleCommandAutocompleted(KeyEvent keyEvent) {
        logger.fine("User invoked auto-completion!");

        var actionResult = commandTextField.triggerImmediateAutocompletion();
        if (actionResult == AutocompleteTextField.ActionResult.EXECUTED) {
            keyEvent.consume();

            commandTextField.requestFocus();
            commandTextField.end();
        }
    }

    /**
     * Handles the request for undoing autocompletion.
     */
    private void handleCommandUndoAutocomplete(KeyEvent keyEvent) {
        logger.fine("User invoked undo auto-completion!");

        var actionResult = commandTextField.undoLastImmediateAutocompletion();
        if (actionResult == AutocompleteTextField.ActionResult.EXECUTED) {
            keyEvent.consume();

            commandTextField.requestFocus();
            commandTextField.end();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}
