package jgram.parser;

import java.util.List;

import jgram.ParseException;
import jgram.Tokenizer;

class ConcatParser implements Parser {
    private final List<Parser> parsers;

    public ConcatParser(final List<Parser> parsers) {
        this.parsers = parsers;
        if (parsers.size() <= 1) {
            throw new IllegalArgumentException("Not enough parsers:" + parsers);
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (final Parser parser : this.parsers) {
            if (!s.isEmpty()) {
                s += " ";
            }
            s += parser;
        }
        return s;
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        final int state = tokenizer.getState();
        try {
            for (final Parser parser : this.parsers) {
                parser.parse(tokenizer);
            }
        } catch (final ParseException e) {
            tokenizer.setState(state);
            throw e;
        }
    }
}