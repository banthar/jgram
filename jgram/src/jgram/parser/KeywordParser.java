package jgram.parser;

import jgram.ParseException;
import jgram.Tokenizer;

class KeywordParser implements Parser {
    private final String pattern;

    public KeywordParser(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return "\"" + this.pattern + "\"";
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        tokenizer.next(this.pattern);
    }
}