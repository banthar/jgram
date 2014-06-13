package jgram.java;

import java.io.File;

import jgram.Generator;
import jgram.ParseException;
import jgram.Tokenizer;
import jgram.parser.Parser;

import org.junit.Assert;

public abstract class ElementTest {
    public abstract Class<?> getElementClass();

    protected final void parse(final String s) throws ParseException {
        final Tokenizer tokenizer = new Tokenizer(s);
        try {
            final Generator generator = new Generator();
            final Parser parser = generator.parserOf(getElementClass());
            System.out.println(generator);
            parser.parse(tokenizer);
        } catch (final ParseException e) {
            e.printError(tokenizer, new File("stdin"));
            throw e;
        }
        Assert.assertFalse(tokenizer.hasNext());
    }
}
