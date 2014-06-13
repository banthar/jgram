package jgram.java;

import java.io.File;

import jgram.Generator;
import jgram.ParseException;
import jgram.Tokenizer;
import jgram.parser.Parser;

public class Main {
    public static void main(final String[] args) throws Exception {
        final Generator generator = new Generator();
        final Parser parser = generator.parserOf(JavaFile.class);
        System.out.println(generator);
        // generateRandom(parser);
        final File file = new File("src/jgram/Generator.java");
        final Tokenizer tokenizer = new Tokenizer(file);
        try {
            parser.parse(tokenizer);
        } catch (final ParseException e) {
            e.printError(tokenizer, file);
        }
    }
}
