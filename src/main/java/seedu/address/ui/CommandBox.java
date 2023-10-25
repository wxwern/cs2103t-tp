package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

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
        commandTextField.setCompletionGenerator(completionGenerator);

        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
    }


    /**
     * Handles the key event in a textbox.
     */
    @FXML
    private void handleKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.handleCommandEntered();

        } else if (keyEvent.getCode() == KeyCode.TAB) {
            this.handleCommandAutocompleted(keyEvent);

        }
    }

    /**
     * Handles the request for finalization of text input.
     */
    private void handleCommandEntered() {
        String commandText = commandTextField.getText();
        if (commandText.equals("")) {
            return;
        }

        try {
            commandExecutor.execute(commandText);
            commandTextField.setText("");
            commandTextField.requestFocus();
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Handles the request for autocompletion of text input.
     */
    @FXML
    private void handleCommandAutocompleted(KeyEvent keyEvent) {
        System.out.println("User invoked auto-completion!");

        boolean hasAutocompletedResult = commandTextField.triggerImmediateAutocompletion();
        if (hasAutocompletedResult) {
            keyEvent.consume();
            commandTextField.setText(commandTextField.getText() + " ");
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
