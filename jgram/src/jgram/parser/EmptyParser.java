package jgram.parser;

import jgram.Tokenizer;

class EmptyParser implements Parser {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public void parse(final Tokenizer tokenizer) {
    }
}