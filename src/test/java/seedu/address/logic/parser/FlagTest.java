package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class FlagTest {

    @Test
    public void isFlagSyntax() {
        String validFullStr = Flag.DEFAULT_PREFIX + "a123" + Flag.DEFAULT_POSTFIX;
        String validAliasStr = Flag.DEFAULT_ALIAS_PREFIX + "b45" + Flag.DEFAULT_ALIAS_POSTFIX;

        assertTrue(Flag.isFlagSyntax(validFullStr));
        assertTrue(Flag.isFlagSyntax(validAliasStr));
        assertTrue(Flag.isFlagSyntax(new Flag("abcde").toString()));
        assertTrue(Flag.isFlagSyntax(new Flag("asdf").getFlagString()));
        assertTrue(Flag.isFlagSyntax(new Flag("aaa", "a").getFlagAliasString()));

        assertFalse(Flag.isFlagSyntax(" " + validFullStr + " "));
        assertFalse(Flag.isFlagSyntax(" " + validAliasStr + " "));

        assertFalse(Flag.isFlagSyntax("000"));
        assertFalse(Flag.isFlagSyntax("aaa"));
        assertFalse(Flag.isFlagSyntax("p/"));
        assertFalse(Flag.isFlagSyntax("*Q"));
        assertFalse(Flag.isFlagSyntax(""));
        assertFalse(Flag.isFlagSyntax(null));
    }

    @Test
    public void getFlagString() {
        String flagString = "abc123";

        // default format works correctly
        Flag f = new Flag(flagString);
        assertEquals(
                Flag.DEFAULT_PREFIX + flagString + Flag.DEFAULT_POSTFIX,
                f.getFlagString()
        );

        // custom formats work correctly
        f = Flag.ofCustomFormat("zz", "&", "!");
        assertEquals("&zz!", f.getFlagString());

        // null inputs work correctly
        f = Flag.ofCustomFormat("a00", "*", null);
        assertEquals("*a00", f.getFlagString());

        f = Flag.ofCustomFormat("b11", null, "?");
        assertEquals("b11?", f.getFlagString());

        f = Flag.ofCustomFormat("c22", null, null);
        assertEquals("c22", f.getFlagString());
    }

    @Test
    public void getFlagAliasString() {
        String full = "aaa";
        String alias = "bbb";

        // alias string should be the full flag string if not provided
        Flag f = new Flag(full);
        assertEquals(
                Flag.DEFAULT_PREFIX + full + Flag.DEFAULT_POSTFIX,
                f.getFlagAliasString()
        );

        f = new Flag(full, null);
        assertEquals(
                Flag.DEFAULT_PREFIX + full + Flag.DEFAULT_POSTFIX,
                f.getFlagAliasString()
        );

        // alias must be alias format if explicitly provided
        f = new Flag(full, alias);
        assertEquals(
                Flag.DEFAULT_ALIAS_PREFIX + alias + Flag.DEFAULT_ALIAS_POSTFIX,
                f.getFlagAliasString()
        );

        // custom formats should still work correctly
        f = Flag.ofCustomFormat("x", "?", "*");
        assertEquals("?x*", f.getFlagAliasString());
    }

    @Test
    public void hasAlias() {
        assertTrue(new Flag("aaa", "aaa").hasAlias());
        assertTrue(new Flag("ccc", "c").hasAlias());

        assertFalse(new Flag("aaa", "").hasAlias());
        assertFalse(new Flag("aaa", "  ").hasAlias());
        assertFalse(new Flag("ccc", null).hasAlias());
    }

    @Test
    public void parse() {
        String name = "abc123";
        String alias = "aa";

        String fullStr = Flag.DEFAULT_PREFIX + name + Flag.DEFAULT_POSTFIX;
        String aliasStr = Flag.DEFAULT_ALIAS_PREFIX + alias + Flag.DEFAULT_ALIAS_POSTFIX;

        // Expected to parse successfully.
        try {
            assertEquals(new Flag(name), Flag.parse(fullStr));
            assertEquals(new Flag(alias, alias), Flag.parse(aliasStr));

            assertEquals(fullStr, Flag.parse(fullStr).getFlagString());
            assertEquals(aliasStr, Flag.parse(aliasStr).getFlagAliasString());

        } catch (ParseException e) {
            fail(e);
        }

        // Expected to fail.
        assertThrows(ParseException.class, () -> Flag.parse(name));
        assertThrows(ParseException.class, () -> Flag.parse(alias));
        assertThrows(ParseException.class, () -> Flag.parse(null));
        assertThrows(ParseException.class, () -> Flag.parse(""));

        assertThrows(ParseException.class, ()
                -> Flag.parse(Flag.DEFAULT_PREFIX + Flag.DEFAULT_POSTFIX));
        assertThrows(ParseException.class, ()
                -> Flag.parse(Flag.DEFAULT_ALIAS_POSTFIX + Flag.DEFAULT_ALIAS_POSTFIX));
    }

    @Test
    public void parseOptional() {
        String name = "zzz";
        String alias = "z";

        String fullStr = Flag.DEFAULT_PREFIX + name + Flag.DEFAULT_POSTFIX;
        String aliasStr = Flag.DEFAULT_ALIAS_PREFIX + alias + Flag.DEFAULT_ALIAS_POSTFIX;

        assertEquals(new Flag(name), Flag.parseOptional(fullStr).orElse(null));
        assertEquals(new Flag(alias, alias), Flag.parseOptional(aliasStr).orElse(null));

        assertTrue(Flag.parseOptional("abcdefg").isEmpty());
        assertTrue(Flag.parseOptional("123").isEmpty());
        assertTrue(Flag.parseOptional(null).isEmpty());
        assertTrue(Flag.parseOptional("").isEmpty());
    }

    @Test
    public void findMatch() {
        Flag a = new Flag("aaa", "a");
        Flag b = new Flag("bbb", "b");
        Flag c = new Flag("ccc");
        Flag cAlt = new Flag("ccc", "c");

        // Determine whether it can correctly match flags to the equivalent string output.
        assertEquals(a, Flag.findMatch(a.getFlagString(), new Flag[] { a, b, c }).orElse(null));
        assertEquals(b, Flag.findMatch(b.getFlagAliasString(), new Flag[] { a, c, b }).orElse(null));

        assertFalse(Flag.findMatch(c.getFlagString(), new Flag[] { a, b }).isPresent());
        assertFalse(Flag.findMatch(cAlt.getFlagAliasString(), new Flag[] { b, c }).isPresent());

        assertFalse(Flag.findMatch(null, new Flag[] {}).isPresent());
        assertFalse(Flag.findMatch(null, new Flag[] { c, b, a }).isPresent());
    }

    @Test
    public void equals() {
        // Case 1: Basic equality checks
        Flag aaa = Flag.ofCustomFormat("aaa", "-", "");

        assertEquals(aaa, aaa);
        assertEquals(aaa, Flag.ofCustomFormat("aaa", "-", null));

        assertNotEquals(aaa, null);
        assertNotEquals(aaa, "-aaa");
        assertNotEquals(aaa, Flag.ofCustomFormat("aab", "-", null));
        assertNotEquals(aaa, Flag.ofCustomFormat("aaa", null, "/"));

        // Case 2: Advanced equality checks
        Flag a = new Flag("aaa", "a");
        Flag aCopy = new Flag("aaa", "a");
        Flag aAlt = a.getNameOnlyDefinition();

        Flag b = new Flag("bbb", "b");
        Flag bAlt = b.getAliasOnlyDefinition();

        Flag aAliasB = new Flag("aaa", "b");

        // - Trivial comparisons
        assertEquals(a, a);
        assertEquals(a, aCopy);

        assertNotEquals(a, b);
        assertNotEquals(a, bAlt);
        assertNotEquals(b, aAlt);
        assertNotEquals(aAlt, bAlt);

        // - Non-trivial comparisons
        assertEquals(a, aAlt); // Same name, excluding alias: equal
        assertEquals(a, aAliasB); // Same name, different alias: equal

        assertEquals(b, bAlt); // Excluding name, same alias: equal
        assertNotEquals(b, aAliasB); // Diff name; same alias: not equal
    }

    @Test
    public void equalsFlagString() {
        Flag a1 = new Flag("aa");
        Flag a2 = new Flag("aa", "a");
        Flag b = new Flag("bb", "a");

        assertTrue(a1.equalsFlagString(a2));
        assertTrue(a2.equalsFlagString(a1));

        assertFalse(a1.equalsFlagString(b));
        assertFalse(a2.equalsFlagString(b));
        assertFalse(b.equalsFlagString(a2));

        assertFalse(a2.equalsFlagString(null));
    }

    @Test
    public void equalsFlagAliasString() {
        Flag a1 = new Flag("aa");
        Flag a2 = new Flag("aa", "a");
        Flag a3 = new Flag("aa");
        Flag b = new Flag("bb", "a");

        assertTrue(a1.equalsFlagAliasString(a3));
        assertTrue(a3.equalsFlagAliasString(a1));

        assertTrue(a2.equalsFlagAliasString(b));
        assertTrue(b.equalsFlagAliasString(a2));

        assertFalse(a1.equalsFlagAliasString(a2));
        assertFalse(a2.equalsFlagAliasString(a1));
        assertFalse(a3.equalsFlagAliasString(b));

        assertFalse(a2.equalsFlagAliasString(null));
    }
}
