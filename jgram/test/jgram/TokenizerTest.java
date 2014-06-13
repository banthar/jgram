package jgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TokenizerTest {
    private static List<String> tokenize(final String string) throws ParseException {
        final List<String> strings = new ArrayList<>();
        final Tokenizer tokenizer = new Tokenizer(string);
        while (tokenizer.hasNext()) {
            strings.add(tokenizer.next().toString());
        }
        return strings;
    }

    @Test
    public void emptySequenceHasNoTokens() throws ParseException {
        Assert.assertEquals(Arrays.asList(), tokenize(""));
    }

    @Test
    public void whitespacesOnlyHasNoTokens() throws ParseException {
        Assert.assertEquals(Arrays.asList(), tokenize(" "));
    }

    @Test
    public void singleLetter() throws ParseException {
        Assert.assertEquals(Arrays.asList("a"), tokenize("a"));
    }

    @Test
    public void multipleLetters() throws ParseException {
        Assert.assertEquals(Arrays.asList("abc"), tokenize("abc"));
    }

    @Test
    public void multipleLettersAndSpace() throws ParseException {
        Assert.assertEquals(Arrays.asList("abc"), tokenize("abc "));
    }

    @Test
    public void operators() throws ParseException {
        Assert.assertEquals(Arrays.asList("a", "++", "b"), tokenize("a++b"));
    }

    @Test
    public void separators() throws ParseException {
        Assert.assertEquals(Arrays.asList("(", "("), tokenize("(("));
    }

    @Test
    public void underscore() throws ParseException {
        Assert.assertEquals(Arrays.asList("a_b"), tokenize("a_b"));
    }

    @Test
    public void types() throws ParseException {
        Assert.assertEquals(Arrays.asList("Set", "<", "Class", "<", "?", ">", ">"), tokenize("Set<Class<?>>"));
    }

    @Test
    public void stringLiteral() throws ParseException {
        Assert.assertEquals(Arrays.asList("\"aaa\""), tokenize("\"aaa\""));
    }

    @Test
    public void charLiteral() throws ParseException {
        Assert.assertEquals(Arrays.asList("'aaa'"), tokenize("'aaa'"));
    }

    @Test
    public void escapedCharLiteral() throws ParseException {
        Assert.assertEquals(Arrays.asList("'a\\'aa'"), tokenize("'a\\'aa'"));
    }

    @Test(expected = ParseException.class)
    public void unmatchedQuote() throws ParseException {
        tokenize("'aaa");
    }

    @Test(expected = ParseException.class)
    public void unmatchedEscapeQuote() throws ParseException {
        tokenize("'aaa\\");
    }

    @Test
    public void emptyCharLiteral() throws ParseException {
        Assert.assertEquals(Arrays.asList("''"), tokenize("''"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void locationOutOfRange() {
        new Tokenizer("").getLocation(1);
    }

    @Test
    public void emptyLocation() {
        Assert.assertEquals("1:1", new Tokenizer("").getLocation(0).toString());
    }

    @Test
    public void startOfLine() {
        Assert.assertEquals("2:1-2", new Tokenizer("12\n45").getLocation(3).toString());
    }

    @Test
    public void middleOfLine() {
        Assert.assertEquals("1:3-6", new Tokenizer("123456").getLocation(2).toString());
    }

    @Test
    public void marker() {
        Assert.assertEquals(" ^", new Tokenizer("12").getLocation(1).getMarker());
    }
}
