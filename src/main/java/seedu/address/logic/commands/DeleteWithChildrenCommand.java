package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

public class DeleteWithChildrenCommand extends DeleteCommand {
    public DeleteWithChildrenCommand(Index targetIndex) {
        super(targetIndex);
    }
}
