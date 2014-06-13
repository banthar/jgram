package jgram;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Pattern;

public final class Tokenizer {
    private enum CharType {
        WHITESPACE, LETTER_OR_DIGIT, SEPARATOR, OPERATOR, QUOTE;
        private static boolean isSeparator(final char ch) {
            final char[] separators = { '(', ')', '{', '}', '[', ']', '<', '>' };
            for (final char separator2 : separators) {
                if (separator2 == ch) {
                    return true;
                }
            }
            return false;
        }

        private static boolean isQuote(final char ch) {
            return ch == '\'' || ch == '\"';
        }

        static CharType typeOf(final char ch) {
            if (Character.isWhitespace(ch)) {
                return WHITESPACE;
            } else if (Character.isLetterOrDigit(ch) || ch == '_') {
                return LETTER_OR_DIGIT;
            } else if (isSeparator(ch)) {
                return SEPARATOR;
            } else if (isQuote(ch)) {
                return QUOTE;
            } else {
                return OPERATOR;
            }
        }
    }

    private int position;
    private final CharSequence input;

    public Tokenizer(final CharSequence input) {
        this(input, 0);
    }

    public Tokenizer(final CharSequence input, final int position) {
        this.input = input;
        this.position = position;
    }

    public Tokenizer(final File file) throws IOException {
        this(getFileContents(file));
    }

    static CharBuffer getFileContents(final File file) throws IOException {
        try (final FileInputStream inputStream = new FileInputStream(file)) {
            try (final FileChannel channel = inputStream.getChannel()) {
                final ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
                return Charset.forName("UTF-8").decode(buffer);
            }
        }
    }

    public boolean hasNext() {
        skipWhiteSpace();
        return this.position < this.input.length();
    }

    private void skipWhiteSpace() {
        while (this.position < this.input.length() && charTypeAt(this.position) == CharType.WHITESPACE) {
            this.position++;
        }
    }

    private String peek() throws ParseException {
        return readNext();
    }

    public String next() throws ParseException {
        debug("*");
        final String token = peek();
        this.position += token.length();
        return token;
    }

    public String next(final String pattern) throws ParseException {
        debug(pattern);
        if (!hasNext()) {
            throw new SingleParseException("expected \"" + pattern + "\" not EOF", getState());
        }
        final String token = peek();
        if (pattern.equals(token)) {
            this.position += token.length();
            return token;
        } else {
            throw new SingleParseException("expected \"" + pattern + "\" not \"" + token + "\"", getState());
        }
    }

    public String next(final Pattern pattern) throws ParseException {
        debug(pattern.pattern());
        if (!hasNext()) {
            throw new SingleParseException("expected string matching \"" + pattern.pattern() + "\" not EOF", getState());
        }
        final String token = peek();
        if (pattern.matcher(token).matches()) {
            this.position += token.length();
            return token;
        } else {
            throw new SingleParseException("expected string matching \"" + pattern.pattern() + "\" not \"" + token + "\"", getState());
        }
    }

    private void debug(final String pattern) throws ParseException {
        final PrintStream err = System.err;
        final String token = peek();
        final Location location;
        if (token != null) {
            location = getLocation(this.position, token.length());
        } else {
            location = getLocation(this.position, 1);
        }
        err.println(location.getQuote());
        final String marker = location.getMarker();
        err.println(marker + " " + pattern);
    }

    private String readNext() throws ParseException {
        skipWhiteSpace();
        final int start = this.position;
        if (start >= this.input.length()) {
            return null;
        }
        final CharType type = charTypeAt(this.position);
        int end = this.position;
        if (type == CharType.QUOTE) {
            final char separator = this.input.charAt(this.position);
            do {
                if (this.input.charAt(end) == '\\') {
                    end += 2;
                } else {
                    end += 1;
                }
                if (end >= this.input.length()) {
                    throw new SingleParseException("unmatched '" + separator + "'", this.input.length() - 1);
                }
            } while (this.input.charAt(end) != separator);
            end++;
        } else {
            end++;
            if (type != CharType.SEPARATOR) {
                while (end < this.input.length() && CharType.typeOf(this.input.charAt(end)) == type) {
                    end++;
                }
            }
        }
        return this.input.subSequence(start, end).toString();
    }

    private CharType charTypeAt(final int i) {
        final CharType type = CharType.typeOf(this.input.charAt(i));
        return type;
    }

    @Override
    public Tokenizer clone() {
        return new Tokenizer(this.input, this.position);
    }

    public int getState() {
        return this.position;
    }

    public void setState(final int state) {
        this.position = state;
    }

    public final class Location {
        private final int offset;
        private final int size;
        private final int line;
        private final int lineOffset;

        public Location(final int offset) {
            this(offset, 0);
        }

        private Location(final int offset, final int size) {
            if (offset > getInput().length()) {
                throw new IllegalArgumentException("offset out of range: " + offset);
            }
            int currentLine = 0;
            int lineStart = 0;
            for (int i = 0; i < offset; i++) {
                if (getInput().charAt(i) == '\n') {
                    currentLine++;
                    lineStart = i + 1;
                }
            }
            int lineEnd = lineStart;
            while (lineEnd < getInput().length() && getInput().charAt(lineEnd) != '\n') {
                lineEnd++;
            }
            this.offset = offset;
            this.size = size == 0 ? lineEnd - offset : size;
            this.line = currentLine;
            this.lineOffset = lineStart;
        }

        @Override
        public String toString() {
            final String lineAndColumn = toString(this.line) + ":" + toString(getStartColumn());
            if (this.size == 0) {
                return lineAndColumn;
            } else {
                return lineAndColumn + "-" + toString(getEndColumn());
            }
        }

        private int getEndColumn() {
            return getStartColumn() + this.size - 1;
        }

        private int getStartColumn() {
            return this.offset - this.lineOffset;
        }

        private String toString(final int startC) {
            return String.valueOf(startC + 1);
        }

        public String getQuote() {
            int lineEnd = this.lineOffset;
            while (lineEnd < getInput().length() && getInput().charAt(lineEnd) != '\n') {
                lineEnd++;
            }
            return getInput().subSequence(this.lineOffset, lineEnd).toString();
        }

        public String getMarker() {
            return multiply(' ', this.offset - this.lineOffset) + multiply('^', this.size);
        }

        private String multiply(final char c, final int n) {
            String s = "";
            for (int i = 0; i < n; i++) {
                s += c;
            }
            return s;
        }
    }

    public void printError(final File file, final int offset, final ParseException e) {
        final Location location = getLocation(offset);
        final PrintStream err = System.err;
        err.println(file + ":" + location + ": error: " + e.getMessage());
        err.println(location.getQuote());
        err.println(location.getMarker());
        // e.printStackTrace(err);
    }

    public Location getLocation(final int offset) {
        return new Location(offset);
    }

    public Location getLocation(final int offset, final int size) {
        return new Location(offset, size);
    }

    private CharSequence getInput() {
        return Tokenizer.this.input;
    }
}
