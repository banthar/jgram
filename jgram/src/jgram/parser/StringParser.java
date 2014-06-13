package jgram.parser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import jgram.ParseException;
import jgram.SingleParseException;
import jgram.Tokenizer;

class StringParser implements Parser {
    private final Pattern pattern;

    public StringParser(final Pattern pattern) {
        this.pattern = pattern;
    }

    private final Set<String> keywords = new HashSet<>(Arrays.asList("abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package",
            "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import",
            "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface",
            "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"));

    @Override
    public String toString() {
        return this.pattern.pattern();
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        if (!tokenizer.hasNext()) {
            throw new SingleParseException("expected identifier not EOF", tokenizer.getState());
        }
        final int state = tokenizer.getState();
        final String token = tokenizer.next(this.pattern);
        if (this.keywords.contains(token)) {
            throw new SingleParseException("expected identifier not keyword " + token, state);
        }
    }
}