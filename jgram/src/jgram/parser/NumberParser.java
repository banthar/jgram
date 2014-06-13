package jgram.parser;

import java.math.BigDecimal;

import jgram.ParseException;
import jgram.SingleParseException;
import jgram.Tokenizer;

public class NumberParser implements Parser {
    private BigDecimal parseNumber(final Tokenizer tokenizer) throws ParseException {
        if (!tokenizer.hasNext()) {
            throw new SingleParseException("expected number not EOF", tokenizer.getState());
        }
        final int state = tokenizer.getState();
        final String token = tokenizer.next();
        try {
            return new BigDecimal(token);
        } catch (final NumberFormatException e) {
            tokenizer.setState(state);
            throw new SingleParseException("expected number not \"" + token + "\"", e, tokenizer.getState());
        }
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        parseNumber(tokenizer);
    }

    @Override
    public String toString() {
        return "<number>";
    }
}
