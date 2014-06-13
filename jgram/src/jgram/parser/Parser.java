package jgram.parser;

import jgram.ParseException;
import jgram.Tokenizer;

public interface Parser {
    void parse(Tokenizer tokenizer) throws ParseException;
}