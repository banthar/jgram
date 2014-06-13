package jgram.parser;

import jgram.ParseException;
import jgram.SingleParseException;
import jgram.Tokenizer;

class EOFParser implements Parser {
    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        if (tokenizer.hasNext()) {
            throw new SingleParseException("expected end of input", tokenizer.getState());
        }
    }
}
