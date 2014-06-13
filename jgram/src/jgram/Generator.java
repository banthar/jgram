package jgram;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jgram.JGram.AtLeast;
import jgram.JGram.Optional;
import jgram.JGram.Pattern;
import jgram.JGram.Prefix;
import jgram.JGram.Separator;
import jgram.JGram.Suffix;
import jgram.JGram.Terminator;
import jgram.parser.Parser;
import jgram.parser.ParserFactory;
import jgram.parser.ParserReference;

import org.reflections.Reflections;

public class Generator {
    private Set<Class<?>> classes;
    private final Map<Class<?>, Parser> classParsers = new HashMap<>();
    private final Reflections reflections = new Reflections("");

    public Parser parserOf(final Class<?> type) {
        this.classes = new HashSet<>();
        final Parser parser = classParser(type);
        while (!this.classes.isEmpty()) {
            final Class<?> c = this.classes.iterator().next();
            if (!this.classParsers.containsKey(c)) {
                this.classParsers.put(c, classParser(c));
            }
            this.classes.remove(c);
        }
        this.classes = null;
        return parser;
    }

    private Parser classParser(final Class<?> type) {
        final List<Parser> parsers = new ArrayList<>();
        for (final Class<?> subclass : getSubTypes(type)) {
            this.classes.add(subclass);
            if (!Modifier.isAbstract(subclass.getModifiers())) {
                parsers.add(new LazyClassParser(subclass));
            }
        }
        if (!Modifier.isAbstract(type.getModifiers())) {
            parsers.add(concreteClassParser(type));
        }
        return ParserFactory.either(parsers);
    }

    private Collection<? extends Class<?>> getSubTypes(final Class<?> type) {
        return this.reflections.getSubTypesOf(type);
    }

    private Parser concreteClassParser(final Class<?> type) {
        final List<Parser> parsers = new ArrayList<>();
        final Prefix classPrefix = type.getAnnotation(Prefix.class);
        if (classPrefix != null) {
            parsers.add(ParserFactory.keyword(classPrefix.value()));
        }
        for (final Field field : type.getDeclaredFields()) {
            if (!field.isSynthetic()) {
                final List<Parser> fieldParsers = new ArrayList<>();
                final Prefix prefix = field.getAnnotation(Prefix.class);
                if (prefix != null) {
                    fieldParsers.add(ParserFactory.keyword(prefix.value()));
                }
                fieldParsers.add(fieldParser(field));
                final Suffix suffix = field.getAnnotation(Suffix.class);
                if (suffix != null) {
                    fieldParsers.add(ParserFactory.keyword(suffix.value()));
                }
                if (field.getAnnotation(Optional.class) != null) {
                    parsers.add(ParserFactory.either(Arrays.asList(ParserFactory.concat(fieldParsers), ParserFactory.empty())));
                } else {
                    parsers.add(ParserFactory.concat(fieldParsers));
                }
            }
        }
        return ParserFactory.concat(parsers);
    }

    private Parser fieldParser(final Field field) {
        return typeParser(field, field.getGenericType());
    }

    private Parser typeParser(final Field field, final Type type) {
        if (type == Void.class) {
            return ParserFactory.empty();
        } else if (type == String.class) {
            final Pattern pattern = field.getAnnotation(Pattern.class);
            if (pattern != null) {
                return ParserFactory.stringParser(pattern.value());
            } else {
                return ParserFactory.stringParser();
            }
        } else if (type == BigDecimal.class) {
            return ParserFactory.number();
        } else if (type instanceof ParameterizedType) {
            final ParameterizedType parameterizedType = (ParameterizedType) type;
            if (parameterizedType.getRawType() == List.class || parameterizedType.getRawType() == Set.class) {
                final Terminator terminator = field.getAnnotation(Terminator.class);
                Parser terminatorParser;
                if (terminator != null) {
                    terminatorParser = ParserFactory.keyword(terminator.value());
                } else {
                    terminatorParser = ParserFactory.empty();
                }
                final Separator separator = field.getAnnotation(Separator.class);
                Parser separatorParser;
                if (separator != null) {
                    separatorParser = ParserFactory.keyword(separator.value());
                } else {
                    separatorParser = ParserFactory.empty();
                }
                final Parser elementParser = typeParser(field, parameterizedType.getActualTypeArguments()[0]);
                final ParserReference remainderParser = new ParserReference();
                final Parser suffixParser;
                if (terminator == null) {
                    suffixParser = ParserFactory.either(remainderParser, terminatorParser);
                } else {
                    suffixParser = ParserFactory.either(terminatorParser, remainderParser);
                }
                remainderParser.setParser(ParserFactory.concat(separatorParser, elementParser, suffixParser));
                final Parser nonEmptyParser = ParserFactory.concat(elementParser, suffixParser);
                int atLeast;
                if (field.getAnnotation(AtLeast.class) != null) {
                    atLeast = field.getAnnotation(AtLeast.class).value();
                } else {
                    atLeast = 0;
                }
                if (atLeast == 0) {
                    if (terminator != null) {
                        return ParserFactory.either(terminatorParser, nonEmptyParser);
                    } else {
                        return ParserFactory.either(nonEmptyParser, terminatorParser);
                    }
                } else {
                    Parser p = nonEmptyParser;
                    while (atLeast > 1) {
                        p = ParserFactory.concat(elementParser, separatorParser, terminatorParser, nonEmptyParser);
                        atLeast--;
                    }
                    return p;
                }
            } else {
                throw new IllegalStateException("Unknown parametrized type: " + parameterizedType);
            }
        } else if (type instanceof Class) {
            this.classes.add((Class<?>) type);
            return new LazyClassParser((Class<?>) type);
        } else {
            throw new IllegalStateException("Unknown type: " + type);
        }
    }

    private class LazyClassParser implements Parser {
        private final Class<?> type;

        public LazyClassParser(final Class<?> type) {
            this.type = type;
        }

        private Parser getParser() {
            return Generator.this.classParsers.get(this.type);
        }

        @Override
        public String toString() {
            return this.type.getSimpleName();
        }

        @Override
        public void parse(final Tokenizer tokenizer) throws ParseException {
            getParser().parse(tokenizer);
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (final Entry<Class<?>, Parser> entry : this.classParsers.entrySet()) {
            s += entry.getKey().getSimpleName() + " = " + entry.getValue();
            s += "\n";
        }
        return s;
    }
}
