package jgram;

import java.io.File;

public class ParseException extends Exception {
    public ParseException() {
    }

    public ParseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParseException(final String message) {
        super(message);
    }

    public void printError(final Tokenizer tokenizer, final File file) {
        throw new AbstractMethodError();
    }

    public int getErrorOffset() {
        throw new AbstractMethodError();
    }
}
