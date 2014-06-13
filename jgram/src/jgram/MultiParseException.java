package jgram;

import java.io.File;
import java.util.Collection;

public class MultiParseException extends ParseException {
    Collection<ParseException> exceptions;

    public MultiParseException(final Collection<ParseException> exceptions) {
        this.exceptions = exceptions;
    }

    @Override
    public void printError(final Tokenizer tokenizer, final File file) {
        for (final ParseException e : this.exceptions) {
            if (e.getErrorOffset() == getErrorOffset()) {
                e.printError(tokenizer, file);
            }
        }
    }

    @Override
    public int getErrorOffset() {
        int max = -1;
        for (final ParseException e : this.exceptions) {
            max = Math.max(max, e.getErrorOffset());
        }
        return max;
    }
}
