package jgram.parser;

import jgram.ParseException;
import jgram.Tokenizer;

public class ParserReference implements Parser {
    Parser parser;

    public void setParser(final Parser parser) {
        this.parser = parser;
    }

    @Override
    public void parse(final Tokenizer tokenizer) throws ParseException {
        this.parser.parse(tokenizer);
    }

    int cycles = 0;

    @Override
    public String toString() {
        if (this.cycles > 2) {
            return "...";
        } else {
            this.cycles++;
            try {
                return this.parser.toString();
            } finally {
                this.cycles--;
            }
        }
    }
}
