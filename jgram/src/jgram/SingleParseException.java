package jgram;

import java.io.File;

public class SingleParseException extends ParseException {
    int errorOffset;

    public SingleParseException(final String message, final int errorOffset) {
        super(message);
        this.errorOffset = errorOffset;
    }

    public SingleParseException(final String message, final Throwable cause, final int errorOffset) {
        super(message, cause);
        this.errorOffset = errorOffset;
    }

    @Override
    public void printError(final Tokenizer tokenizer, final File file) {
        tokenizer.printError(file, this.errorOffset, this);
    }

    @Override
    public int getErrorOffset() {
        return this.errorOffset;
    }
}
