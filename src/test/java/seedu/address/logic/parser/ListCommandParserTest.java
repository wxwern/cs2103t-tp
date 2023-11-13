package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.model.Model.PREDICATE_SHOW_NOT_APPLIED_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_ORGANIZATIONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ONLY_RECRUITERS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand(PREDICATE_SHOW_ALL_CONTACTS));
        assertParseSuccess(parser, "--org", new ListCommand(PREDICATE_SHOW_ONLY_ORGANIZATIONS));
        assertParseSuccess(parser, "--rec", new ListCommand(PREDICATE_SHOW_ONLY_RECRUITERS));
        assertParseSuccess(parser, "--toapply", new ListCommand(PREDICATE_SHOW_NOT_APPLIED_ORGANIZATIONS));
    }
}
