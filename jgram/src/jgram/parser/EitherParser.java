package jgram.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jgram.MultiParseException;
import jgram.ParseException;
import jgram.Tokenizer;

class EitherParser implements Parser {
    private final List<Parser> parsers;

    public EitherParser(final List<Parser> parsers) {
        this.parsers = parsers;
        if (parsers.size() <= 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (final Parser parser : this.parsers) {
            if (!s.isEmpty()) {
                s += "|";
            }
            s += parser;
        }
        return "either(" + s + ")";
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        final int state = tokenizer.getState();
        final Iterator<Parser> iterator = this.parsers.iterator();
        final List<ParseException> exceptions = new ArrayList<>();
        while (iterator.hasNext()) {
            try {
                final Parser parser = iterator.next();
                parser.parse(tokenizer);
                return;
            } catch (final ParseException e) {
                exceptions.add(e);
                tokenizer.setState(state);
                if (!iterator.hasNext()) {
                    throw new MultiParseException(exceptions);
                }
            }
        }
        throw new IllegalStateException();
    }
}