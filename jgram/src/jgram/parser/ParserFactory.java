package jgram.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ParserFactory {
    public static Parser concat(final List<Parser> parsers) {
        if (parsers.size() == 0) {
            return empty();
        } else if (parsers.size() == 1) {
            return parsers.get(0);
        } else {
            return new ConcatParser(parsers);
        }
    }

    public static Parser concat(final Parser... parsers) {
        final List<Parser> optimizedParsers = new ArrayList<>();
        for (final Parser parser : parsers) {
            if (!(parser instanceof EmptyParser)) {
                optimizedParsers.add(parser);
            }
        }
        return concat(optimizedParsers);
    }

    public static Parser either(final List<Parser> parsers) {
        if (parsers.size() == 0) {
            return empty();
        } else if (parsers.size() == 1) {
            return parsers.get(0);
        } else {
            if (parsers.get(0) instanceof EmptyParser) {
                throw new IllegalStateException();
            }
            return new EitherParser(parsers);
        }
    }

    public static Parser either(final Parser... parsers) {
        return either(Arrays.asList(parsers));
    }

    public static Parser empty() {
        return new EmptyParser();
    }

    public static Parser keyword(final String pattern) {
        return new KeywordParser(pattern);
    }

    public static Parser optional(final Parser parser) {
        return either(parser, empty());
    }

    public static Parser stringParser() {
        return new StringParser(Pattern.compile(".*"));
    }

    public static Parser stringParser(final String pattern) {
        return new StringParser(Pattern.compile(pattern));
    }

    public static Parser eof() {
        return new EOFParser();
    }

    public static Parser number() {
        return new NumberParser();
    }
}
